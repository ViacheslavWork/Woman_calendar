package woman.calendar.every.day.health.ui.articles.details

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import woman.calendar.every.day.health.R
import woman.calendar.every.day.health.databinding.FragmentArticleDetailsBinding
import woman.calendar.every.day.health.ui.articles.ArticlesEvent
import woman.calendar.every.day.health.ui.articles.adapters.ArticlesRecyclerAdapter

class ArticleDetailsFragment : Fragment(R.layout.fragment_article_details) {
    private var _binding: FragmentArticleDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ArticleDetailsViewModel by viewModel()
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
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentArticleDetailsBinding.bind(view)

        setUpRecycler()
        setUpListeners()

        observeArticle()
        observeInternalArticles()
        observeItemEvents()
    }

    private fun observeItemEvents() {
        adapter.event.observe(viewLifecycleOwner){
            findNavController().navigate(
                R.id.action_articleDetailsFragment_self,
                bundleOf(ARG_ID to it?.id)
            )
        }
    }



    private fun observeArticle() {
        viewModel.article.observe(viewLifecycleOwner) {
            it?.let {
                binding.titleTv.text = it.title
            }
        }
    }

    private fun observeInternalArticles() {
        viewModel.internalArticles.observe(viewLifecycleOwner) {
            it?.let {
                adapter.submitList(it)
                Timber.d(it.toString())
            }
        }
    }

    private fun setUpRecycler() {
        binding.articlesRv.adapter = adapter
    }

    private fun setUpListeners() {
        binding.crossIb.setOnClickListener { findNavController().popBackStack() }

        binding.bookmarkIb.setOnClickListener {
            TODO()
            binding.bookmarkIb.setImageResource(R.drawable.ic_bookmark_full)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}