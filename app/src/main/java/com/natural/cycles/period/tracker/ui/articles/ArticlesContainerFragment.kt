package com.natural.cycles.period.tracker.ui.articles

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.natural.cycles.period.tracker.R
import com.natural.cycles.period.tracker.databinding.FragmentArticlesBinding
import com.natural.cycles.period.tracker.databinding.ItemArticlesTabBinding
import com.natural.cycles.period.tracker.domain.usecase.firebase.bookmarks.DownloadArticlesBookmarksFromFirebaseUseCase
import com.natural.cycles.period.tracker.ui.articles.adapters.ArticlesViewPagerAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ArticlesContainerFragment : Fragment(R.layout.fragment_articles) {
    private var _binding: FragmentArticlesBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private val viewModel: ArticlesViewModel by sharedViewModel()
    private val downloadArticlesBookmarksFromFirebaseUseCase: DownloadArticlesBookmarksFromFirebaseUseCase by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch(Dispatchers.IO) {
            downloadArticlesBookmarksFromFirebaseUseCase.execute()
        }
    }

    override fun onResume() {
        viewModel.onContainerResume()
        super.onResume()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentArticlesBinding.bind(view)

        setUpViewPager()
        setUpTubLayout()
        setData()
        setUpListeners()
    }

    private fun setUpListeners() {
        binding.menuIb.setOnClickListener {
            findNavController().navigate(
                ArticlesContainerFragmentDirections.actionNavigationArticlesToSettingsFragment()
            )
        }
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