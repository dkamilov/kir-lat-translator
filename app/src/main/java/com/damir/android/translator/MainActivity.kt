package com.damir.android.translator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(savedInstanceState == null) {
            loadFragment(KirLatFragment())
        }
        setBottomNav()
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
                R.id.kirlat -> {
                    loadFragment(KirLatFragment())
                    true
                }
                else -> false

            }
        }
        bottom_nav.setOnNavigationItemReselectedListener {

        }
    }
}
