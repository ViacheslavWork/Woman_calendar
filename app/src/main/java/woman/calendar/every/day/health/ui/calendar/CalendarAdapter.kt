package woman.calendar.every.day.health.ui.calendar

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.threeten.bp.LocalDate
import timber.log.Timber
import woman.calendar.every.day.health.R
import woman.calendar.every.day.health.databinding.ItemCalendarDayBinding
import woman.calendar.every.day.health.databinding.ItemCalendarMonthBinding
import woman.calendar.every.day.health.domain.model.Day
import woman.calendar.every.day.health.domain.model.StateOfDay.*

private const val TAG = "CalendarAdapter"
private val mapDateToAdapter = mutableMapOf<LocalDate, DayAdapter>()
private var timeStart = 0L

class CalendarAdapter(
    val event: MutableLiveData<CalendarEvent> = MutableLiveData(),
) : ListAdapter<ItemMonth, MonthHolder>(MonthDiffCallbacks()) {
    /*private val mapDateToAdapterPosition = mutableMapOf<LocalDate, Int>()
    fun getPositionByDate(date: LocalDate): Int? {
        Timber.d(mapDateToAdapterPosition.toString())
        return mapDateToAdapterPosition[date]
    }*/

    fun updateMonths(months: List<ItemMonth>) {
        months.forEach { mapDateToAdapter[it.date]?.submitList(it.daysWithStartDelay) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonthHolder {
        val binding =
            ItemCalendarMonthBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return MonthHolder(binding)
    }

    override fun onBindViewHolder(holder: MonthHolder, position: Int) {
        getItem(position).let {
            /*mapDateToAdapterPosition[LocalDateHelper.getByMonth(it.date.year, it.date.month)] =
                position*/
            holder.onBind(it, event)
        }
    }
}

class MonthHolder(private val binding: ItemCalendarMonthBinding) :
    RecyclerView.ViewHolder(binding.root) {
    var isDateInitialized = false
    fun onBind(
        item: ItemMonth,
        event: MutableLiveData<CalendarEvent>
    ) {
        binding.monthTv.text = item.title
        val dayAdapter = DayAdapter(event)
        mapDateToAdapter[item.date] = dayAdapter
        dayAdapter.submitList(item.daysWithStartDelay)
        binding.daysRv.adapter = dayAdapter
        binding.daysRv.setHasFixedSize(true)
    }
}

private class MonthDiffCallbacks : DiffUtil.ItemCallback<ItemMonth>() {
    override fun areItemsTheSame(
        oldItem: ItemMonth,
        newItem: ItemMonth
    ): Boolean =
        (oldItem.date.year == newItem.date.year) && (oldItem.date.month == newItem.date.month)

    override fun areContentsTheSame(
        oldItem: ItemMonth,
        newItem: ItemMonth
    ): Boolean {
        return (oldItem == newItem)
    }
}

class DayAdapter(
    private val event: MutableLiveData<CalendarEvent> = MutableLiveData(),
) : ListAdapter<ItemDay, DayHolder>(DayDiffCallbacks()) {

    lateinit var binding: ItemCalendarDayBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayHolder {
        binding =
            ItemCalendarDayBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return DayHolder(binding)
    }

    override fun onBindViewHolder(holder: DayHolder, position: Int) {
        getItem(position).let { holder.onBind(it, event) }
    }
}

class DayHolder(private val binding: ItemCalendarDayBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun onBind(
        item: ItemDay,
        event: MutableLiveData<CalendarEvent>
    ) {

        Timber.d("time between click and show ${System.currentTimeMillis() - timeStart}")
        item.stateOfDay?.let {
            when (it) {
                FERTILE -> {
                    binding.dateTv.setTextColor(
                        binding.root.resources.getColor(
                            R.color.green,
                            null
                        )
                    )
                }
                PERIOD -> {
                    binding.root.background =
                        ResourcesCompat.getDrawable(
                            binding.root.resources,
                            R.color.pink,
                            null
                        )
                    binding.dateTv.setTextColor(
                        binding.root.resources.getColor(
                            R.color.white,
                            null
                        )
                    )
                }
                OVULATION -> {
                    binding.root.background =
                        ResourcesCompat.getDrawable(
                            binding.root.resources,
                            R.drawable.bg_border_dashed_green,
                            null
                        )
                    binding.dateTv.setTextColor(
                        binding.root.resources.getColor(
                            R.color.green,
                            null
                        )
                    )
                }
                EXPECTED_NEW_PERIOD -> {
                    if (item.date?.isBefore(LocalDate.now()) == true) {
                        binding.root.background =
                            ResourcesCompat.getDrawable(
                                binding.root.resources,
                                R.color.gray4,
                                null
                            )
                        binding.dateTv.setTextColor(
                            binding.root.resources.getColor(
                                R.color.white,
                                null
                            )
                        )
                    } else {
                        binding.root.background =
                            ResourcesCompat.getDrawable(
                                binding.root.resources,
                                R.drawable.bg_border_dashed_pink,
                                null
                            )
                        binding.dateTv.setTextColor(
                            binding.root.resources.getColor(
                                R.color.pink,
                                null
                            )
                        )
                    }
                }
                PRE_PERIOD -> TODO()
                DELAY -> TODO()
            }
        }
        item.numOfDay?.let { binding.dateTv.text = it }

        item.date?.let { date ->
            if (date.isAfter(LocalDate.now())) return@let
            binding.root.setOnClickListener {
                timeStart = System.currentTimeMillis()
                if (item.stateOfDay != PERIOD) {
                    binding.root.background =
                        ResourcesCompat.getDrawable(
                            binding.root.resources,
                            R.color.pink,
                            null
                        )
                    binding.dateTv.setTextColor(binding.root.resources.getColor(R.color.white))

                    event.postValue(CalendarEvent.OnDayClick(Day(date, PERIOD)))
                } else {
                    binding.root.setBackgroundColor(Color.TRANSPARENT)
                    binding.dateTv.setTextColor(binding.root.resources.getColor(R.color.text_color))
                    event.postValue(CalendarEvent.OnDayClick(Day(date, null)))
                }
            }
        }
    }
}

private class DayDiffCallbacks : DiffUtil.ItemCallback<ItemDay>() {
    override fun areItemsTheSame(
        oldItem: ItemDay,
        newItem: ItemDay
    ): Boolean {
        return (oldItem.date == newItem.date)
    }

    override fun areContentsTheSame(
        oldItem: ItemDay,
        newItem: ItemDay
    ): Boolean {
        return (oldItem.stateOfDay == newItem.stateOfDay)
    }
}