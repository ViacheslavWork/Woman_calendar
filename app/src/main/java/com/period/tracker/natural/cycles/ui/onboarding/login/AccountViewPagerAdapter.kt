package com.period.tracker.natural.cycles.ui.onboarding.login

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class AccountViewPagerAdapter(
    fragment: Fragment
) :
    FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return AccountSignUpFragment()
            1 -> return AccountLogInFragment()
        }
        return Fragment()
    }
}