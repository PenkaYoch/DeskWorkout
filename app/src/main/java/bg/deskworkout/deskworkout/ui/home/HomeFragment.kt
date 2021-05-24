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
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
        //inflater.inflate(R.layout.fragment_home, container, false)
    }
//    {
//        homeViewModel =
//                ViewModelProvider(this).get(HomeViewModel::class.java)
//        val root = inflater.inflate(R.layout.fragment_home, container, false)
//        val textView: TextView = root.findViewById(R.id.text_home)
//        homeViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })
//        return root
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.homeViewModel.listener = this
        this.homeViewModel.initRecyclerViewAdapter(recyclerView = binding.recyclerView)
    }

    override fun onRecyclerViewListItemClicked(position: Int) {
        findNavController().navigate(R.id.nav_workout_exercises,
        WorkoutRoutineFragment::class,
        null,
        slideNavigationOptions())
        //Alerts.showDefaultAlert(requireContext(), "Show", "Show $position")
    }
}