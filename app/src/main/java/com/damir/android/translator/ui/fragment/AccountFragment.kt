package com.damir.android.translator.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.damir.android.translator.R
import com.damir.android.translator.utils.setToolbarTitle

class AccountFragment: Fragment(R.layout.fragment_account) {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setToolbarTitle(R.string.account)
    }
}