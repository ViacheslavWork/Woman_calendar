package woman.calendar.every.day.health.ui.onboarding.container.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import woman.calendar.every.day.health.R
import woman.calendar.every.day.health.databinding.FragmentOnboarding1Binding
import woman.calendar.every.day.health.ui.onboarding.container.OnBoardingContainerViewModel

class OnBoarding1Fragment : Fragment(R.layout.fragment_onboarding_1) {
    private var _binding: FragmentOnboarding1Binding? = null
    private val binding get() = _binding!!
    private val viewModel: OnBoardingContainerViewModel by sharedViewModel()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentOnboarding1Binding.bind(view)
        setUpUI()
        setUpListeners()
    }

    private fun setUpListeners() {
        binding.myCycleIsRegularBtn.root.setOnClickListener { viewModel.onNextFragmentClick() }
        binding.myCycleIsIrregularBtn.root.setOnClickListener { viewModel.onNextFragmentClick() }
        binding.iDonTKnowBtn.root.setOnClickListener { viewModel.onNextFragmentClick() }
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