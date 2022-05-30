package com.period.tracker.natural.cycles.ui.onboarding

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.period.tracker.natural.cycles.R
import com.period.tracker.natural.cycles.databinding.FragmentMoreAccuratePredictionsBinding
import com.period.tracker.natural.cycles.ui.calendar.CalendarFragment
import com.period.tracker.natural.cycles.ui.calendar.CalendarState

class MoreAccuratePredictionsFragment : Fragment(R.layout.fragment_more_accurate_predictions) {
    private var _binding: FragmentMoreAccuratePredictionsBinding? = null
    private val binding get() = _binding!!
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentMoreAccuratePredictionsBinding.bind(view)
        binding.continueBtn.setOnClickListener {
            findNavController().navigate(
                R.id.action_moreAccuratePredictionsFragment_to_calendarFragment,
                bundleOf(CalendarFragment.ARG_CALENDAR_STATE to CalendarState.PRE_PERIOD_SELECTION_FROM_ON_BOARDING)
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}