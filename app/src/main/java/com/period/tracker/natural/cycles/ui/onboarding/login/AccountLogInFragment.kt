package com.period.tracker.natural.cycles.ui.onboarding.login

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.period.tracker.natural.cycles.R
import com.period.tracker.natural.cycles.databinding.FragmentAccountLogInBinding
import com.period.tracker.natural.cycles.preferences.FirstRunPreferences
import com.period.tracker.natural.cycles.ui.MainViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber

class AccountLogInFragment : Fragment(R.layout.fragment_account_log_in) {
    private var _binding: FragmentAccountLogInBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel: MainViewModel by sharedViewModel()
    private val firstRunPreferences: FirstRunPreferences by inject()
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentAccountLogInBinding.bind(view)
        binding.logInBtn.setOnClickListener {
            if (binding.emailEt.text.isNotBlank() && binding.passwordEt.text.isNotBlank()) {
                logIn(binding.emailEt.text.toString(), binding.passwordEt.text.toString())
            } else {
                Toast.makeText(
                    context,
                    getString(R.string.you_must_fill_in_all_fields),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun logIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    Timber.d("signInWithEmail:success ${user?.uid}")
                    findNavController()
                        .navigate(AccountContainerFragmentDirections.actionAccountFragmentToNavigationHome())
                    mainViewModel.setUser(user)
                } else {
                    Timber.d(task.exception?.message, "failed")
                    Toast.makeText(
                        context,
                        getString(R.string.authentification_failed) + task.exception?.message,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}