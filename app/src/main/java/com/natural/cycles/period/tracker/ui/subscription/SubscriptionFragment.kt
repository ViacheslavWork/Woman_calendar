package com.natural.cycles.period.tracker.ui.subscription

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.natural.cycles.period.tracker.R
import com.natural.cycles.period.tracker.databinding.FragmentSubscriptionBinding
import com.natural.cycles.period.tracker.ui.MainViewModel
import com.natural.cycles.period.tracker.utils.Constants
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SubscriptionFragment : Fragment(R.layout.fragment_subscription) {
    companion object {
        const val ARG_HAS_TOOL_BAR = "ARG_HAS_TOOL_BAR"
    }

    private val mainViewModel: MainViewModel by sharedViewModel()
    private var _binding: FragmentSubscriptionBinding? = null
    private val binding get() = _binding!!
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentSubscriptionBinding.bind(view)
        binding.threeDayTrialTv.text =
            getString(R.string.day_free_trial, Constants.TRIAL_LENGTH_DAYS)

        arguments
            ?.takeIf { it.containsKey(ARG_HAS_TOOL_BAR) }
            ?.apply {
                binding.toolBar.visibility = if (getBoolean(ARG_HAS_TOOL_BAR)) VISIBLE else GONE
            }

        setUpListeners()
        observePremiumStatus()
        observePrice()
    }

    private fun observePrice() {
        mainViewModel.monthPrice.observe(viewLifecycleOwner) {
            binding.thenTv.text =
                if (it != null) getString(R.string.then_, it)
                else getString(R.string.then_sum__)
        }
    }

    private fun observePremiumStatus() {
        mainViewModel.isPremium.observe(viewLifecycleOwner) {
            if (it) findNavController().popBackStack()
        }
    }

    private fun setUpListeners() {
        binding.backIb.setOnClickListener { findNavController().popBackStack() }
        binding.skipBtn.setOnClickListener {
            findNavController().navigate(
                SubscriptionFragmentDirections.actionSubscriptionFragmentToNavigationHome()
            )
        }
        binding.subscriptionBtn.setOnClickListener {
            mainViewModel.handleEvent(SubscriptionEvent(Subscription.MONTH))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}