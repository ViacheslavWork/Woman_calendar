package com.period.tracker.natural.cycles.ui.onboarding.container.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import com.period.tracker.natural.cycles.R
import com.period.tracker.natural.cycles.databinding.FragmentOnboarding4Binding
import com.period.tracker.natural.cycles.ui.onboarding.container.OnBoardingContainerCallbacks
import com.period.tracker.natural.cycles.ui.onboarding.container.OnBoardingContainerViewModel
import java.util.*

class OnBoarding4Fragment(val onBoardingContainerCallbacks: OnBoardingContainerCallbacks) : Fragment(R.layout.fragment_onboarding_4) {
    private var _binding: FragmentOnboarding4Binding? = null
    private val binding get() = _binding!!
    private val viewModel: OnBoardingContainerViewModel by sharedViewModel()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentOnboarding4Binding.bind(view)
        setUpUI()
        setUpListeners()
    }
    private fun setUpListeners() {
        binding.nextBtn.setOnClickListener { onBoardingContainerCallbacks.onNextClick() }
    }

    private fun setUpUI() {
        binding.kgTv.text = when (Locale.getDefault().country) {
            "US" -> resources.getString(R.string.lb)
            else -> resources.getString(R.string.kg)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}