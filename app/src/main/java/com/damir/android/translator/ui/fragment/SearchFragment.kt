package com.damir.android.translator.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.damir.android.translator.R
import com.damir.android.translator.utils.setToolbarTitle

class SearchFragment: Fragment(R.layout.fragment_search) {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setToolbarTitle(R.string.search)
    }
}