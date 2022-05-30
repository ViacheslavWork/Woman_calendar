package com.period.tracker.natural.cycles.ui.onboarding

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayout
import com.period.tracker.natural.cycles.R
import com.period.tracker.natural.cycles.databinding.FragmentAccountBinding

class AccountFragment : Fragment(R.layout.fragment_account) {
    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentAccountBinding.bind(view)
        observeTabLayout()
        setUpListeners()
    }

    private fun setUpListeners() {
        binding.btn.setOnClickListener {
            findNavController()
                .navigate(AccountFragmentDirections.actionAccountFragmentToSavingDetailsFragment())
        }
    }

    private fun observeTabLayout() {
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> {
                        binding.passwordRequirementsTv.apply {
                            text = getString(R.string.password_requirements)
                            setTextColor(resources.getColor(R.color.text_color_50, null))
                        }
                        binding.btn.text = getString(R.string.sign_up)
                    }
                    1 -> {
                        binding.passwordRequirementsTv.apply {
                            text = getString(R.string.password_forgot)
                            setTextColor(resources.getColor(R.color.pink, null))
                        }
                        binding.btn.text = getString(R.string.log_in)
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}