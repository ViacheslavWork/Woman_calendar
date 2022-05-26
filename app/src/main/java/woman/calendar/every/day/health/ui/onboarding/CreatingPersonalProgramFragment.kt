package woman.calendar.every.day.health.ui.onboarding

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import woman.calendar.every.day.health.R
import woman.calendar.every.day.health.databinding.FragmentCreatingPersonalProgramBinding

class CreatingPersonalProgramFragment : Fragment(R.layout.fragment_creating_personal_program) {
    private var _binding: FragmentCreatingPersonalProgramBinding? = null
    private val binding get() = _binding!!
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentCreatingPersonalProgramBinding.bind(view)
        binding.loadEllipseIv.startAnimation(
            AnimationUtils.loadAnimation(
                context,
                R.anim.ellipse_animation
            )
        )
        lifecycleScope.launch(Dispatchers.Default) {
            delay(3000)
            launch(Dispatchers.Main) {
                findNavController()
                    .navigate(CreatingPersonalProgramFragmentDirections.actionCreatingPersonalProgramFragmentToMeetFragment())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}