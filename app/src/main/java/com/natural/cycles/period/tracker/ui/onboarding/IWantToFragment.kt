package com.natural.cycles.period.tracker.ui.onboarding

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.natural.cycles.period.tracker.R
import com.natural.cycles.period.tracker.databinding.FragmentIWantToBinding
import com.natural.cycles.period.tracker.ui.onboarding.login.AccountContainerFragment

class IWantToFragment : Fragment(R.layout.fragment_i_want_to) {
    private var _binding: FragmentIWantToBinding? = null
    private val binding get() = _binding!!
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentIWantToBinding.bind(view)
        setUpUI()
        setUpListeners()
    }

    private fun setUpUI() {
        binding.trackMyFemaleHealthBtn.titleTv.text = getString(R.string.track_my_female_health)
        binding.trackMyFemaleHealthBtn.contentTv.text =
            getString(R.string.manage_my_well_being_and_contraception)

        binding.getPregnantBtn.titleTv.text = getString(R.string.get_pregnant)
        binding.getPregnantBtn.contentTv.text = getString(R.string.leverage_my_fertile_days)

        binding.followMyPregnancyBtn.titleTv.text = getString(R.string.follow_my_pregnancy)
        binding.followMyPregnancyBtn.contentTv.text =
            getString(R.string.take_care_of_myself_and_my_future_baby)

    }

    private fun setUpListeners() {
        binding.signUpBtn.setOnClickListener {
            findNavController().navigate(
                R.id.action_IWantToFragment_to_accountFragment,
                bundleOf(AccountContainerFragment.ARG_TAB to AccountContainerFragment.ARG_SIGN_UP)
            )
        }
        binding.logInBtn.setOnClickListener {
            findNavController().navigate(
                R.id.action_IWantToFragment_to_accountFragment,
                bundleOf(AccountContainerFragment.ARG_TAB to AccountContainerFragment.ARG_LOGIN)
            )
        }
        binding.trackMyFemaleHealthBtn.root.setOnClickListener {
            binding.trackMyFemaleHealthBtn.root.animate()
                .setDuration(100)
                .scaleX(0.90f)
                .scaleY(0.90f)
                .withEndAction { findNavController().navigate(IWantToFragmentDirections.actionIWantToFragmentToBirthDateFragment()) }
        }
        binding.getPregnantBtn.root.setOnClickListener {
            binding.getPregnantBtn.root.animate()
                .setDuration(100)
                .scaleX(0.90f)
                .scaleY(0.90f)
                .withEndAction { findNavController().navigate(IWantToFragmentDirections.actionIWantToFragmentToBirthDateFragment()) }
        }
        binding.followMyPregnancyBtn.root.setOnClickListener {
            binding.followMyPregnancyBtn.root.animate()
                .setDuration(100)
                .scaleX(0.90f)
                .scaleY(0.90f)
                .withEndAction { findNavController().navigate(IWantToFragmentDirections.actionIWantToFragmentToBirthDateFragment()) }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}