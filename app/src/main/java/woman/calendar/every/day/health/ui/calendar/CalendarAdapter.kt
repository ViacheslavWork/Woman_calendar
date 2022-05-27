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
    val isChangeColorByClick: Boolean = true,
    val event: MutableLiveData<CalendarEvent> = MutableLiveData(),
) : ListAdapter<ItemMonth, MonthHolder>(MonthDiffCallbacks()) {
    /*private val mapDateToAdapterPosition = mutableMapOf<LocalDate, Int>()
    fun getPositionByDate(date: LocalDate): Int? {
        Timber.d(mapDateToAdapterPosition.toString())
        return mapDateToAdapterPosition[date]
    }*/

    fun updateMonths(months: List<ItemMonth>) {
        Timber.d(months.toString())
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
            holder.onBind(it, event, isChangeColorByClick)
        }
    }
}

class MonthHolder(private val binding: ItemCalendarMonthBinding) :
    RecyclerView.ViewHolder(binding.root) {
    var isDateInitialized = false
    fun onBind(
        item: ItemMonth,
        event: MutableLiveData<CalendarEvent>,
        isChangeColorByClick: Boolean
    ) {
        binding.monthTv.text = item.title
        val dayAdapter = DayAdapter(event, isChangeColorByClick)
        mapDateToAdapter[item.date] = dayAdapter
        dayAdapter.submitList(item.daysWithStartDelay)
        binding.daysRv.adapter = dayAdapter
//        binding.daysRv.setHasFixedSize(true)
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
        return (oldItem.days == newItem.days)
    }
}

class DayAdapter(
    private val event: MutableLiveData<CalendarEvent> = MutableLiveData(),
    private val isChangeColorByClick: Boolean,
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
        getItem(position).let { holder.onBind(it, event, isChangeColorByClick) }
    }
}

class DayHolder(private val binding: ItemCalendarDayBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun onBind(
        item: ItemDay,
        event: MutableLiveData<CalendarEvent>,
        isChangeColorByClick: Boolean
    ) {

        if (System.currentTimeMillis() - timeStart < 1000) {
            Timber.d("time between click and show ${item.date} ${System.currentTimeMillis() - timeStart}")
        }
        if (item.date == LocalDate.now()) {
            binding.root.background =
                ResourcesCompat.getDrawable(
                    binding.root.resources,
                    R.drawable.bg_today,
                    null
                )
        }
        item.stateOfDay?.let {
            when (it) {
                FERTILE -> {
                    binding.dateTv.setTextColor(
                        binding.root.resources.getColor(
                            R.color.green,
                            null
                        )
                    )
                    if (item.date == LocalDate.now()) {
                        binding.root.background =
                            ResourcesCompat.getDrawable(
                                binding.root.resources,
                                R.drawable.bg_today,
                                null
                            )
                    }
                }
                PERIOD -> {
                    if (item.date == LocalDate.now()) {
                        binding.root.background = ResourcesCompat.getDrawable(
                            binding.root.resources,
                            R.drawable.bg_today_period_day,
                            null
                        )
                        binding.dateTv.setTextColor(
                            binding.root.resources.getColor(
                                R.color.pink,
                                null
                            )
                        )
                    } else {
                        binding.root.background = ResourcesCompat.getDrawable(
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
                }
                OVULATION -> {
                    binding.root.background = if (item.date == LocalDate.now()) {
                        ResourcesCompat.getDrawable(
                            binding.root.resources,
                            R.drawable.bg_today_ovulation_day,
                            null
                        )
                    } else {
                        ResourcesCompat.getDrawable(
                            binding.root.resources,
                            R.drawable.bg_border_dashed_green,
                            null
                        )
                    }
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
                        binding.root.background = if (item.date == LocalDate.now()) {
                            ResourcesCompat.getDrawable(
                                binding.root.resources,
                                R.drawable.bg_today_expected_day,
                                null
                            )
                        } else {
                            ResourcesCompat.getDrawable(
                                binding.root.resources,
                                R.drawable.bg_border_dashed_pink,
                                null
                            )
                        }
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
            binding.root.setOnClickListener {
//                if (item.date.isAfter(LocalDate.now())) return@setOnClickListener
                timeStart = System.currentTimeMillis()

                if (item.stateOfDay != PERIOD) {
                    if (isChangeColorByClick) {
                        if (item.date == LocalDate.now()) {
                            binding.root.background = ResourcesCompat.getDrawable(
                                binding.root.resources,
                                R.drawable.bg_today_period_day,
                                null
                            )
                            binding.dateTv.setTextColor(
                                binding.root.resources.getColor(
                                    R.color.pink,
                                    null
                                )
                            )
                        } else {
                            binding.root.background = ResourcesCompat.getDrawable(
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
                    }
                    event.postValue(CalendarEvent.OnDayClick(Day(date = date, stateOfDay = PERIOD)))
                } else {
                    if (isChangeColorByClick) {
                        binding.root.setBackgroundColor(Color.TRANSPARENT)
                        if (item.date == LocalDate.now()) {
                            binding.root.background = ResourcesCompat.getDrawable(
                                binding.root.resources,
                                R.drawable.bg_today,
                                null
                            )
                        } else {
                            binding.root.setBackgroundColor(Color.TRANSPARENT)
                        }
                        binding.dateTv.setTextColor(
                            binding.root.resources.getColor(
                                R.color.text_color,
                                null
                            )
                        )
                    }
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
        return (oldItem.date?.dayOfMonth == newItem.date?.dayOfMonth)
    }

    override fun areContentsTheSame(
        oldItem: ItemDay,
        newItem: ItemDay
    ): Boolean {
        return (oldItem.stateOfDay == newItem.stateOfDay)
    }
}