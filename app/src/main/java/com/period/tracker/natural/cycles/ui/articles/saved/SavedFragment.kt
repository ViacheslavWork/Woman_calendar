package com.period.tracker.natural.cycles.ui.articles.saved

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import com.period.tracker.natural.cycles.R
import com.period.tracker.natural.cycles.databinding.FragmentSavedBinding
import com.period.tracker.natural.cycles.ui.articles.ArticlesEvent
import com.period.tracker.natural.cycles.ui.articles.ArticlesViewModel
import com.period.tracker.natural.cycles.ui.articles.adapters.ArticlesRecyclerAdapter
import com.period.tracker.natural.cycles.ui.articles.details.ArticleDetailsFragment

class SavedFragment : Fragment(R.layout.fragment_saved) {
    private var _binding: FragmentSavedBinding? = null
    private val binding get() = _binding!!

    private val articleEvent: MutableLiveData<ArticlesEvent> = MutableLiveData()

    private val adapter = ArticlesRecyclerAdapter(articleEvent)
    private val viewModel: SavedViewModel by viewModel()
    private val containerViewModel: ArticlesViewModel by sharedViewModel()

    override fun onResume() {
        viewModel.updateBookmarkArticles()
        super.onResume()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentSavedBinding.bind(view)
        binding.savedArticlesRv.adapter = adapter
        binding.savedArticlesRv.layoutManager = LinearLayoutManager(context)
        observeItems()
        observeItemEvents()
        observeContainer()
    }

    private fun observeContainer() {
        containerViewModel.containerResume.observe(viewLifecycleOwner){
            viewModel.updateBookmarkArticles()
        }
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
            viewModel.bookmarkArticlesFlow.collectLatest {
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