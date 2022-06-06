package com.period.tracker.natural.cycles.ui.subscription

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.period.tracker.natural.cycles.R
import com.period.tracker.natural.cycles.databinding.FragmentSubscriptionBinding

class SubscriptionFragment : Fragment(R.layout.fragment_subscription) {
    companion object {
        const val ARG_IS_TOOL_BAR = "ARG_IS_TOOL_BAR"
    }

    private var _binding: FragmentSubscriptionBinding? = null
    private val binding get() = _binding!!
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentSubscriptionBinding.bind(view)
        arguments
            ?.takeIf { it.containsKey(ARG_IS_TOOL_BAR) }
            ?.apply {
                binding.toolBar.visibility = if (getBoolean(ARG_IS_TOOL_BAR)) VISIBLE else GONE
            }

        setUpListeners()
    }

    private fun setUpListeners() {
        binding.backIb.setOnClickListener { findNavController().popBackStack() }
        binding.skipBtn.setOnClickListener {
            findNavController().navigate(
                SubscriptionFragmentDirections.actionSubscriptionFragmentToNavigationHome()
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}