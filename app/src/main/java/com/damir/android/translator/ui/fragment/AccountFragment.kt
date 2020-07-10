package com.damir.android.translator.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.damir.android.translator.R
import com.damir.android.translator.data.FirebaseUserInfo
import com.damir.android.translator.firebase.FirebaseDatabase
import com.damir.android.translator.ui.AccountPagerAdapter
import com.damir.android.translator.utils.*
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.fragment_account.*


class AccountFragment: Fragment(R.layout.fragment_account), FirebaseDatabase.UserInfoReadListener{

    private val RC_SIGN_IN = 0
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private val tabs = listOf(R.string.text_statistic, R.string.text_all_accounts)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        FirebaseDatabase.setInfoReadListener(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setToolbarTitle(R.string.account)
        setSignInButton()
        setViewPager()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    override fun onDestroy() {
        super.onDestroy()
        FirebaseDatabase.removeReadListener()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        showSignInProgress(false)
        if(requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    override fun onUserRetrieved(userInfo: FirebaseUserInfo) {
        textUsername.text = userInfo.username
        textUserEmail.text = userInfo.email
        imageUserAvatar.load(userInfo.photoUrl)
    }

    private fun setSignInButton() {
        btnSignIn.setSize(SignInButton.SIZE_STANDARD)
        btnSignIn.setOnClickListener {
            signIn()
        }
    }

    private fun setViewPager() {
        stats_view_pager.adapter = AccountPagerAdapter(this)
        TabLayoutMediator(stats_tab_layout, stats_view_pager) { tab, position ->
            tab.text = getString(tabs[position])
        }.attach()
    }

    private fun signIn() {
        showSignInProgress(true)
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            firebaseAuthWithGoogle(account?.idToken!!)
        } catch(e: ApiException) {
            Log.w("AccountFragment", "signIn failed ${e.printStackTrace()}")
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener {task ->
                if(task.isSuccessful) {
                    val user = auth.currentUser
                    FirebaseDatabase.updateUser(user)
                    FirebaseDatabase.saveUserInfo()
                    SpentTimeCounter.start()
                    updateUI(user)
                } else {
                    updateUI(null)
                }
            }
    }

    private fun updateUI(user: FirebaseUser?) {
        user?.let {
            FirebaseDatabase.updateUser(user)
            showAuthorizedView()
            return
        }
        showUnauthorizedView()
    }

    private fun showAuthorizedView() {
        FirebaseDatabase.readUserInfo()
        layoutAuthorizedProfile.show()
        layoutNotAuthorized.hide()
        setSignOutButton()
    }

    private fun showUnauthorizedView() {
        layoutNotAuthorized.show()
        layoutAuthorizedProfile.hide()

    }

    private fun showSignInProgress(visible: Boolean) {
        if(visible) progressSignIn.show()
        else progressSignIn.hide()
    }

    private fun setSignOutButton() {
        btnSignOut.setOnClickListener {
            signOut()
        }
    }

    private fun signOut() {
        auth.signOut()
        googleSignInClient.signOut()
            .addOnCompleteListener {
                updateUI(null)
                SpentTimeCounter.stop()
            }
    }
}