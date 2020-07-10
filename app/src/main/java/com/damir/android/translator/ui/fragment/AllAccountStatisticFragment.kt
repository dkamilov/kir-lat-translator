package com.damir.android.translator.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.damir.android.translator.R
import com.damir.android.translator.data.FirebaseUserInfo
import com.damir.android.translator.firebase.FirebaseDatabase
import com.damir.android.translator.ui.AllAccountStatisticAdapter
import kotlinx.android.synthetic.main.fragment_all_account_statistic.*

class AllAccountStatisticFragment: Fragment(R.layout.fragment_all_account_statistic),
    FirebaseDatabase.AllUsersReadListener {

    private lateinit var accountsAdapter: AllAccountStatisticAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseDatabase.setAllUsersReadListener(this)
        accountsAdapter = AllAccountStatisticAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        FirebaseDatabase.getAllUsers()
    }

    override fun onAllUsersRetrieved(users: List<FirebaseUserInfo>) {
        updateAccountList(users)
    }

    private fun setupRecyclerView() {
        statsRecyclerView.adapter = accountsAdapter
    }

    private fun updateAccountList(items: List<FirebaseUserInfo>) {
        accountsAdapter.setAccounts(items)
    }
}