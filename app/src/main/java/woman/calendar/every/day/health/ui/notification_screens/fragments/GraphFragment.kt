package woman.calendar.every.day.health.ui.notification_screens.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import woman.calendar.every.day.health.R
import woman.calendar.every.day.health.databinding.FragmentGraphBinding
import woman.calendar.every.day.health.ui.notification_screens.NotificationScreenViewModel

class GraphFragment : Fragment(R.layout.fragment_graph) {
    private var _binding: FragmentGraphBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NotificationScreenViewModel by sharedViewModel()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentGraphBinding.bind(view)

        setUpListeners()
        observeData()
    }

    private fun observeData() {
        viewModel.notificationData.observe(viewLifecycleOwner) {
            binding.graphIv.setImageResource(it.graphImage)
        }
    }

    private fun setUpListeners() {
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}