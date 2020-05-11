package com.damir.android.translator.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

fun Fragment.setToolbarTitle(title: Int) {
    (activity as AppCompatActivity).supportActionBar?.title = getString(title)
}