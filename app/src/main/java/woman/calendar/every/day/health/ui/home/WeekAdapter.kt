package woman.calendar.every.day.health.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.threeten.bp.LocalDate
import woman.calendar.every.day.health.R
import woman.calendar.every.day.health.databinding.ItemWeekDayBinding
import woman.calendar.every.day.health.domain.model.StateOfDay

class WeekAdapter : ListAdapter<ItemDayOfWeek, WeekDayHolder>(WeekDayCallbacks()) {

    private lateinit var binding: ItemWeekDayBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeekDayHolder {
        binding =
            ItemWeekDayBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return WeekDayHolder(binding)
    }

    override fun onBindViewHolder(holder: WeekDayHolder, position: Int) {
        getItem(position).let { holder.onBind(it) }
    }
}

class WeekDayHolder(private val binding: ItemWeekDayBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun onBind(item: ItemDayOfWeek) {
        binding.dateTv.text = item.numOfDay
        binding.weekDayTv.text = item.dayOfWeek
        item.stateOfDay?.let {
            when (it) {
                StateOfDay.FERTILE -> {
                    binding.dateTv.setTextColor(binding.root.resources.getColor(R.color.green))
                }
                StateOfDay.PERIOD -> {
                    binding.root.background =
                        ResourcesCompat.getDrawable(
                            binding.root.resources,
                            R.color.pink,
                            null
                        )
                    binding.dateTv.setTextColor(binding.root.resources.getColor(R.color.white))
                }
                StateOfDay.OVULATION -> {
                    binding.root.background =
                        ResourcesCompat.getDrawable(
                            binding.root.resources,
                            R.drawable.bg_border_dashed_green,
                            null
                        )
                    binding.dateTv.setTextColor(binding.root.resources.getColor(R.color.green))
                }
                StateOfDay.EXPECTED_NEW_PERIOD -> {
                    if (item.date.isBefore(LocalDate.now())) {
                        binding.root.background =
                            ResourcesCompat.getDrawable(
                                binding.root.resources,
                                R.color.gray4,
                                null
                            )
                        binding.dateTv.setTextColor(binding.root.resources.getColor(R.color.white))
                    } else {
                        binding.root.background =
                            ResourcesCompat.getDrawable(
                                binding.root.resources,
                                R.drawable.bg_border_dashed_pink,
                                null
                            )
                        binding.dateTv.setTextColor(binding.root.resources.getColor(R.color.pink))
                    }
                }
                StateOfDay.PRE_PERIOD -> TODO()
                StateOfDay.DELAY -> TODO()
            }
        }
    }
}

private class WeekDayCallbacks : DiffUtil.ItemCallback<ItemDayOfWeek>() {
    override fun areItemsTheSame(
        oldItem: ItemDayOfWeek,
        newItem: ItemDayOfWeek
    ): Boolean =
        (oldItem.date == newItem.date)

    override fun areContentsTheSame(
        oldItem: ItemDayOfWeek,
        newItem: ItemDayOfWeek
    ): Boolean =
        (oldItem == newItem)
}