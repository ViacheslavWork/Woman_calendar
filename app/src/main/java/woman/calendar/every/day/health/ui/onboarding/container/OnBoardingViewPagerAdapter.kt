package woman.calendar.every.day.health.ui.onboarding.container

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import woman.calendar.every.day.health.ui.onboarding.container.fragments.*

class OnBoardingViewPagerAdapter(fragment: Fragment) :
    FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 10
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return OnBoarding1Fragment()
            1 -> return OnBoarding2Fragment()
            2 -> return OnBoarding3Fragment()
            3 -> return OnBoarding4Fragment()
            4 -> return OnBoarding5Fragment()
            5 -> return OnBoarding6Fragment()
            6 -> return OnBoarding7Fragment()
            7 -> return OnBoarding8Fragment()
            8 -> return OnBoarding9Fragment()
            9 -> return OnBoarding10Fragment()
        }
        return Fragment()
    }
}