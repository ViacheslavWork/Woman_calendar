package com.natural.cycles.period.tracker.ui.weight

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.natural.cycles.period.tracker.R
import com.natural.cycles.period.tracker.databinding.FragmentWeightBinding
import com.natural.cycles.period.tracker.preferences.WeightPreferences
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class WeightFragment : Fragment(R.layout.fragment_weight) {
    private var _binding: FragmentWeightBinding? = null
    private val binding get() = _binding!!
    private val viewModel: WeightViewModel by viewModel()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentWeightBinding.bind(view)
        setUpUi()
        setInitialWeight()
        setUpListeners()
    }

    private fun setUpUi() {
        binding.kgTv.text = when (Locale.getDefault().country) {
            "US" -> getString(R.string.lb)
            else -> getString(R.string.kg)
        }
    }

    private fun setInitialWeight() {
        WeightPreferences.getWeight(requireContext())?.also {
            binding.weightEt.setText(it.toString())
            binding.applyBtn.isEnabled = true
        }
    }

    private fun setUpListeners() {
        binding.backIb.setOnClickListener { findNavController().popBackStack() }
        binding.weightEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.applyBtn.isEnabled = p0?.isNotEmpty() == true
            }

            override fun afterTextChanged(p0: Editable?) {}
        })
        binding.weightEt.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                setWeightToPreferences()
                findNavController().popBackStack()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }

        binding.applyBtn.setOnClickListener {
            setWeightToPreferences()
            findNavController().popBackStack()
        }
    }

    private fun setWeightToPreferences() {
        if (binding.weightEt.text.isNotEmpty()) {
            WeightPreferences.setWeight(binding.weightEt.text.toString().toInt(), requireContext())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}