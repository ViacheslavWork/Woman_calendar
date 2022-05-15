package woman.calendar.every.day.health.ui.articles.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import woman.calendar.every.day.health.ui.articles.ArticlesTab
import woman.calendar.every.day.health.ui.articles.discover.DiscoverFragment
import woman.calendar.every.day.health.ui.articles.recent.RecentFragment
import woman.calendar.every.day.health.ui.articles.saved.SavedFragment

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
        /*val pagerFragment = PagerFragment()
        pagerFragment.arguments = Bundle().apply {
            putSerializable(ARG_CATEGORY, tabs[position])
        }*/
        return Fragment()
    }
}