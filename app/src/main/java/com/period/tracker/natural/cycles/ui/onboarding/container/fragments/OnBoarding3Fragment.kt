package com.period.tracker.natural.cycles.ui.onboarding.container.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import com.period.tracker.natural.cycles.R
import com.period.tracker.natural.cycles.databinding.FragmentOnboarding3Binding
import com.period.tracker.natural.cycles.ui.onboarding.container.OnBoardingContainerCallbacks
import com.period.tracker.natural.cycles.ui.onboarding.container.OnBoardingContainerViewModel

class OnBoarding3Fragment(val onBoardingContainerCallbacks: OnBoardingContainerCallbacks) : Fragment(R.layout.fragment_onboarding_3) {
    private var _binding: FragmentOnboarding3Binding? = null
    private val binding get() = _binding!!
    private val viewModel: OnBoardingContainerViewModel by sharedViewModel()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentOnboarding3Binding.bind(view)
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