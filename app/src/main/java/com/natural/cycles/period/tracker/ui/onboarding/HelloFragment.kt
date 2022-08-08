package com.natural.cycles.period.tracker.ui.onboarding

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.natural.cycles.period.tracker.R
import com.natural.cycles.period.tracker.databinding.FragmentHelloBinding
import com.natural.cycles.period.tracker.preferences.FirstRunPreferences
import org.koin.android.ext.android.inject
import timber.log.Timber

class HelloFragment : Fragment(R.layout.fragment_hello) {
    private var _binding: FragmentHelloBinding? = null
    private val binding get() = _binding!!
    private val firstRunPreferences: FirstRunPreferences by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (!firstRunPreferences.isFirstRun() || Firebase.auth.currentUser != null) {
            navigate()
        }
        _binding = FragmentHelloBinding.bind(view)
        binding.startBtn.setOnClickListener {
            findNavController().navigate(HelloFragmentDirections.actionHelloFragmentToIWantToFragment())
//            findNavController().navigate(HelloFragmentDirections.actionHelloFragmentToNavigationHome())
        }
        binding.titleTv.text = String.format(
            getString(R.string.hello_i_m_app_s_name),
            getString(R.string.app_name)
        )
    }

    private fun navigate() {
        if (Firebase.auth.currentUser != null) {
            Timber.d(Firebase.auth.currentUser!!.email.toString())
            findNavController().navigate(HelloFragmentDirections.actionHelloFragmentToNavigationHome())
        } else {
            findNavController().navigate(HelloFragmentDirections.actionHelloFragmentToAccountFragment())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}