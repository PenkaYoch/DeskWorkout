package bg.deskworkout.deskworkout.ui.createWorkout

import android.database.Cursor
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import bg.deskworkout.deskworkout.R
import bg.deskworkout.deskworkout.databinding.FragmentCreateWorkoutBinding
import bg.deskworkout.deskworkout.databinding.FragmentExercisesBinding
import bg.deskworkout.deskworkout.model.Workout
import bg.deskworkout.deskworkout.ui.exercises.ExercisesRecyclerViewAdapter
import bg.deskworkout.deskworkout.ui.exercises.ExercisesViewModel

class CreateWorkoutFragment : Fragment() {

    private lateinit var binding: FragmentCreateWorkoutBinding
    private val viewModel by lazy { ViewModelProvider(this).get(CreateWorkoutViewModel::class.java) }
    private val mAdapter = CreateWorkoutRecyclerViewAdapter(null)//,this

    private var cursor: Cursor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.cursor.observe(this, { cursor: Cursor ->
            if (cursor.count == 0) {
                viewModel.fillDatabaseWithDefaultExercises()
            } else {
                mAdapter.swapCursor(cursor)?.close()
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateWorkoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.exerciseListRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.exerciseListRecyclerView.adapter = mAdapter
        binding.createButton.setOnClickListener {
            this.viewModel.putWorkout(
                Workout(
                    binding.workoutNameEditText.text.toString(),
                    "",
                    binding.workoutDescriptionEditText.text.toString(),
                    "C", // for custom transaction
                    0
                )
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cursor?.close()
    }
}