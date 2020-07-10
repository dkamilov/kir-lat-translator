package com.damir.android.translator.firebase

import android.util.Log
import com.damir.android.translator.data.FirebaseUserInfo
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

private val TAG = "DamirK"
object FirebaseDatabase {

    const val USER_SPENT_TIME = "spentTime"
    private const val USER_WORDS_TRANSLATED = "wordsCount"
    private val userInfoReadListeners = mutableListOf<UserInfoReadListener>()
    private val db = Firebase.firestore

    private var currentUser: FirebaseUser? = null
    private var userRef: DocumentReference? = null
    private var registration: ListenerRegistration? = null
    private var allUsersReadListener: AllUsersReadListener?= null

    private fun createUserInfo(user: FirebaseUser): FirebaseUserInfo {
        return FirebaseUserInfo(
            username = user.displayName,
            email = user.email,
            photoUrl = user.photoUrl.toString(),
            spentTime = 0,
            wordsCount = 0
        )
    }

    fun updateUser(user: FirebaseUser?) {
        currentUser = user
        user?.let {
            userRef = db.collection("users").document(it.uid)
        }
    }

    fun setInfoReadListener(listener: UserInfoReadListener) {
        userInfoReadListeners.add(listener)
    }

    fun setAllUsersReadListener(listener: AllUsersReadListener) {
        allUsersReadListener = listener
    }

    fun removeReadListener() {
        userInfoReadListeners.clear()
        allUsersReadListener = null
        registration?.remove()
    }

    fun saveUserInfo() {
        userRef?.get()
            ?.addOnCompleteListener { task ->
                if(task.isSuccessful && task.result?.exists() == false) {
                    currentUser?.let { user ->
                        val userInfo = createUserInfo(user)
                        db.collection("users").document(user.uid).set(userInfo)
                    }
                }
            }
    }

    fun readUserInfo() {
        registration = userRef?.addSnapshotListener { snapshot, e ->
            Log.d(TAG, "readUserInfo: ${snapshot?.data}")
            if(snapshot != null && snapshot.exists()){
                snapshot.toObject<FirebaseUserInfo>()?.let { user ->
                    userInfoReadListeners.forEach {
                        it.onUserRetrieved(user)
                    }
                }
            }
        }
    }

    fun getAllUsers() {
        Log.d(TAG, "getAllUsers")
        db.collection("users")
            .get()
            .addOnSuccessListener { result ->
                val allUsers = mutableListOf<FirebaseUserInfo>()
                for(document in result) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                    document.toObject<FirebaseUserInfo>().also {
                        allUsers.add(it)
                    }
                }
                allUsersReadListener?.onAllUsersRetrieved(allUsers)
            }.addOnFailureListener {
                Log.d(TAG, "getAllUsers error: $it")
            }
    }

    fun updateSpentTime(value: Long) {
        userRef?.update(USER_SPENT_TIME, FieldValue.increment(value))
    }

    fun incrementWordsCount() {
        userRef?.update(USER_WORDS_TRANSLATED, FieldValue.increment(1))
    }

    interface UserInfoReadListener {
        fun onUserRetrieved(userInfo: FirebaseUserInfo)
    }

    interface AllUsersReadListener {
        fun onAllUsersRetrieved(users: List<FirebaseUserInfo>)
    }
}