package com.period.tracker.natural.cycles.ui.articles.details

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.coroutines.flow.collectLatest
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.period.tracker.natural.cycles.R
import com.period.tracker.natural.cycles.databinding.FragmentArticleDetailsBinding
import com.period.tracker.natural.cycles.domain.model.ArticleTitleColor
import com.period.tracker.natural.cycles.ui.articles.ArticlesEvent
import com.period.tracker.natural.cycles.ui.articles.adapters.ArticlesRecyclerAdapter
import com.period.tracker.natural.cycles.preferences.BookmarksPreferences


class ArticleDetailsFragment : Fragment(R.layout.fragment_article_details) {
    private var _binding: FragmentArticleDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ArticleDetailsViewModel by viewModel()
    private val bookmarksPreferences: BookmarksPreferences by inject()
    private var articleId: Int = 0

    private val articleEvent: MutableLiveData<ArticlesEvent> = MutableLiveData()
    private val adapter = ArticlesRecyclerAdapter(articleEvent)

    companion object {
        const val ARG_ID = "ARG_ID"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.takeIf { it.containsKey(ARG_ID) }
            ?.apply {
                articleId = getInt(ARG_ID)
                viewModel.getArticle(articleId)
            }
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (viewModel.isArticleStackEmpty()) {
                    isEnabled = false
                    activity?.onBackPressed()
                } else {
                    viewModel.popParentArticle()
                }
            }
        })
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentArticleDetailsBinding.bind(view)
        binding.contentNsv.fullScroll(View.FOCUS_UP)

        setUpUi()
        setUpRecycler()
        setUpListeners()

        observeArticle()
        observeInternalArticles()
        observeItemEvents()
    }

    private fun setUpUi() {
        updateBookmarkButton()
    }

    private fun observeItemEvents() {
        adapter.event.observe(viewLifecycleOwner) {
            if (it is ArticlesEvent.OnArticleClick) {
                viewModel.putCurrentArticleToStack(articleId)
                viewModel.getArticle(it.id)
            }
        }
    }

    private fun observeArticle() {
        viewModel.article.observe(viewLifecycleOwner) {
            binding.contentNsv.fullScroll(View.FOCUS_UP)
            it?.let {
                articleId = it.id
                binding.titleTv.text = it.title
                when (it.titleColor) {
                    ArticleTitleColor.BLACK -> binding.titleTv.setTextColor(resources.getColor(R.color.text_color,null))
                    ArticleTitleColor.WHITE -> binding.titleTv.setTextColor(resources.getColor(R.color.white,null))
                }
                val text = HtmlCompat.fromHtml(
                    it.content,
                    HtmlCompat.FROM_HTML_MODE_COMPACT
                )
                binding.contentTv.text = text
                val url = it.bigImage.toString()
                val imageOption = RequestOptions()
                    .placeholder(R.drawable.im_placeholder)
                    .fallback(R.drawable.im_placeholder)
                Glide.with(requireContext())
                    .load(url)
//                    .apply(imageOption)
                    .into(binding.imageIv)
            }
        }
    }

    private fun observeInternalArticles() {
        lifecycleScope.launchWhenStarted {
            viewModel.internalArticlesFlow.collectLatest {
                adapter.submitList(it)
                binding.contentNsv.fullScroll(View.FOCUS_UP)
            }
        }
    }

    private fun setUpRecycler() {
        binding.articlesRv.adapter = adapter
    }

    private fun setUpListeners() {
        binding.crossIb.setOnClickListener { findNavController().popBackStack() }

        binding.bookmarkIb.setOnClickListener {
            if (bookmarksPreferences.getBookmarks().contains(articleId)) {
                bookmarksPreferences.removeBookmark(articleId)
            } else {
                bookmarksPreferences.putBookmark(articleId)
            }
            updateBookmarkButton()
        }
    }

    private fun updateBookmarkButton() {
        if (bookmarksPreferences.getBookmarks().contains(articleId)) {
            binding.bookmarkIb.setImageResource(R.drawable.ic_bookmark_full)
        } else {
            binding.bookmarkIb.setImageResource(R.drawable.ic_bookmark_empty)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}