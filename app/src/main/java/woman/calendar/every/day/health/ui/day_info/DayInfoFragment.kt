package woman.calendar.every.day.health.ui.day_info

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.threeten.bp.LocalDate
import woman.calendar.every.day.health.R
import woman.calendar.every.day.health.databinding.FragmentDayInfoBinding
import woman.calendar.every.day.health.ui.symptoms.SymptomItem
import woman.calendar.every.day.health.utils.LocalDateHelper.getMonthName

class DayInfoFragment : Fragment(R.layout.fragment_day_info) {
    companion object {
        const val ARG_DATE = "ARG_DATE"
    }

    private var _binding: FragmentDayInfoBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DayInfoViewModel by viewModel()
    private val adapter: DayInfoSymptomsAdapter = DayInfoSymptomsAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments
            ?.takeIf { it.containsKey(ARG_DATE) }
            ?.apply { viewModel.loadDay(getSerializable(ARG_DATE) as LocalDate) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentDayInfoBinding.bind(view)
        setUpSymptoms()
        observeDay()
        observeCycle()
        setUpListeners()
    }

    private fun setUpListeners() {
        binding.crossIb.setOnClickListener { findNavController().popBackStack() }
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
            adapter.submitList(it.symptoms.map { symptom -> SymptomItem.fromSymptom(symptom) })
            binding.dateToolbarTv.text = String.format(
                resources.getString(R.string.dd_month),
                it.date.dayOfMonth, it.date.getMonthName()
            )
            it.notes?.let { notes -> binding.notesTv.text = notes }
        }
    }

    private fun setUpSymptoms() {
        binding.symptomsRv.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}