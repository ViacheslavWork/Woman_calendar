package com.natural.cycles.period.tracker.ui.onboarding.container.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import com.natural.cycles.period.tracker.R
import com.natural.cycles.period.tracker.databinding.*
import com.natural.cycles.period.tracker.ui.onboarding.container.OnBoardingContainerCallbacks
import com.natural.cycles.period.tracker.ui.onboarding.container.OnBoardingContainerViewModel

class OnBoarding7Fragment(val onBoardingContainerCallbacks: OnBoardingContainerCallbacks) : Fragment(R.layout.fragment_onboarding_7) {
    private var _binding: FragmentOnboarding7Binding? = null
    private val binding get() = _binding!!
    private val viewModel: OnBoardingContainerViewModel by sharedViewModel()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentOnboarding7Binding.bind(view)
        setUpUI()
        setUpListeners()
    }
    private fun setUpListeners() {
        binding.nextBtn.setOnClickListener { onBoardingContainerCallbacks.onNextClick() }
    }

    private fun setUpUI() {
        
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}