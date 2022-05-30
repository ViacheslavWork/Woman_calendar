package com.period.tracker.natural.cycles.ui.articles

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import com.period.tracker.natural.cycles.R
import com.period.tracker.natural.cycles.databinding.FragmentArticlesBinding
import com.period.tracker.natural.cycles.databinding.ItemArticlesTabBinding
import com.period.tracker.natural.cycles.ui.articles.adapters.ArticlesViewPagerAdapter

class ArticlesContainerFragment : Fragment(R.layout.fragment_articles) {
    private var _binding: FragmentArticlesBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private val viewModel: ArticlesViewModel by sharedViewModel()
    override fun onResume() {
        viewModel.onContainerResume()
        super.onResume()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentArticlesBinding.bind(view)

        setUpViewPager()
        setUpTubLayout()
        setData()
    }

    private fun setData() {
        val tabs = ArticlesTab.values().toList()
        viewPager.adapter = ArticlesViewPagerAdapter(requireParentFragment(), tabs)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            val itemArticlesTabBinding =
                ItemArticlesTabBinding.inflate(LayoutInflater.from(context))
            tabs[position].icon?.let {
                itemArticlesTabBinding.iconIv.apply {
                    visibility = View.VISIBLE
                    setImageResource(it)
                }
            }
            itemArticlesTabBinding.titleTv.text = tabs[position].title
            itemArticlesTabBinding.tabCl.setOnClickListener { tab.select() }
            tab.customView = itemArticlesTabBinding.root
        }.attach()
    }

    private fun setUpViewPager() {
        viewPager = binding.articlesVp
    }

    private fun setUpTubLayout() {
        tabLayout = binding.tabLayout
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.view?.findViewById<ConstraintLayout>(R.id.tab_cl)
                    ?.background =
                    ResourcesCompat.getDrawable(resources, R.drawable.bg_tab_selected, null)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                tab?.view?.findViewById<ConstraintLayout>(R.id.tab_cl)
                    ?.background = ResourcesCompat.getDrawable(resources, R.drawable.bg_tab, null)
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}