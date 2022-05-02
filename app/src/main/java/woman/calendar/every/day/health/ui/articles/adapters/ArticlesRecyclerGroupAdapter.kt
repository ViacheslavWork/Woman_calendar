package woman.calendar.every.day.health.ui.articles.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import woman.calendar.every.day.health.databinding.ItemArticlesBigCardBinding
import woman.calendar.every.day.health.ui.articles.items.ArticleItem
import woman.calendar.every.day.health.ui.articles.ArticlesEvent

class ArticlesRecyclerGroupAdapter(
    val event: MutableLiveData<ArticlesEvent> = MutableLiveData(),
) : ListAdapter<ArticleItem, ArticleGroupHolder>(ArticleGroupCallbacks()) {

    lateinit var binding: ItemArticlesBigCardBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleGroupHolder {
        binding =
            ItemArticlesBigCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ArticleGroupHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleGroupHolder, position: Int) {
        getItem(position).let { holder.onBind(it, event) }
    }
}

class ArticleGroupHolder(private val binding: ItemArticlesBigCardBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun onBind(
        item: ArticleItem,
        event: MutableLiveData<ArticlesEvent>
    ) {
        binding.itemTv.text = item.title
        binding.root.setOnClickListener { event.postValue(ArticlesEvent.OnGroupClick(item.id)) }
    }
}

private class ArticleGroupCallbacks : DiffUtil.ItemCallback<ArticleItem>() {
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