package bg.deskworkout.deskworkout.ui.exercises

import android.database.Cursor
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import bg.deskworkout.deskworkout.database.WorkoutDB
import bg.deskworkout.deskworkout.database.WorkoutsExercisesDB
import bg.deskworkout.deskworkout.databinding.ListItemExerciseBinding
import bg.deskworkout.deskworkout.model.Exercise
import bg.deskworkout.deskworkout.model.WorkoutExercise

class ExercisesRecyclerViewAdapter(
    private var cursor: Cursor?)//private val values: List<Workout>?
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val holder = ListItemExerciseBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ExercisesViewHolder(holder)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val cursor = cursor

        if (cursor != null && cursor.count != 0) {
            if (!cursor.moveToPosition(position)) {
                throw IllegalStateException("Couldn't move cursor to position $position")
            }
            val item = WorkoutExercise(cursor.getLong(cursor.getColumnIndex(WorkoutsExercisesDB.Columns.WORKOUT_ID)),
                cursor.getLong(cursor.getColumnIndex(WorkoutsExercisesDB.Columns.EXERCISE_ID)),
                cursor.getInt(cursor.getColumnIndex(WorkoutsExercisesDB.Columns.SERIES_COUNT)))

            val workoutView = (holder as ExercisesViewHolder).workoutViewHolder
            workoutView.exerciseName.text = item.workoutID.toString()
            workoutView.exerciseDescription.text = item.exerciseID.toString()
        }
    }

    override fun getItemCount(): Int = cursor?.count ?: 0

    inner class ExercisesViewHolder(val workoutViewHolder:ListItemExerciseBinding) : RecyclerView.ViewHolder(workoutViewHolder.root)

    fun swapCursor(newCursor: Cursor?): Cursor? {
        if (newCursor === cursor) {
            return null
        }
        val numItems = itemCount
        val oldCursor = cursor
        cursor = newCursor
        if (newCursor != null) {
            notifyDataSetChanged()
        } else {
            notifyItemRangeRemoved(0, numItems)
        }

        return oldCursor
    }
}