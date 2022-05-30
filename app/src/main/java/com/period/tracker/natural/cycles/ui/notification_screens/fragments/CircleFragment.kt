package com.period.tracker.natural.cycles.ui.notification_screens.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import com.period.tracker.natural.cycles.R
import com.period.tracker.natural.cycles.databinding.FragmentCircleBinding
import com.period.tracker.natural.cycles.ui.notification_screens.NotificationScreenViewModel

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
            it?.let { binding.circleIv.setImageResource(it.circleImage) }
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