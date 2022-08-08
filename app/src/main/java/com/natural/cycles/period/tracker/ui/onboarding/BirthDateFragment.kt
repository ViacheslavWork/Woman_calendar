package com.natural.cycles.period.tracker.ui.onboarding

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.natural.cycles.period.tracker.R
import com.natural.cycles.period.tracker.databinding.FragmentBirthDateBinding


class BirthDateFragment : Fragment(R.layout.fragment_birth_date) {
    private var _binding: FragmentBirthDateBinding? = null
    private val binding get() = _binding!!
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentBirthDateBinding.bind(view)
        setUpListeners()
    }

    private fun setUpListeners() {
        binding.dateTv.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { _, year, monthOfYear, dayOfMonth ->
                    binding.dateTv.text = String.format(
                        getString(R.string.date_format),
                        dayOfMonth,
                        monthOfYear + 1,
                        year
                    )
                    binding.continueBtn.isEnabled = true
                }, 1999, 0, 1
            )
            datePickerDialog.datePicker.maxDate = System.currentTimeMillis()
            datePickerDialog.show()
        }
        binding.continueBtn.setOnClickListener {
            findNavController().navigate(
                BirthDateFragmentDirections.actionBirthDateFragmentToContinueWithEmailFragment()
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}