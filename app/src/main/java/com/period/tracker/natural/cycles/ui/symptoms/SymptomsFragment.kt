package com.period.tracker.natural.cycles.ui.symptoms

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.period.tracker.natural.cycles.R
import com.period.tracker.natural.cycles.databinding.FragmentSymptomsBinding
import com.period.tracker.natural.cycles.domain.model.StateOfDay
import com.period.tracker.natural.cycles.domain.model.SymptomType
import com.period.tracker.natural.cycles.preferences.WeightPreferences
import com.period.tracker.natural.cycles.ui.notes.NotesFragment
import com.period.tracker.natural.cycles.utils.LocalDateHelper.getMonthName
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.threeten.bp.LocalDate
import timber.log.Timber
import java.util.*

class SymptomsFragment : Fragment(R.layout.fragment_symptoms) {
    private var _binding: FragmentSymptomsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SymptomsViewModel by viewModel()
    private lateinit var date: LocalDate

    private lateinit var menstrualFlowRecyclerView: RecyclerView
    private lateinit var sexDriveRecyclerView: RecyclerView
    private lateinit var moodRecyclerView: RecyclerView
    private lateinit var symptomsRecyclerView: RecyclerView
    private lateinit var vaginalRecyclerView: RecyclerView
    private lateinit var otherRecyclerView: RecyclerView

    private lateinit var menstrualFlowAdapter: SymptomsAdapter
    private lateinit var sexDriveAdapter: SymptomsAdapter
    private lateinit var moodAdapter: SymptomsAdapter
    private lateinit var symptomsAdapter: SymptomsAdapter
    private lateinit var vaginalAdapter: SymptomsAdapter
    private lateinit var otherAdapter: SymptomsAdapter

    private val symptomEvent: MutableLiveData<SymptomEvent> = MutableLiveData()

    companion object {
        const val ARG_DATE = "ARG_DATE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        date = (arguments?.getSerializable(ARG_DATE) as LocalDate?) ?: LocalDate.now()
        viewModel.init(date)
    }

    override fun onStart() {
        viewModel.updateUI()
        super.onStart()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentSymptomsBinding.bind(view)

        setUpUI()
        setUpRecyclerViews()
        setUpListeners()

        observeSymptoms()
        observeNotes()
        observeSymptomsEvent()
        observeWater()
        observeStateOfDay()
    }

    private fun observeStateOfDay() {
        viewModel.stateOfDay.observe(viewLifecycleOwner) {
            binding.menstrualFlowTv.visibility = if (it == StateOfDay.PERIOD) VISIBLE else GONE
            binding.menstrualFlowRv.visibility = if (it == StateOfDay.PERIOD) VISIBLE else GONE
        }
    }

    private fun observeNotes() {
        viewModel.notes.observe(viewLifecycleOwner) {
            it?.let { binding.notesTv.text = it }
        }
    }


    private fun observeWater() {
        viewModel.volumeOfWater.observe(viewLifecycleOwner) {
            binding.waterNumberTv.text = String.format(
                resources.getString(R.string.water_l),
                it
            )
        }
    }

    private fun setUpUI() {
        /*binding.weightNumberTv.text = when (Locale.getDefault().country) {
            "US" -> getString(R.string.__lb)
            else -> getString(R.string.__kg)
        }*/
        binding.dateToolbarTv.text = String.format(
            resources.getString(R.string.dd_month),
            date.dayOfMonth, date.getMonthName()
        )
        lifecycleScope.launch {
            binding.cycleDayToolbarTv.text = String.format(
                resources.getString(R.string.cycle_day_s),
                viewModel.getDayOfLastCycle().toString()
            )
        }

        WeightPreferences.getWeight(requireContext()).also {
            if (it == null) {
                binding.weightNumberTv.text = when (Locale.getDefault().country) {
                    "US" -> resources.getString(R.string.__lb)
                    else -> resources.getString(R.string.__kg)
                }
            } else {
                binding.weightNumberTv.text = when (Locale.getDefault().country) {
                    "US" -> String.format(resources.getString(R.string.s_lb), it.toString())
                    else -> String.format(resources.getString(R.string.s_kg), it.toString())
                }
            }
        }
        lifecycleScope.launch { Timber.d(viewModel.isPeriod(date).toString()) }
    }

    private fun observeSymptomsEvent() {
        symptomEvent.observe(viewLifecycleOwner) {
            viewModel.handleEvent(it)
        }
    }

    private fun setUpListeners() {
        binding.crossIb.setOnClickListener { findNavController().popBackStack() }
        binding.editWaterBtn.setOnClickListener {
            findNavController().navigate(
                SymptomsFragmentDirections.actionSymptomsFragmentToWaterFragment()
            )
        }
        binding.editWeightBtn.setOnClickListener {
            findNavController().navigate(
                SymptomsFragmentDirections.actionSymptomsFragmentToWeightFragment()
            )
        }
        binding.notesTv.setOnClickListener {
            findNavController().navigate(
                R.id.action_symptomsFragment_to_notesFragment,
                bundleOf(NotesFragment.ARG_DATE to date)
            )
        }
    }

    private fun observeSymptoms() {
        viewModel.symptoms.observe(viewLifecycleOwner) {
            menstrualFlowAdapter.submitList(it.filter { symptom -> symptom.symptomType == SymptomType.MENSTRUAL_FLOW })
            sexDriveAdapter.submitList(it.filter { symptom -> symptom.symptomType == SymptomType.SEX_AND_SEX_DRIVE })
            moodAdapter.submitList(it.filter { symptom -> symptom.symptomType == SymptomType.MOOD })
            symptomsAdapter.submitList(it.filter { symptom -> symptom.symptomType == SymptomType.SYMPTOMS })
            vaginalAdapter.submitList(it.filter { symptom -> symptom.symptomType == SymptomType.VAGINAL_DISCHARGE })
            otherAdapter.submitList(it.filter { symptom -> symptom.symptomType == SymptomType.OTHER })
        }
    }

    private fun setUpRecyclerViews() {
        menstrualFlowRecyclerView = binding.menstrualFlowRv
        menstrualFlowAdapter = SymptomsAdapter(symptomEvent)
        setUpRecycler(menstrualFlowRecyclerView, menstrualFlowAdapter)

        sexDriveRecyclerView = binding.sexDriveRv
        sexDriveAdapter = SymptomsAdapter(symptomEvent)
        setUpRecycler(sexDriveRecyclerView, sexDriveAdapter)

        moodRecyclerView = binding.moodRv
        moodAdapter = SymptomsAdapter(symptomEvent)
        setUpRecycler(moodRecyclerView, moodAdapter)

        symptomsRecyclerView = binding.symptomsRv
        symptomsAdapter = SymptomsAdapter(symptomEvent)
        setUpRecycler(symptomsRecyclerView, symptomsAdapter)

        vaginalRecyclerView = binding.vaginalDischargeRv
        vaginalAdapter = SymptomsAdapter(symptomEvent)
        setUpRecycler(vaginalRecyclerView, vaginalAdapter)

        otherRecyclerView = binding.otherRv
        otherAdapter = SymptomsAdapter(symptomEvent)
        setUpRecycler(otherRecyclerView, otherAdapter)
    }

    private fun setUpRecycler(recyclerView: RecyclerView, symptomsAdapter: SymptomsAdapter) {
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 4)
        recyclerView.adapter = symptomsAdapter
    }

    override fun onStop() {
        viewModel.saveSymptoms()
        super.onStop()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}