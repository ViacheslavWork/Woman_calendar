package woman.calendar.every.day.health.ui.onboarding

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import woman.calendar.every.day.health.R
import woman.calendar.every.day.health.databinding.FragmentMeetBinding

class MeetFragment : Fragment(R.layout.fragment_meet) {
    private var _binding: FragmentMeetBinding? = null
    private val binding get() = _binding!!
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentMeetBinding.bind(view)
        binding.continueBtn.setOnClickListener { findNavController().navigate(MeetFragmentDirections.actionMeetFragmentToStayOnTopOfHealthFragment()) }
        binding.titleTv.text = String.format(
            getString(R.string.meet_app_s_name),
            getString(R.string.app_name)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}