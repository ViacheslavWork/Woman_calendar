package woman.calendar.every.day.health.ui.advices

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import org.koin.androidx.viewmodel.ext.android.viewModel
import woman.calendar.every.day.health.R
import woman.calendar.every.day.health.databinding.FragmentAdvicesBinding

class AdvicesFragment : Fragment(R.layout.fragment_advices) {
    private var _binding: FragmentAdvicesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AdvicesViewModel by viewModel()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentAdvicesBinding.bind(view)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}