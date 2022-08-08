package com.natural.cycles.period.tracker.ui.onboarding

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.natural.cycles.period.tracker.R
import com.natural.cycles.period.tracker.databinding.FragmentStayOnTopOfHealthBinding
import com.natural.cycles.period.tracker.ui.calendar.CalendarFragment
import com.natural.cycles.period.tracker.ui.calendar.CalendarState

class StayOnTopOfHealthFragment : Fragment(R.layout.fragment_stay_on_top_of_health) {
    private var _binding: FragmentStayOnTopOfHealthBinding? = null
    private val binding get() = _binding!!
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentStayOnTopOfHealthBinding.bind(view)
        binding.continueBtn.setOnClickListener {
            findNavController().navigate(
                R.id.action_stayOnTopOfHealthFragment_to_calendarFragment,
                bundleOf(CalendarFragment.ARG_CALENDAR_STATE to CalendarState.PERIOD_SELECTION_FROM_ON_BOARDING)
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}