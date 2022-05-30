package com.period.tracker.natural.cycles.ui.notification_screens.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.threeten.bp.LocalDate
import com.period.tracker.natural.cycles.R
import com.period.tracker.natural.cycles.databinding.FragmentChallengesBinding
import com.period.tracker.natural.cycles.ui.notification_screens.NotificationScreenViewModel
import com.period.tracker.natural.cycles.utils.LocalDateHelper.getMonthName

class ChallengesFragment : Fragment(R.layout.fragment_challenges) {
    private var _binding: FragmentChallengesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NotificationScreenViewModel by sharedViewModel()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentChallengesBinding.bind(view)

        setUpListeners()
        observeData()
    }

    private fun observeData() {
        viewModel.notificationData.observe(viewLifecycleOwner) {
            it?.let {
                binding.firstCardTitleTv.text = it.firstChallengesMessage.title
                binding.firstCardContentTv.text = it.firstChallengesMessage.content
                binding.secondCardTitleTv.text = it.secondChallengesMessage.title
                binding.secondCardContentTv.text = it.secondChallengesMessage.content
            }
        }
        viewModel.lengthOfCycle.observe(viewLifecycleOwner) {
            binding.dateTv.text = String.format(
                resources.getString(R.string.month_dd_cycle_day_nn),
                //TODO
                LocalDate.now().getMonthName(),
                LocalDate.now().dayOfMonth,
                it.toString()
            )
        }
    }

    private fun setUpListeners() {
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}