package com.damir.android.translator.utils

import android.app.Activity
import android.content.Context
import android.util.TypedValue
import com.damir.android.translator.R

const val THEME_DAY = 0
const val THEME_NIGHT = 1
const val PREFS_THEME_KEY = "PREFS_THEME_KEY"

class ThemeUtils {

    companion object {

        fun applyTheme(activity: Activity) {
            if(activity.isThemeNight) {
                activity.setTheme(R.style.AppThemeNight)
            } else {
                activity.setTheme(R.style.AppTheme)
            }
        }

        fun saveThemeModeToPrefs(activity: Activity) {
            val sharedPref = activity.getSharedPreferences(
                activity.getString(R.string.shared_prefs), Context.MODE_PRIVATE)
            with(sharedPref.edit()) {
                if(activity.isThemeNight)
                    putInt(PREFS_THEME_KEY, THEME_DAY)
                else
                    putInt(PREFS_THEME_KEY, THEME_NIGHT)
                commit()
            }
        }

        fun getAttrColor(context: Context, color: Int): Int {
            val typedValue = TypedValue()
            val theme = context.theme
            theme.resolveAttribute(color, typedValue, true)
            return typedValue.data
        }
    }
}