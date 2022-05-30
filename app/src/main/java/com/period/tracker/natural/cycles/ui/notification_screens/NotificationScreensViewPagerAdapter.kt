package com.period.tracker.natural.cycles.ui.notification_screens

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.period.tracker.natural.cycles.ui.notification_screens.NotificationScreenType.*
import com.period.tracker.natural.cycles.ui.notification_screens.fragments.ChallengesFragment
import com.period.tracker.natural.cycles.ui.notification_screens.fragments.CircleFragment
import com.period.tracker.natural.cycles.ui.notification_screens.fragments.GraphFragment
import com.period.tracker.natural.cycles.ui.notification_screens.fragments.WinsFragment

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