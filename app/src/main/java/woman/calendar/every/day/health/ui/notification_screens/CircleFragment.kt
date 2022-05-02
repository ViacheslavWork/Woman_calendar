package woman.calendar.every.day.health.ui.notification_screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import woman.calendar.every.day.health.R
import woman.calendar.every.day.health.databinding.FragmentCircleBinding

class CircleFragment : Fragment(R.layout.fragment_circle) {
    private var _binding: FragmentCircleBinding? = null
    private val binding get() = _binding!!
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentCircleBinding.bind(view)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}