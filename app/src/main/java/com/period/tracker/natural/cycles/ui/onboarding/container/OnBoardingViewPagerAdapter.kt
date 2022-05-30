package com.period.tracker.natural.cycles.ui.onboarding.container

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.period.tracker.natural.cycles.ui.onboarding.container.fragments.*

class OnBoardingViewPagerAdapter(
    fragment: Fragment,
    val onBoardingContainerCallbacks: OnBoardingContainerCallbacks
) :
    FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 10
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return OnBoarding1Fragment(onBoardingContainerCallbacks)
            1 -> return OnBoarding2Fragment(onBoardingContainerCallbacks)
            2 -> return OnBoarding3Fragment(onBoardingContainerCallbacks)
            3 -> return OnBoarding4Fragment(onBoardingContainerCallbacks)
            4 -> return OnBoarding5Fragment(onBoardingContainerCallbacks)
            5 -> return OnBoarding6Fragment(onBoardingContainerCallbacks)
            6 -> return OnBoarding7Fragment(onBoardingContainerCallbacks)
            7 -> return OnBoarding8Fragment(onBoardingContainerCallbacks)
            8 -> return OnBoarding9Fragment(onBoardingContainerCallbacks)
            9 -> return OnBoarding10Fragment(onBoardingContainerCallbacks)
        }
        return Fragment()
    }
}