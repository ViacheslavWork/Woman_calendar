package com.natural.cycles.period.tracker.ui.articles.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.natural.cycles.period.tracker.ui.articles.ArticlesTab
import com.natural.cycles.period.tracker.ui.articles.discover.DiscoverFragment
import com.natural.cycles.period.tracker.ui.articles.recent.RecentFragment
import com.natural.cycles.period.tracker.ui.articles.saved.SavedFragment

class ArticlesViewPagerAdapter(fragment: Fragment, private val tabs: List<ArticlesTab>) :
    FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return tabs.size
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            ArticlesTab.DISCOVER.ordinal -> return DiscoverFragment()
            ArticlesTab.SAVED.ordinal -> return SavedFragment()
            ArticlesTab.RECENT.ordinal -> return RecentFragment()
        }
        return Fragment()
    }
}