package com.damir.android.translator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.damir.android.translator.ui.fragment.*
import com.damir.android.translator.utils.ThemeUtils
import com.damir.android.translator.utils.isThemeNight
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        ThemeUtils.applyTheme(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(savedInstanceState == null) {
            loadFragment(KirLatFragment())
        }
        setBottomNav()
        setThemeSwitcher()
        setSupportActionBar(toolbar_main)
        supportActionBar?.title = null
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }

    private fun setThemeSwitcher() {
        if(isThemeNight)
            img_switch_theme.setImageResource(R.drawable.ic_moon)
        img_switch_theme.setOnClickListener {
            switchTheme()
        }
    }

    private fun setBottomNav() {
        bottom_nav.selectedItemId = R.id.kirlat
        bottom_nav.setOnNavigationItemSelectedListener { item ->
            when(item.itemId) {
                R.id.translate -> loadFragment(TranslateFragment())
                R.id.favorite -> loadFragment(FavoritesFragment())
                R.id.kirlat -> loadFragment(KirLatFragment())
                R.id.search -> loadFragment(SearchFragment())
                R.id.account -> loadFragment(AccountFragment())
            }
            true
        }
        bottom_nav.setOnNavigationItemReselectedListener {
            return@setOnNavigationItemReselectedListener
        }
    }

    private fun switchTheme() {
        ThemeUtils.saveThemeModeToPrefs(this)
        recreate()
    }

    fun navigateToFavorite() {
        loadFragment(FavoritesFragment())
        bottom_nav.selectedItemId = R.id.favorite
    }
}
