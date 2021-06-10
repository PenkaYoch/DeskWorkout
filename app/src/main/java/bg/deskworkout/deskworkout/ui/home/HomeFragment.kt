package bg.deskworkout.deskworkout.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import bg.deskworkout.deskworkout.R
import bg.deskworkout.deskworkout.databinding.FragmentHomeBinding
import bg.deskworkout.deskworkout.factory.HomeViewModelFactory
import bg.deskworkout.deskworkout.ui.workoutBenefits.DeskWorkoutBenefits
import bg.deskworkout.deskworkout.ui.workoutRoutine.WorkoutRoutineFragment
import bg.deskworkout.deskworkout.utils.Alerts
import bg.deskworkout.deskworkout.utils.navigate
import bg.deskworkout.deskworkout.utils.slideNavigationOptions

class HomeFragment : Fragment(), HomeRecyclerViewAdapter.OnRecyclerViewItemClickedListener {

    private lateinit var binding: FragmentHomeBinding
    val homeViewModel: HomeViewModel by viewModels { HomeViewModelFactory(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.homeViewModel.listener = this
        this.homeViewModel.initRecyclerViewAdapter(recyclerView = binding.recyclerView)
    }

    override fun onRecyclerViewListItemClicked(position: Int) {
        when(position) {
            0 -> {
                findNavController().navigate(
                    R.id.nav_workout_exercises,
                    WorkoutRoutineFragment::class,
                    null,
                    slideNavigationOptions()
                )
            }
            1 -> {
                findNavController().navigate(
                    R.id.deskWorkoutBenefits,
                    DeskWorkoutBenefits::class,
                    null,
                    slideNavigationOptions()
                )
            }
            2 -> {
                Alerts.showDefaultAlert(requireContext(), "Info", "Coming soon!")
            }
        }
    }
}