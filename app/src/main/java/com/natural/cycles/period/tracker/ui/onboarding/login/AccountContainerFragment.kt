package com.natural.cycles.period.tracker.ui.onboarding.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.natural.cycles.period.tracker.R
import com.natural.cycles.period.tracker.databinding.FragmentAccountContainerBinding

class AccountContainerFragment : Fragment(R.layout.fragment_account_container) {
    companion object {
        const val ARG_TAB = "ARG_TAB"
        const val ARG_LOGIN = "ARG_LOGIN"
        const val ARG_SIGN_UP = "ARG_SIGN_UP"
    }

    private var _binding: FragmentAccountContainerBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: AccountViewPagerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentAccountContainerBinding.bind(view)

        /*Firebase.auth.currentUser?.also {
            Timber.d("user email: ${it.email}")
            findNavController().navigate(AccountContainerFragmentDirections.actionAccountFragmentToSavingDetailsFragment())
            return
        }*/

        adapter = AccountViewPagerAdapter(requireParentFragment())
        binding.accountVp.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.accountVp) { tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.sign_up)
                1 -> tab.text = getString(R.string.log_in)
            }
        }.attach()

        arguments
            ?.takeIf { it.containsKey(ARG_TAB) }
            ?.apply {
                binding.accountVp.post {
                    when (getString(ARG_TAB)) {
                        ARG_SIGN_UP -> binding.accountVp.currentItem = 0
                        ARG_LOGIN -> binding.accountVp.currentItem = 1
                    }
                }
            }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}