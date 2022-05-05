package woman.calendar.every.day.health.ui.notification_screens.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.threeten.bp.LocalDate
import woman.calendar.every.day.health.R
import woman.calendar.every.day.health.databinding.FragmentWinsBinding
import woman.calendar.every.day.health.ui.notification_screens.NotificationScreenViewModel
import woman.calendar.every.day.health.utils.LocalDateHelper.getMonthName

class WinsFragment : Fragment(R.layout.fragment_wins) {
    private var _binding: FragmentWinsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NotificationScreenViewModel by sharedViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentWinsBinding.bind(view)

        observeData()
    }

    private fun observeData() {
        viewModel.notificationData.observe(viewLifecycleOwner) {
            it?.let {
                binding.firstCardTitleTv.text = it.firstWinMessage.title
                binding.firstCardContentTv.text = it.firstWinMessage.content
                binding.secondCardTitleTv.text = it.secondWinMessage.title
                binding.secondCardContentTv.text = it.secondWinMessage.content
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}