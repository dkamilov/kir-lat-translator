package com.damir.android.translator.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.damir.android.translator.R
import com.damir.android.translator.ui.fragment.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(savedInstanceState == null) {
            loadFragment(KirLatFragment())
        }
        setBottomNav()

        setSupportActionBar(toolbar_main)
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
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

    fun navigateToFavorite() {
        loadFragment(FavoritesFragment())
        bottom_nav.selectedItemId = R.id.favorite
    }
}
