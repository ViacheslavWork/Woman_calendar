package com.natural.cycles.period.tracker.ui.notification_screens

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.natural.cycles.period.tracker.ui.notification_screens.NotificationScreenType.*
import com.natural.cycles.period.tracker.ui.notification_screens.fragments.ChallengesFragment
import com.natural.cycles.period.tracker.ui.notification_screens.fragments.CircleFragment
import com.natural.cycles.period.tracker.ui.notification_screens.fragments.GraphFragment
import com.natural.cycles.period.tracker.ui.notification_screens.fragments.WinsFragment

class NotificationScreensViewPagerAdapter(
    fragment: Fragment,
    private val screens: List<NotificationScreenType>
) :
    FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return screens.size
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            CIRCLE.ordinal -> return CircleFragment()
            GRAPH.ordinal -> return GraphFragment()
            WINS.ordinal -> return WinsFragment()
            CHALLENGES.ordinal -> return ChallengesFragment()
        }
        return Fragment()
    }
}