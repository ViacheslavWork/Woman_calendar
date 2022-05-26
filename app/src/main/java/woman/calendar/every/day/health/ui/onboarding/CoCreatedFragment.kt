package woman.calendar.every.day.health.ui.onboarding

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import woman.calendar.every.day.health.R
import woman.calendar.every.day.health.databinding.FragmentCoCreatedBinding

class CoCreatedFragment : Fragment(R.layout.fragment_co_created) {
    private var _binding: FragmentCoCreatedBinding? = null
    private val binding get() = _binding!!
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentCoCreatedBinding.bind(view)
        binding.continueBtn.setOnClickListener {
            findNavController().navigate(
                CoCreatedFragmentDirections.actionCoCreatedFragmentToCreatingPersonalProgramFragment()
            )
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}