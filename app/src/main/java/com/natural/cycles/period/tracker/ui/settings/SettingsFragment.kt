package com.natural.cycles.period.tracker.ui.settings

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.natural.cycles.period.tracker.R
import com.natural.cycles.period.tracker.databinding.FragmentSettingsBinding
import com.natural.cycles.period.tracker.ui.subscription.SubscriptionFragment

class SettingsFragment : Fragment(R.layout.fragment_settings) {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentSettingsBinding.bind(view)
        setUpListeners()

    }

    private fun setUpListeners() {
        binding.backIb.setOnClickListener { findNavController().popBackStack() }
        binding.premiumIb.setOnClickListener {
            findNavController().navigate(
                R.id.action_settingsFragment_to_subscriptionFragment,
                bundleOf(SubscriptionFragment.ARG_HAS_TOOL_BAR to false)
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}