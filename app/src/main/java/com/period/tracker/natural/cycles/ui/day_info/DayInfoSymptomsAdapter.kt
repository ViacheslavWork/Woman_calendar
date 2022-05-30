package com.period.tracker.natural.cycles.ui.day_info

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import timber.log.Timber
import com.period.tracker.natural.cycles.databinding.ItemSymptomDayInfoBinding
import com.period.tracker.natural.cycles.ui.symptoms.SymptomItem

class DayInfoSymptomsAdapter() : ListAdapter<SymptomItem, SymptomHolder>(SymptomCallbacks()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SymptomHolder {
        val binding =
            ItemSymptomDayInfoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return SymptomHolder(binding)
    }

    override fun onBindViewHolder(holder: SymptomHolder, position: Int) {
        getItem(position).let { holder.onBind(it) }
    }
}

class SymptomHolder(private val binding: ItemSymptomDayInfoBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun onBind(
        item: SymptomItem
    ) {

        binding.circleView.background = ResourcesCompat.getDrawable(
            binding.root.resources,
            item.symptomType.background,
            null
        )
        binding.imageView.setImageResource(item.image)
        binding.symptomTv.text = item.title
    }
}

private class SymptomCallbacks : DiffUtil.ItemCallback<SymptomItem>() {
    override fun areItemsTheSame(
        oldItem: SymptomItem,
        newItem: SymptomItem
    ): Boolean {
        Timber.d(newItem.title)
        return (oldItem.title == newItem.title)
    }

    override fun areContentsTheSame(
        oldItem: SymptomItem,
        newItem: SymptomItem
    ): Boolean =
        (oldItem.isChecked == newItem.isChecked)
}