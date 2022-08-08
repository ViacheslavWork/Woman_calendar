package com.natural.cycles.period.tracker.ui.notes

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.threeten.bp.LocalDate
import com.natural.cycles.period.tracker.R
import com.natural.cycles.period.tracker.databinding.FragmentNotesBinding
import com.natural.cycles.period.tracker.ui.day_info.DayInfoFragment
import com.natural.cycles.period.tracker.utils.LocalDateHelper.getMonthName

class NotesFragment : Fragment(R.layout.fragment_notes) {
    companion object {
        const val ARG_DATE = "ARG_DATE"
    }

    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NotesViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments
            ?.takeIf { it.containsKey(DayInfoFragment.ARG_DATE) }
            ?.apply { viewModel.loadDay(getSerializable(DayInfoFragment.ARG_DATE) as LocalDate) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentNotesBinding.bind(view)
        observeDay()
        observeCycle()
        setUpListeners()
    }

    private fun setUpListeners() {
        binding.crossIb.setOnClickListener {
            if (binding.notesEt.text.isNotBlank()) {
                viewModel.saveNote(binding.notesEt.text.toString())
            }
            findNavController().popBackStack()
        }
    }

    private fun observeCycle() {
        viewModel.cycle.observe(viewLifecycleOwner) {
            binding.cycleDayToolbarTv.text = String.format(
                resources.getString(R.string.cycle_day_s),
                it?.getDaysAfterStartOfPeriod(arguments?.getSerializable(ARG_DATE) as LocalDate)
                    ?: "0"
            )
        }
    }

    private fun observeDay() {
        viewModel.day.observe(viewLifecycleOwner) {
            binding.dateToolbarTv.text = String.format(
                resources.getString(R.string.dd_month),
                it.date.dayOfMonth, it.date.getMonthName()
            )
            it.notes.let { notesText -> binding.notesEt.setText(notesText) }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}