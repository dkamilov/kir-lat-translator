package com.damir.android.translator.utils

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.damir.android.translator.MainActivity
import com.damir.android.translator.R
import com.damir.android.translator.ui.fragment.FavoritesFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_kir_lat.*

val Activity.isThemeNight: Boolean
    get() {
        val sharedPref = getSharedPreferences(
            getString(R.string.shared_prefs), Context.MODE_PRIVATE)
        val themeMode = sharedPref.getInt(PREFS_THEME_KEY, THEME_NIGHT)
        return themeMode == THEME_NIGHT
    }

fun Fragment.setToolbarTitle(title: Int) {
    val m = (activity as AppCompatActivity) as MainActivity
    m.toobar_title.text = getString(title)
}

fun Fragment.showSnackBar(text: Int) {
    Snackbar.make(requireView(), text, Snackbar.LENGTH_SHORT).show()
}