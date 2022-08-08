package com.natural.cycles.period.tracker.ui.notification_screens.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.threeten.bp.LocalDate
import timber.log.Timber
import com.natural.cycles.period.tracker.R
import com.natural.cycles.period.tracker.databinding.FragmentNotificationScreensContainerBinding
import com.natural.cycles.period.tracker.ui.notification_screens.NotificationScreenType
import com.natural.cycles.period.tracker.ui.notification_screens.NotificationScreenViewModel
import com.natural.cycles.period.tracker.ui.notification_screens.NotificationScreensViewPagerAdapter

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
        binding.nextBtn.setOnClickListener {
            binding.screensVp.currentItem = binding.screensVp.currentItem + 1
        }
        binding.crossIb.setOnClickListener {
            findNavController().navigate(NotificationScreensContainerFragmentDirections.actionNotificationScreensContainerFragmentToNavigationHome())
        }
        binding.screensVp.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                Timber.d(position.toString())
                if (position == NotificationScreenType.values().size - 1) {
                    binding.nextBtn.visibility = View.GONE
                } else {
                    binding.nextBtn.visibility = View.VISIBLE
                }
                super.onPageSelected(position)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}