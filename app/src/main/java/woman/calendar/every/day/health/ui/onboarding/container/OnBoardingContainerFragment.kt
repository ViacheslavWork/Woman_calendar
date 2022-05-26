package woman.calendar.every.day.health.ui.onboarding.container

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber
import woman.calendar.every.day.health.R
import woman.calendar.every.day.health.databinding.FragmentOnboardingContainerBinding

class OnBoardingContainerFragment : Fragment(R.layout.fragment_onboarding_container) {
    private var _binding: FragmentOnboardingContainerBinding? = null
    private val binding get() = _binding!!
    private val viewModel: OnBoardingContainerViewModel by sharedViewModel()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentOnboardingContainerBinding.bind(view)
        binding.onboardingVp.adapter = OnBoardingViewPagerAdapter(requireParentFragment())
        observeViewPagerPosition()
        setUpListeners()
        observeNextClick()
    }

    private fun observeNextClick() {
        lifecycleScope.launchWhenStarted {
            viewModel.nextClickFlow.collectLatest() {
                Timber.d("boolean = ${it.hashCode()}")
                onNextClick()
            }
        }
    }

    private fun onNextClick() {
        binding.onboardingVp.apply {
            if (currentItem != adapter?.itemCount?.minus(1)) {
                currentItem += 1
            } else {
                findNavController().navigate(OnBoardingContainerFragmentDirections.actionOnBoardingContainerFragmentToCoCreatedFragment())
            }
        }
    }

    private fun setUpListeners() {
        binding.backIb.setOnClickListener {
            binding.onboardingVp.apply {
                if (currentItem != 0) {
                    currentItem -= 1
                }
            }
        }
        binding.skipBtn.setOnClickListener {
            findNavController().navigate(OnBoardingContainerFragmentDirections.actionOnBoardingContainerFragmentToCoCreatedFragment())
        }
    }

    private fun observeViewPagerPosition() {
        binding.onboardingVp.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding.numberOfFragmentTv.text =
                    String.format(getString(R.string.d_of_10, position + 1))
                binding.backIb.visibility =
                    when (position) {
                        0 -> View.GONE
                        else -> View.VISIBLE
                    }
                super.onPageSelected(position)
            }
        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}