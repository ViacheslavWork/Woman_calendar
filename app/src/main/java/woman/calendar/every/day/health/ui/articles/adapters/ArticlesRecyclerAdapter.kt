package woman.calendar.every.day.health.ui.articles.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import woman.calendar.every.day.health.databinding.ItemArticlesBinding
import woman.calendar.every.day.health.ui.articles.ArticlesEvent
import woman.calendar.every.day.health.ui.articles.items.ArticleItem

class ArticlesRecyclerAdapter(
    val event: MutableLiveData<ArticlesEvent> = MutableLiveData(),
) : ListAdapter<ArticleItem, ArticleHolder>(ArticleCallbacks()) {

    lateinit var binding: ItemArticlesBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleHolder {
        binding =
            ItemArticlesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ArticleHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleHolder, position: Int) {
        getItem(position).let { holder.onBind(it, event) }
    }
}

class ArticleHolder(private val binding: ItemArticlesBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun onBind(
        item: ArticleItem,
        event: MutableLiveData<ArticlesEvent>
    ) {
        binding.itemTv.text = item.title
        binding.root.setOnClickListener { event.postValue(ArticlesEvent.OnGroupClick(item.id)) }
    }
}

private class ArticleCallbacks : DiffUtil.ItemCallback<ArticleItem>() {
    override fun areItemsTheSame(
        oldItem: ArticleItem,
        newItem: ArticleItem
    ): Boolean =
        //TODO
        (oldItem.title == newItem.title)

    override fun areContentsTheSame(
        oldItem: ArticleItem,
        newItem: ArticleItem
    ): Boolean =
        (oldItem == newItem)
}