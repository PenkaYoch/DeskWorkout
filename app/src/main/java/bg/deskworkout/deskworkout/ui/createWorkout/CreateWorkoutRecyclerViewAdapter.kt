package bg.deskworkout.deskworkout.ui.createWorkout

import android.database.Cursor
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import bg.deskworkout.deskworkout.database.WorkoutDB
import bg.deskworkout.deskworkout.databinding.ListItemCreateWorkoutBinding
import bg.deskworkout.deskworkout.model.Exercise
import bg.deskworkout.deskworkout.utils.ImagesUtils


class CreateWorkoutRecyclerViewAdapter(
    private var cursor: Cursor?
)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val holder = ListItemCreateWorkoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ExerciseViewHolder(holder)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val cursor = cursor

        if (cursor != null && cursor.count != 0) {
            if (!cursor.moveToPosition(position)) {
                throw IllegalStateException("Couldn't move cursor to position $position")
            }
            val item = Exercise(
                cursor.getString(cursor.getColumnIndex(WorkoutDB.Columns.NAME)),
                cursor.getString(cursor.getColumnIndex(WorkoutDB.Columns.IMAGE_NAME)),
                cursor.getString(cursor.getColumnIndex(WorkoutDB.Columns.DESCRIPTION))
            )
            val workoutView = (holder as ExerciseViewHolder).exerciseViewHolder
            workoutView.exerciseName.text = item.name
            workoutView.exerciseImageView.setImageResource(ImagesUtils.getExerciseImage(item.name))
        }
    }

    override fun getItemCount(): Int = cursor?.count ?: 0

    inner class ExerciseViewHolder(val exerciseViewHolder: ListItemCreateWorkoutBinding) : RecyclerView.ViewHolder(
        exerciseViewHolder.root
    )

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