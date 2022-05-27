package woman.calendar.every.day.health.ui.onboarding.container.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import woman.calendar.every.day.health.R
import woman.calendar.every.day.health.databinding.FragmentOnboarding5Binding
import woman.calendar.every.day.health.ui.onboarding.container.OnBoardingContainerCallbacks
import woman.calendar.every.day.health.ui.onboarding.container.OnBoardingContainerViewModel

class OnBoarding5Fragment(val onBoardingContainerCallbacks: OnBoardingContainerCallbacks) : Fragment(R.layout.fragment_onboarding_5) {
    private var _binding: FragmentOnboarding5Binding? = null
    private val binding get() = _binding!!
    private val viewModel: OnBoardingContainerViewModel by sharedViewModel()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentOnboarding5Binding.bind(view)
        setUpUI()
        setUpListeners()
    }
    private fun setUpListeners() {
        binding.yesBtn.root.setOnClickListener { onBoardingContainerCallbacks.onNextClick() }
        binding.noBtn.root.setOnClickListener { onBoardingContainerCallbacks.onNextClick() }
        binding.noButIUsedToBtn.root.setOnClickListener { onBoardingContainerCallbacks.onNextClick() }
        binding.iDonTKnowBtn.root.setOnClickListener { onBoardingContainerCallbacks.onNextClick() }
    }

    private fun setUpUI() {
        binding.yesBtn.titleTv.text = getString(R.string.yes)
        binding.noBtn.titleTv.text = getString(R.string.no)
        binding.noButIUsedToBtn.titleTv.text = getString(R.string.no_but_I_used_to)
        binding.iDonTKnowBtn.titleTv.text = getString(R.string.i_don_t_know)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}