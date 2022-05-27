package woman.calendar.every.day.health.ui.onboarding

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import woman.calendar.every.day.health.R
import woman.calendar.every.day.health.databinding.FragmentHelloBinding

class HelloFragment : Fragment(R.layout.fragment_hello) {
    private var _binding: FragmentHelloBinding? = null
    private val binding get() = _binding!!
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentHelloBinding.bind(view)
        binding.startBtn.setOnClickListener { findNavController().navigate(HelloFragmentDirections.actionHelloFragmentToIWantToFragment()) }
        binding.titleTv.text = String.format(
            getString(R.string.hello_i_m_app_s_name),
            getString(R.string.app_name)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}