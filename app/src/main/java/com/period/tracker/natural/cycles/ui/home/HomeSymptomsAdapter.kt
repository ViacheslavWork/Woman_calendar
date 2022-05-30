package com.period.tracker.natural.cycles.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.period.tracker.natural.cycles.databinding.ItemSymptomSmallBinding

class HomeSymptomsAdapter :
    ListAdapter<HomeSymptomItem, HomeSymptomHolder>(HomeSymptomCallbacks()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeSymptomHolder {
        val binding =
            ItemSymptomSmallBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return HomeSymptomHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeSymptomHolder, position: Int) {
        getItem(position).let { holder.onBind(it) }
    }
}

class HomeSymptomHolder(private val binding: ItemSymptomSmallBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun onBind(item: HomeSymptomItem) {

        binding.root.background = ResourcesCompat.getDrawable(
            binding.root.resources,
            item.symptomType.background,
            null
        )
        binding.imageView.setImageResource(item.image)
    }
}

private class HomeSymptomCallbacks : DiffUtil.ItemCallback<HomeSymptomItem>() {
    override fun areItemsTheSame(
        oldItem: HomeSymptomItem,
        newItem: HomeSymptomItem
    ): Boolean =
        (oldItem.title == newItem.title)

    override fun areContentsTheSame(
        oldItem: HomeSymptomItem,
        newItem: HomeSymptomItem
    ): Boolean = (oldItem == newItem)
}