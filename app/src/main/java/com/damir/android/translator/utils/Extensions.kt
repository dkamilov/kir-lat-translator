package com.damir.android.translator.utils

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.damir.android.translator.MainActivity
import com.damir.android.translator.R
import com.damir.android.translator.ui.fragment.FavoritesFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_kir_lat.*

val Activity.isThemeNight: Boolean
    get() {
        val sharedPref = getSharedPreferences(
            getString(R.string.shared_prefs), Context.MODE_PRIVATE
        )
        val themeMode = sharedPref.getInt(PREFS_THEME_KEY, THEME_NIGHT)
        return themeMode == THEME_NIGHT
    }

val Activity.sharedPrefs: SharedPreferences
    get(){
        return getSharedPreferences(
            getString(R.string.shared_prefs), Context.MODE_PRIVATE)
    }

val Fragment.sharedPrefs: SharedPreferences
    get(){
        return requireActivity().getSharedPreferences(
            getString(R.string.shared_prefs), Context.MODE_PRIVATE)
    }

fun View.show() {
    if(visibility == View.GONE)
        visibility = View.VISIBLE
}

fun View.hide() {
    if(visibility == View.VISIBLE)
        visibility = View.GONE
}

fun ImageView.load(path: String?) = Glide
    .with(this)
    .load(path)
    .into(this)


fun Fragment.setToolbarTitle(title: Int) {
    val m = (activity as AppCompatActivity) as MainActivity
    m.toobar_title.text = getString(title)
}

fun Fragment.showSnackbar(text: Int) {
    Snackbar.make(
        requireView().rootView,
        text,
        Snackbar.LENGTH_SHORT).show()
}
