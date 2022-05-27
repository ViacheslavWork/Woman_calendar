package woman.calendar.every.day.health.ui.onboarding

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import woman.calendar.every.day.health.R
import woman.calendar.every.day.health.databinding.FragmentContinueWithEmailBinding

class ContinueWithEmailFragment : Fragment(R.layout.fragment_continue_with_email) {
    private var _binding: FragmentContinueWithEmailBinding? = null
    private val binding get() = _binding!!
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentContinueWithEmailBinding.bind(view)
        binding.continueWithEmailBtn.setOnClickListener {
            findNavController().navigate(
                ContinueWithEmailFragmentDirections.actionContinueWithEmailFragmentToAccountFragment()
            )
        }
        binding.titleTv.text = String.format(
            getString(R.string.take_your_health_n_well_being_to_the_next_level_with_the_s),
            getString(R.string.app_name)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}