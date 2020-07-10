package com.damir.android.translator.ui

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.damir.android.translator.ui.fragment.AllAccountStatisticFragment
import com.damir.android.translator.ui.fragment.MyAccountStatisticFragment

class AccountPagerAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {

    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment {
        Log.d("DamirK", "createFragment: pos $position")
        return when(position) {
            0 -> MyAccountStatisticFragment()
            1 -> AllAccountStatisticFragment()
            else -> MyAccountStatisticFragment()
        }
    }

}