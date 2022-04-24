package woman.calendar.every.day.health.ui.calendar

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.koin.androidx.viewmodel.ext.android.viewModel
import woman.calendar.every.day.health.R
import woman.calendar.every.day.health.databinding.FragmentCalendrarBinding


class CalendarFragment : Fragment(R.layout.fragment_calendrar) {
    private var _binding: FragmentCalendrarBinding? = null
    private val binding get() = _binding!!
    private var isScrollToBottom = true
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CalendarAdapter
    private val viewModel: CalendarViewModel by viewModel()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentCalendrarBinding.bind(view)

        setUpRecyclerView()
        setUpAdapter()
        observeListEvent()
        observeMonths()
        setUpListeners()
    }

    private fun setUpListeners() {
        binding.crossIb.setOnClickListener { findNavController().popBackStack() }
    }

    private fun observeMonths() {
        viewModel.months.observe(viewLifecycleOwner) {
            adapter.submitList(it)
            if (isScrollToBottom) {
                isScrollToBottom = false
                recyclerView.scrollToPosition(it.size - 1)
            }
        }
    }


    private fun observeListEvent() {
        adapter.event.observe(viewLifecycleOwner) {
            viewModel.handleEvent(it)
        }
    }

    private fun setUpRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(context)
        recyclerView = binding.calendarRv
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.setHasFixedSize(true)
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (linearLayoutManager.findFirstVisibleItemPosition() == 0) {
                    viewModel.getPrevMonth()
                }
            }
        })
    }

    private fun setUpAdapter() {
        adapter = CalendarAdapter()
        recyclerView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}