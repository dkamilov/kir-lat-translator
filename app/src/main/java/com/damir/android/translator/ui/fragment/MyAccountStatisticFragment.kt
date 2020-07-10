package com.damir.android.translator.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.damir.android.translator.R
import com.damir.android.translator.data.FirebaseUserInfo
import com.damir.android.translator.firebase.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_my_account_statistic.*

class MyAccountStatisticFragment: Fragment(R.layout.fragment_my_account_statistic), FirebaseDatabase.UserInfoReadListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseDatabase.setInfoReadListener(this)
    }

    @SuppressLint("SetTextI18n")
    override fun onUserRetrieved(userInfo: FirebaseUserInfo) {
        textUserSpentTime.text = (userInfo.spentTime?.div(60)).toString() + " min"
        textTranslatedWordCount.text = userInfo.wordsCount.toString()
    }

}