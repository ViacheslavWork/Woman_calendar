package woman.calendar.every.day.health.ui.water

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.RelativeSizeSpan
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import woman.calendar.every.day.health.R
import woman.calendar.every.day.health.databinding.FragmentWaterBinding
import woman.calendar.every.day.health.utils.WeightPreferences

class WaterFragment : Fragment(R.layout.fragment_water) {
    private var _binding: FragmentWaterBinding? = null
    private val binding get() = _binding!!
    private val viewModel: WaterViewModel by viewModel()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentWaterBinding.bind(view)

        setUpListeners()
        observeVolumeOfWater()
    }

    private fun observeVolumeOfWater() {
        viewModel.volumeOfWater.observe(viewLifecycleOwner) {
            val text = String.format(
                resources.getString(R.string.volume_of_water),
                it,
                viewModel.getWaterPerDay(WeightPreferences.getWeight(requireContext()))
            )
            val cs = SpannableStringBuilder(text).apply {
                setSpan(
                    RelativeSizeSpan(0.625f),
                    5,
                    length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }

            binding.volumeOfWaterTv.text = cs

            if (it == 0F) {
                binding.glassIv.setImageResource(R.drawable.ic_glass_empty)
            } else {
                binding.glassIv.setImageResource(R.drawable.ic_glass_full)
            }
        }
    }

    private fun setUpListeners() {
        binding.backIb.setOnClickListener { findNavController().popBackStack() }
        binding.minusBtn.setOnClickListener { viewModel.subWater() }
        binding.plusBtn.setOnClickListener { viewModel.addWater() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}