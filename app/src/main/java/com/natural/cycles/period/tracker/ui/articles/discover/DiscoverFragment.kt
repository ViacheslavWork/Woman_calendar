package com.natural.cycles.period.tracker.ui.articles.discover

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.natural.cycles.period.tracker.R
import com.natural.cycles.period.tracker.databinding.FragmentDiscoverBinding
import com.natural.cycles.period.tracker.domain.model.ArticleType.*
import com.natural.cycles.period.tracker.ui.articles.ArticlesEvent
import com.natural.cycles.period.tracker.ui.articles.adapters.ArticlesRecyclerGroupAdapter
import com.natural.cycles.period.tracker.ui.articles.details.ArticleDetailsFragment
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber


class DiscoverFragment : Fragment(R.layout.fragment_discover), RecyclerView.OnItemTouchListener,
    Observer<ArticlesEvent> {
    private var _binding: FragmentDiscoverBinding? = null
    private val binding get() = _binding!!

    private val articleEvent: MutableLiveData<ArticlesEvent> = MutableLiveData()

    private val reproductiveHealthAdapter = ArticlesRecyclerGroupAdapter(articleEvent)
    private val sexAdapter = ArticlesRecyclerGroupAdapter(articleEvent)
    private val yourCyclePhaseAdapter = ArticlesRecyclerGroupAdapter(articleEvent)
    private val theLatestAdapter = ArticlesRecyclerGroupAdapter(articleEvent)
    private val lgbtqAdapter = ArticlesRecyclerGroupAdapter(articleEvent)
    private val tipsAdapter = ArticlesRecyclerGroupAdapter(articleEvent)
    private val nutritionAdapter = ArticlesRecyclerGroupAdapter(articleEvent)

    private val viewModel: DiscoverViewModel by viewModel()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentDiscoverBinding.bind(view)
        setUpRecyclers()
        observeItems()
        observeItemEvents()
    }

    private fun observeItemEvents() {
        reproductiveHealthAdapter.event.observe(viewLifecycleOwner, this)
        sexAdapter.event.observe(viewLifecycleOwner, this)
        yourCyclePhaseAdapter.event.observe(viewLifecycleOwner, this)
        theLatestAdapter.event.observe(viewLifecycleOwner, this)
        lgbtqAdapter.event.observe(viewLifecycleOwner, this)
        tipsAdapter.event.observe(viewLifecycleOwner, this)
        nutritionAdapter.event.observe(viewLifecycleOwner, this)
    }

    private fun observeItems() {
        /*viewModel.articleGroups.observe(viewLifecycleOwner) {
            reproductiveHealthAdapter.submitList(it.filter { item -> item.parentGroupType == REPRODUCTIVE_HEALTH })
            sexAdapter.submitList(it.filter { item -> item.parentGroupType == SEX })
            yourCyclePhaseAdapter.submitList(it.filter { item -> item.parentGroupType == YOUR_CYCLE_PHASE })
            theLatestAdapter.submitList(it.filter { item -> item.parentGroupType == THE_LATEST })
            lgbtqAdapter.submitList(it.filter { item -> item.parentGroupType == LGBTQ })
            tripsAdapter.submitList(it.filter { item -> item.parentGroupType == TRIPS })
            nutritionAdapter.submitList(it.filter { item -> item.parentGroupType == NUTRITION })
        }*/
        lifecycleScope.launchWhenStarted {
            viewModel.articlesStateFlow.collectLatest {
                Timber.d(it.toString())
                reproductiveHealthAdapter.submitList(it.filter { item -> item.parentType == REPRODUCTIVE_HEALTH })
                sexAdapter.submitList(it.filter { item -> item.parentType == SEX })
                yourCyclePhaseAdapter.submitList(it.filter { item -> item.parentType == YOUR_CYCLE_PHASE })
                theLatestAdapter.submitList(it.filter { item -> item.parentType == THE_LATEST })
                lgbtqAdapter.submitList(it.filter { item -> item.parentType == LGBTQ })
                tipsAdapter.submitList(it.filter { item -> item.parentType == TIPS_FOR_FREE_PAIN })
                nutritionAdapter.submitList(it.filter { item -> item.parentType == NUTRITION_AND_FITNESS })
            }
        }
    }

    private fun setUpRecyclers() {
        binding.reproductiveHealthRv.apply {
            adapter = reproductiveHealthAdapter
            addOnItemTouchListener(this@DiscoverFragment)
        }
        binding.lgbtqRv.apply {
            adapter = lgbtqAdapter
            addOnItemTouchListener(this@DiscoverFragment)
        }
        binding.nutritionAndFitnessRv.apply {
            adapter = nutritionAdapter
            addOnItemTouchListener(this@DiscoverFragment)
        }
        binding.sexRv.apply {
            adapter = sexAdapter
            addOnItemTouchListener(this@DiscoverFragment)
        }
        binding.theLatestRv.apply {
            adapter = theLatestAdapter
            addOnItemTouchListener(this@DiscoverFragment)
        }
        binding.tripsRv.apply {
            adapter = tipsAdapter
            addOnItemTouchListener(this@DiscoverFragment)
        }
        binding.yourCyclePhaseRv.apply {
            adapter = yourCyclePhaseAdapter
            addOnItemTouchListener(this@DiscoverFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onTouchEvent(view: RecyclerView, event: MotionEvent) {}

    override fun onInterceptTouchEvent(view: RecyclerView, event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                view.parent?.requestDisallowInterceptTouchEvent(true)
            }
        }
        return false
    }

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}

    override fun onChanged(event: ArticlesEvent?) {
        viewModel.handleEvent(event)
        findNavController().navigate(
            R.id.action_navigation_articles_to_articleDetailsFragment,
            bundleOf(ArticleDetailsFragment.ARG_ID to event?.id)
        )
    }
}