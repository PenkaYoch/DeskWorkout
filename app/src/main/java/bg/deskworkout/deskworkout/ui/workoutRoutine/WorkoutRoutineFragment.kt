package bg.deskworkout.deskworkout.ui.workoutRoutine

import android.app.AlarmManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import bg.deskworkout.deskworkout.DeskWorkoutApplication
import bg.deskworkout.deskworkout.R
import bg.deskworkout.deskworkout.databinding.FragmentWorkoutRoutineListBinding
import bg.deskworkout.deskworkout.ui.common.NotificationTimeDialog
import bg.deskworkout.deskworkout.ui.exercises.ExercisesFragment
import bg.deskworkout.deskworkout.ui.notifications.NotificationService
import bg.deskworkout.deskworkout.ui.notifications.UserNotification
import bg.deskworkout.deskworkout.utils.NotificationUtils
import bg.deskworkout.deskworkout.utils.UserSharedPreferences
import bg.deskworkout.deskworkout.utils.navigate
import bg.deskworkout.deskworkout.utils.slideNavigationOptions
import java.util.*


/**
 * A fragment representing a list of Items.
 */
class WorkoutRoutineFragment : Fragment(), WorkoutRoutineRecyclerViewAdapter.OnRecyclerViewItemClickedListener {

    private lateinit var binding: FragmentWorkoutRoutineListBinding
    private val viewModel by lazy { ViewModelProvider(this).get(WorkoutRoutineViewModel::class.java) }
    private val mAdapter = WorkoutRoutineRecyclerViewAdapter(null, this)

    private var cursor: Cursor? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.cursor.observe(this, { cursor: Cursor ->
            if (cursor.count == 0) {
                viewModel.fillDatabaseWithDefaultWorkouts()
            } else {
                mAdapter.swapCursor(cursor)?.close()
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWorkoutRoutineListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.workoutListRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.workoutListRecyclerView.adapter = mAdapter
        binding.timeButton.setOnClickListener {
            showTimePickerDialog()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cursor?.close()
    }

    private fun showTimePickerDialog() {//v: View)
        NotificationTimeDialog().show(parentFragmentManager, "timePicker")
    }

    override fun onRecyclerViewListItemClicked(position: Int) {
        findNavController().navigate(
            R.id.exercisesFragment,
            ExercisesFragment::class,
            null,
            slideNavigationOptions()
        )
    }

}