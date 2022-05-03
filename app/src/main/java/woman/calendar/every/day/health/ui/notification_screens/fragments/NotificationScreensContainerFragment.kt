package woman.calendar.every.day.health.ui.notification_screens.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.threeten.bp.LocalDate
import woman.calendar.every.day.health.R
import woman.calendar.every.day.health.databinding.FragmentNotificationScreensContainerBinding
import woman.calendar.every.day.health.ui.notification_screens.NotificationScreenType
import woman.calendar.every.day.health.ui.notification_screens.NotificationScreenViewModel
import woman.calendar.every.day.health.ui.notification_screens.NotificationScreensViewPagerAdapter

class NotificationScreensContainerFragment :
    Fragment(R.layout.fragment_notification_screens_container) {
    private var _binding: FragmentNotificationScreensContainerBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: NotificationScreensViewPagerAdapter
    private val viewModel: NotificationScreenViewModel by sharedViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentNotificationScreensContainerBinding.bind(view)
        viewModel.init(LocalDate.now())
        setUpAdapter()
        setUpListeners()
    }

    private fun setUpAdapter() {
        adapter = NotificationScreensViewPagerAdapter(
            requireParentFragment(),
            NotificationScreenType.values().toList()
        )
        binding.screensVp.adapter = adapter
    }

    private fun setUpListeners() {
        binding.nextBtn.setOnClickListener { TODO() }
        binding.crossIb.setOnClickListener {
            findNavController().navigate(
                NotificationScreensContainerFragmentDirections.actionNotificationScreensContainerFragmentToNavigationHome()
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}