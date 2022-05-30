package com.period.tracker.natural.cycles.ui.articles.recent

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import com.period.tracker.natural.cycles.R
import com.period.tracker.natural.cycles.databinding.FragmentRecentBinding
import com.period.tracker.natural.cycles.ui.articles.ArticlesEvent
import com.period.tracker.natural.cycles.ui.articles.adapters.ArticlesRecyclerAdapter
import com.period.tracker.natural.cycles.ui.articles.details.ArticleDetailsFragment

class RecentFragment : Fragment(R.layout.fragment_recent) {
    private var _binding: FragmentRecentBinding? = null
    private val binding get() = _binding!!

    private val articleEvent: MutableLiveData<ArticlesEvent> = MutableLiveData()

    private val adapter = ArticlesRecyclerAdapter(articleEvent)
    private val viewModel: RecentViewModel by viewModel()
    override fun onResume() {
        viewModel.updateRecentArticles()
        super.onResume()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentRecentBinding.bind(view)
        binding.recentArticlesRv.adapter = adapter
//        binding.recentArticlesRv.layoutManager = LinearLayoutManager(context)
        observeItems()
        observeItemEvents()
    }

    private fun observeItemEvents() {
        articleEvent.observe(viewLifecycleOwner) {
            findNavController().navigate(
                R.id.action_navigation_articles_to_articleDetailsFragment,
                bundleOf(ArticleDetailsFragment.ARG_ID to it.id)
            )
        }
    }

    private fun observeItems() {
        lifecycleScope.launchWhenStarted {
            viewModel.recentArticlesFlow.collectLatest {
                Timber.d(it.toString())
                adapter.submitList(it)
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}