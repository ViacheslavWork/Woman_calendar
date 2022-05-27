package woman.calendar.every.day.health.ui.onboarding.container.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import woman.calendar.every.day.health.R
import woman.calendar.every.day.health.databinding.FragmentOnboarding1Binding
import woman.calendar.every.day.health.databinding.FragmentOnboarding2Binding
import woman.calendar.every.day.health.databinding.FragmentOnboarding3Binding
import woman.calendar.every.day.health.databinding.FragmentOnboarding4Binding
import woman.calendar.every.day.health.ui.onboarding.container.OnBoardingContainerCallbacks
import woman.calendar.every.day.health.ui.onboarding.container.OnBoardingContainerViewModel

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
        
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}