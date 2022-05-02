package woman.calendar.every.day.health.ui.articles.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import woman.calendar.every.day.health.ui.articles.ArticlesTab
import woman.calendar.every.day.health.ui.articles.discover.DiscoverFragment

class ArticlesViewPagerAdapter(fragment: Fragment, private val tabs: List<ArticlesTab>) :
    FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return tabs.size
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            ArticlesTab.DISCOVER.ordinal -> return DiscoverFragment()
            ArticlesTab.SAVED.ordinal -> {}
            ArticlesTab.RECENT.ordinal -> {}
        }
        /*val pagerFragment = PagerFragment()
        pagerFragment.arguments = Bundle().apply {
            putSerializable(ARG_CATEGORY, tabs[position])
        }*/
        return Fragment()
    }
}