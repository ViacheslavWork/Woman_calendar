package com.period.tracker.natural.cycles.ui.calendar

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.threeten.bp.LocalDate
import com.period.tracker.natural.cycles.R
import com.period.tracker.natural.cycles.databinding.FragmentCalendrarBinding
import com.period.tracker.natural.cycles.ui.calendar.CalendarState.*
import com.period.tracker.natural.cycles.ui.day_info.DayInfoFragment


class CalendarFragment : Fragment(R.layout.fragment_calendrar) {
    private var _binding: FragmentCalendrarBinding? = null
    private val binding get() = _binding!!
    private var isScrollToBottom = true
    private var isScrollToDate = false
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CalendarAdapter
    private val viewModel: CalendarViewModel by viewModel()
    private var dateForScroll: LocalDate? = null
    private var isAbleToDownloadNewMonth = true
    private var calendarState: CalendarState = PERIOD_SELECTION

    companion object {
        const val ARG_DATE_MONTH_FOR_SCROLL = "ARG_DATE_FOR_SCROLL"
        const val ARG_CALENDAR_STATE = "ARG_CALENDAR_STATE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dateForScroll = arguments?.getSerializable(ARG_DATE_MONTH_FOR_SCROLL) as LocalDate?
        dateForScroll?.let { isScrollToDate = true }
        arguments
            ?.takeIf { it.containsKey(ARG_CALENDAR_STATE) }
            ?.apply { calendarState = getSerializable(ARG_CALENDAR_STATE) as CalendarState }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentCalendrarBinding.bind(view)

        setUpUI()
        setUpRecyclerView()
        setUpListeners()

        observeListEvent()
        observeMonths()
        observeCountOfPeriods()
    }

    private fun observeCountOfPeriods() {
        viewModel.countOfPeriods.observe(viewLifecycleOwner) {
            if (calendarState == PERIOD_SELECTION_FROM_ON_BOARDING) {
                binding.continueBtn.visibility = if (it > 0) VISIBLE else GONE
            }
        }
    }

    private fun setUpUI() {
        when (calendarState) {
            PERIOD_SELECTION_FROM_ON_BOARDING -> {
                setUpOnBoardingUiState(getString(R.string.dates_are_auto_filled_tap_them_to_adjust))
            }
            PRE_PERIOD_SELECTION_FROM_ON_BOARDING -> {
                setUpOnBoardingUiState(getString(R.string.you_can_log_previous_periods_here))
                binding.continueBtn.visibility = VISIBLE
            }
            OPEN_INFO_BY_CLICK -> Unit
            PERIOD_SELECTION -> Unit
        }
    }

    private fun setUpOnBoardingUiState(title: String) {
        binding.toolBar.visibility = GONE
        binding.crossIb.visibility = GONE
        binding.title.visibility = VISIBLE
        binding.title.text = title
    }

    private fun setUpListeners() {
        binding.crossIb.setOnClickListener { findNavController().popBackStack() }
        binding.continueBtn.setOnClickListener {
            when (calendarState) {
                PERIOD_SELECTION_FROM_ON_BOARDING -> findNavController().navigate(
                    CalendarFragmentDirections.actionCalendarFragmentToMoreAccuratePredictionsFragment()
                )
                PRE_PERIOD_SELECTION_FROM_ON_BOARDING -> findNavController().navigate(
                    CalendarFragmentDirections.actionCalendarFragmentToSubscriptionFragment()
                )
                OPEN_INFO_BY_CLICK -> Unit
                PERIOD_SELECTION -> Unit
            }
        }
    }

    private fun observeMonths() {
        viewModel.months.observe(viewLifecycleOwner) {
            isAbleToDownloadNewMonth = true
            adapter.submitList(it)
            scrollIfNeeded(it)
        }
    }

    private fun scrollIfNeeded(it: List<ItemMonth>) {
        if (isScrollToDate) {
            isScrollToBottom = false
            isScrollToDate = false
            it.forEachIndexed { index, itemMonth ->
                if (itemMonth.date.year == dateForScroll?.year
                    && itemMonth.date.month == dateForScroll?.month
                ) {
                    recyclerView.scrollToPosition(index)
                    return
                }
            }
        }

        if (isScrollToBottom) {
            isScrollToBottom = false
            recyclerView.scrollToPosition(it.size - 1)
        }
    }

    private fun observeListEvent() {
        adapter.event.observe(viewLifecycleOwner) {
            if (calendarState == OPEN_INFO_BY_CLICK) {
                findNavController().navigate(
                    R.id.action_calendarFragment_to_dayInfoFragment,
                    bundleOf(DayInfoFragment.ARG_DATE to it.day.date)
                )
            } else {
                viewModel.handleEvent(it)
            }
        }
    }

    private fun setUpRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(context)
        recyclerView = binding.calendarRv
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (linearLayoutManager.findFirstVisibleItemPosition() == 0) {
                    if (isAbleToDownloadNewMonth) {
                        isAbleToDownloadNewMonth = false
                        viewModel.getPrevMonth()
                    }
                }
            }
        })
        adapter =
            CalendarAdapter(isChangeColorByClick = calendarState != OPEN_INFO_BY_CLICK)
        recyclerView.adapter = adapter
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}