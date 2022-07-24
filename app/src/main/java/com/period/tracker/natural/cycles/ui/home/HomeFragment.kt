package com.period.tracker.natural.cycles.ui.home

import android.os.Bundle
import android.util.TypedValue
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.period.tracker.natural.cycles.R
import com.period.tracker.natural.cycles.databinding.FragmentHomeBinding
import com.period.tracker.natural.cycles.databinding.ItemHomeFragmentCycleBinding
import com.period.tracker.natural.cycles.databinding.ItemWeekDayBinding
import com.period.tracker.natural.cycles.domain.model.Cycle
import com.period.tracker.natural.cycles.domain.model.CycleStatus
import com.period.tracker.natural.cycles.domain.model.StateOfDay.*
import com.period.tracker.natural.cycles.preferences.BookmarksPreferences
import com.period.tracker.natural.cycles.preferences.FirstRunPreferences
import com.period.tracker.natural.cycles.ui.MainViewModel
import com.period.tracker.natural.cycles.ui.calendar.CalendarFragment
import com.period.tracker.natural.cycles.ui.calendar.CalendarState
import com.period.tracker.natural.cycles.ui.subscription.SubscriptionFragment
import com.period.tracker.natural.cycles.ui.symptoms.SymptomsFragment
import com.period.tracker.natural.cycles.utils.Constants
import com.period.tracker.natural.cycles.utils.LocalDateHelper
import com.period.tracker.natural.cycles.utils.LocalDateHelper.getMonthName
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.threeten.bp.Instant
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import timber.log.Timber
import java.util.*

class HomeFragment : Fragment(R.layout.fragment_home) {
    private val viewModel: HomeViewModel by viewModel()
    private val mainViewModel: MainViewModel by sharedViewModel()
    private val bookmarksPreferences: BookmarksPreferences by inject()
    private val firstRunPreferences: FirstRunPreferences by inject()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var symptomsRecyclerView: RecyclerView
    private lateinit var symptomsAdapter: HomeSymptomsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.downloadDaysFromFirebase()
    }

    override fun onStart() {
        viewModel.updateUI()
        firstRunPreferences.setIsFirstRun(false)
        super.onStart()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentHomeBinding.bind(view)

        setUpUI()
        setUpListeners()
        setUpSymptomsRecycler()

        observeFirebaseDays()
        observeWeek()
        observeLastCycles()
        observeCountOfPeriods()
        observeSymptoms()
        observePremiumStatus()
    }

    private fun observePremiumStatus() {
        mainViewModel.isPremium.observe(viewLifecycleOwner) {
            Firebase.auth.currentUser?.metadata?.creationTimestamp
                ?.let { creationTimeInMillis ->
                    LocalDateTime.ofInstant(
                        Instant.ofEpochMilli(creationTimeInMillis),
                        ZoneId.systemDefault()
                    )
                        .toLocalDate()
                }
                ?.let { creationDate ->
                    if (LocalDate.now()
                            .isBefore(creationDate.plusDays(Constants.TRIAL_LENGTH_DAYS))
                    )
                        return@observe
                }

            if (!it) {
                findNavController().navigate(
                    R.id.action_navigation_home_to_subscriptionFragment,
                    bundleOf(SubscriptionFragment.ARG_HAS_TOOL_BAR to false)
                )
            }

        }
    }

    private fun observeFirebaseDays() {
        viewModel.isDaysFromFirebaseDownloaded.observe(viewLifecycleOwner) {
            viewModel.updateUI()
        }
    }

    private fun setUpSymptomsRecycler() {
        symptomsRecyclerView = binding.symptomsRv
        symptomsAdapter = HomeSymptomsAdapter()
        symptomsRecyclerView.layoutManager =
            LinearLayoutManager(context).apply { orientation = RecyclerView.HORIZONTAL }
        symptomsRecyclerView.adapter = symptomsAdapter
    }

    private fun observeSymptoms() {
        viewModel.symptoms.observe(viewLifecycleOwner) {
            symptomsAdapter.submitList(it)
        }
    }

    private fun observeWeek() {
        viewModel.weekDays.observe(viewLifecycleOwner) {
            getWeekItemsBinding().forEachIndexed { index, itemWeekDayBinding ->
                val date = it[index].date
                itemWeekDayBinding.weekDayTv.text = it[index].dayOfWeek
                itemWeekDayBinding.dateTv.text = it[index].numOfDay
                it[index].stateOfDay.let { stateOfDay ->
                    itemWeekDayBinding.dateTv.setTextColor(
                        resources.getColor(
                            R.color.text_color,
                            null
                        )
                    )
                    itemWeekDayBinding.root.background =
                        ResourcesCompat.getDrawable(resources, android.R.color.transparent, null)
                    if (date == LocalDate.now()) {
                        itemWeekDayBinding.root.background =
                            ResourcesCompat.getDrawable(
                                resources,
                                R.drawable.bg_today,
                                null
                            )
                    }
                    when (stateOfDay) {
                        FERTILE -> {
                            itemWeekDayBinding.dateTv.setTextColor(binding.root.resources.getColor(R.color.green))
                            if (date == LocalDate.now()) {
                                itemWeekDayBinding.root.background =
                                    ResourcesCompat.getDrawable(
                                        resources,
                                        R.drawable.bg_today,
                                        null
                                    )
                            }
                        }
                        PERIOD -> {
                            if (date == LocalDate.now()) {
                                itemWeekDayBinding.root.background = ResourcesCompat.getDrawable(
                                    resources,
                                    R.drawable.bg_today_period_day,
                                    null
                                )
                                itemWeekDayBinding.dateTv.setTextColor(
                                    resources.getColor(
                                        R.color.pink,
                                        null
                                    )
                                )
                            } else {
                                itemWeekDayBinding.root.background =
                                    ResourcesCompat.getDrawable(
                                        resources,
                                        R.color.pink,
                                        null
                                    )
                                itemWeekDayBinding.dateTv.setTextColor(
                                    binding.root.resources.getColor(
                                        R.color.white
                                    )
                                )
                            }
                        }
                        OVULATION -> {
                            itemWeekDayBinding.root.background =
                                if (date == LocalDate.now()) {
                                    ResourcesCompat.getDrawable(
                                        resources,
                                        R.drawable.bg_today_ovulation_day,
                                        null
                                    )
                                } else {
                                    ResourcesCompat.getDrawable(
                                        resources,
                                        R.drawable.bg_border_dashed_green,
                                        null
                                    )
                                }
                            itemWeekDayBinding.dateTv.setTextColor(
                                resources.getColor(
                                    R.color.green,
                                    null
                                )
                            )
                        }
                        EXPECTED_NEW_PERIOD -> {
                            if (date.isBefore(LocalDate.now())) {
                                itemWeekDayBinding.root.background =
                                    ResourcesCompat.getDrawable(
                                        resources,
                                        R.color.gray4,
                                        null
                                    )
                                itemWeekDayBinding.dateTv.setTextColor(
                                    binding.root.resources.getColor(
                                        R.color.white
                                    )
                                )
                            } else {
                                itemWeekDayBinding.root.background =
                                    if (date == LocalDate.now()) {
                                        ResourcesCompat.getDrawable(
                                            resources,
                                            R.drawable.bg_today_expected_day,
                                            null
                                        )
                                    } else {
                                        ResourcesCompat.getDrawable(
                                            resources,
                                            R.drawable.bg_border_dashed_pink,
                                            null
                                        )
                                    }
                                itemWeekDayBinding.dateTv.setTextColor(
                                    binding.root.resources.getColor(
                                        R.color.pink
                                    )
                                )
                            }
                        }
                        PRE_PERIOD -> TODO()
                        DELAY -> TODO()
                    }
                }
            }
        }
    }

    private fun observeLastCycles() {
        viewModel.lastCycles.observe(viewLifecycleOwner) {
            if (it.isEmpty()) return@observe

            updateStatus(it[0])

            getPrevCycleFields().forEach { itemHomeFragmentCycleBinding ->
                itemHomeFragmentCycleBinding.root.visibility = View.GONE
            }
            it.forEachIndexed { index, cycle ->
                val itemBinding = getPrevCycleFields()[index]
                itemBinding.root.visibility = View.VISIBLE
                itemBinding.rightArrowBtn.setOnClickListener {
                    findNavController().navigate(
                        R.id.action_navigation_home_to_calendarFragment,
                        bundleOf(
                            CalendarFragment.ARG_DATE_MONTH_FOR_SCROLL
                                    to LocalDateHelper.getByMonth(
                                cycle.start.year,
                                cycle.start.month
                            ),
                            CalendarFragment.ARG_CALENDAR_STATE to CalendarState.OPEN_INFO_BY_CLICK
                        )
                    )
                }
                if (cycle.finish == null) {
                    itemBinding.titleTv.text = String.format(
                        resources.getString(
                            R.string.current_cycle_nn_days,
                            cycle.getDaysAfterStartOfPeriod().toString()
                        )
                    )
                    itemBinding.dateTv.text = String.format(
                        resources.getString(R.string.started_month_dd_yyyy),
                        cycle.start.getMonthName(),
                        cycle.start.dayOfMonth,
                        cycle.start.year
                    )
                } else {
                    itemBinding.titleTv.text = String.format(
                        resources.getString(R.string.nn_days, cycle.getLengthDays().toString())
                    )
                    itemBinding.dateTv.text = String.format(
                        resources.getString(R.string.month_dd_month_dd),
                        cycle.start.getMonthName(),
                        cycle.start.dayOfMonth,
                        cycle.finish.getMonthName(),
                        cycle.finish.dayOfMonth
                    )
                }

            }
        }
    }

    private fun observeCountOfPeriods() {
        viewModel.countOfPeriods.observe(viewLifecycleOwner) {
            when (it) {
                in 0..2 -> {
                    binding.firstLogBlockTv.visibility = View.VISIBLE
                    binding.secondLogBlockTv.visibility = View.VISIBLE
                    binding.titleLogBlockTv.visibility = View.VISIBLE
                    binding.logBlockCl.background =
                        ResourcesCompat.getDrawable(resources, R.color.white_50, null)
                    if (it == 2) {
                        binding.firstLogBlockTv.visibility = View.GONE
                    }
                }
                in 2..Int.MAX_VALUE -> {
                    binding.firstLogBlockTv.visibility = View.GONE
                    binding.secondLogBlockTv.visibility = View.GONE
                    binding.titleLogBlockTv.visibility = View.GONE
                    binding.logBlockCl.background =
                        ResourcesCompat.getDrawable(resources, android.R.color.transparent, null)
                }
            }
        }
    }

    private fun updateStatus(lastCycle: Cycle) {
        binding.logPeriodBtn.text = getString(R.string.log_period)
        binding.statusTitleTv.visibility = View.VISIBLE
        binding.statusDaysTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 36f)
        binding.statusDaysTv.setTextColor(resources.getColor(R.color.text_color, null))
        when (lastCycle.currentCycleStatus) {
            CycleStatus.PERIOD -> {
                binding.statusCl.background = ResourcesCompat.getDrawable(
                    resources,
                    R.color.pale_red,
                    null
                )
                binding.statusTitleTv.text = getString(R.string.period)
                binding.statusDaysTv.text = String.format(
                    getString(R.string.nn_day),
                    lastCycle.getDaysAfterStartOfPeriod().toString()
                )
                binding.logPeriodBtn.text = getString(R.string.edit_period_dates)
            }
            CycleStatus.FERTILE_BEFORE_OVULATION -> {
                binding.statusCl.background = ResourcesCompat.getDrawable(
                    resources,
                    R.color.green,
                    null
                )
                binding.statusTitleTv.text = getString(R.string.ovulation_in)
                binding.statusDaysTv.text = String.format(
                    getString(R.string.nn_days),
                    lastCycle.getDaysBeforeOvulation().toString()
                )
            }
            CycleStatus.FERTILE_AFTER_OVULATION -> {
                binding.statusCl.background = ResourcesCompat.getDrawable(
                    resources,
                    R.color.green,
                    null
                )
                binding.statusTitleTv.text = getString(R.string.current_cycle)
                binding.statusDaysTv.text = String.format(
                    getString(R.string.nn_days),
                    lastCycle.getDaysAfterStartOfPeriod().toString()
                )
            }
            CycleStatus.EXPECTED_NEW_PERIOD -> {
                binding.statusCl.background = ResourcesCompat.getDrawable(
                    resources,
                    R.color.beige,
                    null
                )
                binding.statusTitleTv.visibility = View.GONE
                binding.statusDaysTv.text = getString(R.string.period_may_start_today)
                binding.statusDaysTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24f)
                binding.statusDaysTv.setTextColor(resources.getColor(R.color.pale_red, null))
            }
            null -> {
                binding.statusCl.background = ResourcesCompat.getDrawable(
                    resources,
                    R.color.beige,
                    null
                )
                binding.statusTitleTv.text = getString(R.string.current_cycle)
                binding.statusDaysTv.text = String.format(
                    getString(R.string.nn_days),
                    lastCycle.getDaysAfterStartOfPeriod().toString()
                )
            }
        }
    }

    private fun getPrevCycleFields(): List<ItemHomeFragmentCycleBinding> {
        return mutableListOf<ItemHomeFragmentCycleBinding>()
            .apply {
                add(binding.currentCycleItem)
                add(binding.prevCycle1Item)
                add(binding.prevCycle2Item)
            }.toList()
    }

    private fun setUpUI() {
        binding.toolBarCalendarBtn.text = LocalDate.now().month.toString()
            .lowercase(Locale.getDefault())
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
    }

    private fun getWeekItemsBinding(): List<ItemWeekDayBinding> {
        return mutableListOf<ItemWeekDayBinding>()
            .apply {
                add(binding.mondayItem)
                add(binding.tuesdayItem)
                add(binding.wednesdayItem)
                add(binding.thursdayItem)
                add(binding.fridayItem)
                add(binding.saturdayItem)
                add(binding.sundayItem)
            }.toList()
    }

    private fun setUpListeners() {
        binding.bellIb.setOnClickListener {
            Timber.d(bookmarksPreferences.getBookmarks().toString())
        }
        binding.menuIb.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionNavigationHomeToSettingsFragment())
        }
        binding.logPeriodBtn.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionNavigationHomeToCalendarFragment())
        }
        binding.logPrevCycleBtn.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionNavigationHomeToCalendarFragment())
        }
        binding.plusCycleIb.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionNavigationHomeToCalendarFragment())
        }
        binding.toolBarCalendarBtn.setOnClickListener {
            findNavController().navigate(
                R.id.action_navigation_home_to_calendarFragment,
                bundleOf(CalendarFragment.ARG_CALENDAR_STATE to CalendarState.OPEN_INFO_BY_CLICK)
            )
        }
        binding.plusSymptomIb.setOnClickListener {
            findNavController().navigate(
                R.id.action_navigation_home_to_symptomsFragment,
                bundleOf(SymptomsFragment.ARG_DATE to LocalDate.now())
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}