package com.period.tracker.natural.cycles.ui.onboarding.container.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import com.period.tracker.natural.cycles.R
import com.period.tracker.natural.cycles.databinding.FragmentOnboarding1Binding
import com.period.tracker.natural.cycles.ui.onboarding.container.OnBoardingContainerCallbacks
import com.period.tracker.natural.cycles.ui.onboarding.container.OnBoardingContainerViewModel

class OnBoarding1Fragment(val onBoardingContainerCallbacks: OnBoardingContainerCallbacks) : Fragment(R.layout.fragment_onboarding_1) {
    private var _binding: FragmentOnboarding1Binding? = null
    private val binding get() = _binding!!
    private val viewModel: OnBoardingContainerViewModel by sharedViewModel()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentOnboarding1Binding.bind(view)
        setUpUI()
        setUpListeners()
    }

    private fun setUpListeners() {
        binding.myCycleIsRegularBtn.root.setOnClickListener { onBoardingContainerCallbacks.onNextClick() }
        binding.myCycleIsIrregularBtn.root.setOnClickListener { onBoardingContainerCallbacks.onNextClick() }
        binding.iDonTKnowBtn.root.setOnClickListener { onBoardingContainerCallbacks.onNextClick() }
    }

    private fun setUpUI() {
        binding.myCycleIsRegularBtn.titleTv.text = getString(R.string.my_cycle_is_regular)
        binding.myCycleIsIrregularBtn.titleTv.text = getString(R.string.my_cycle_is_irregular)
        binding.iDonTKnowBtn.titleTv.text = getString(R.string.i_don_t_know)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}