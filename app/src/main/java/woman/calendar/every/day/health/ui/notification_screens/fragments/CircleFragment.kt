package woman.calendar.every.day.health.ui.notification_screens.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import woman.calendar.every.day.health.R
import woman.calendar.every.day.health.databinding.FragmentCircleBinding
import woman.calendar.every.day.health.ui.notification_screens.NotificationScreenViewModel

class CircleFragment : Fragment(R.layout.fragment_circle) {
    private var _binding: FragmentCircleBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NotificationScreenViewModel by sharedViewModel()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentCircleBinding.bind(view)

        observeData()
    }

    private fun observeData() {
        viewModel.notificationData.observe(viewLifecycleOwner) {
            binding.circleIv.setImageResource(it.circleImage)
        }
        viewModel.lengthOfCycle.observe(viewLifecycleOwner) {
            binding.titleTv.text = String.format(
                resources.getString(R.string.today_is_n_your_cycle_day_s),
                it.toString()
            )
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}