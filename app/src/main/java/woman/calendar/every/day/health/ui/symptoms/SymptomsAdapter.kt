package woman.calendar.every.day.health.ui.symptoms

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import timber.log.Timber
import woman.calendar.every.day.health.R
import woman.calendar.every.day.health.databinding.ItemSymptomBigBinding

class SymptomsAdapter(
    private val event: MutableLiveData<SymptomEvent> = MutableLiveData(),
) : ListAdapter<SymptomItem, SymptomHolder>(SymptomCallbacks()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SymptomHolder {
        val binding =
            ItemSymptomBigBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return SymptomHolder(binding)
    }

    override fun onBindViewHolder(holder: SymptomHolder, position: Int) {
        getItem(position).let { holder.onBind(it, event) }
    }
}

class SymptomHolder(private val binding: ItemSymptomBigBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun onBind(
        item: SymptomItem,
        event: MutableLiveData<SymptomEvent>
    ) {
        var isChecked = item.isChecked
        if (item.isChecked) {
            binding.circleView.background = ResourcesCompat.getDrawable(
                binding.root.resources,
                R.drawable.bg_circle_red,
                null
            )
        } else {
            binding.circleView.background = ResourcesCompat.getDrawable(
                binding.root.resources,
                item.symptomType.background,
                null
            )
        }

        binding.imageView.setImageResource(item.image)
        binding.symptomTv.text = item.title
        binding.root.setOnClickListener {
            isChecked = !isChecked
            bindingAdapter?.notifyItemChanged(bindingAdapterPosition, item.apply { this.isChecked = isChecked })
            event.postValue(SymptomEvent.OnSymptomClick(item.apply { this.isChecked = isChecked }))
        }
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