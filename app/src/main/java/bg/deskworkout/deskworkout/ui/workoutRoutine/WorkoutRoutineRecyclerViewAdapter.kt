package bg.deskworkout.deskworkout.ui.workoutRoutine

import android.database.Cursor
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import bg.deskworkout.deskworkout.R
import bg.deskworkout.deskworkout.database.WorkoutDB
import bg.deskworkout.deskworkout.databinding.ListItemWorkoutBinding
import bg.deskworkout.deskworkout.model.Workout
import bg.deskworkout.deskworkout.ui.home.HomeRecyclerViewAdapter

/**
 * [RecyclerView.Adapter] that can display a [DummyItem].
 * TODO: Replace the implementation with code for your data type.
 */
class WorkoutRoutineRecyclerViewAdapter(
    private var cursor: Cursor?, private val listener: OnRecyclerViewItemClickedListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface OnRecyclerViewItemClickedListener {
        fun onRecyclerViewListItemClicked(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val holder = ListItemWorkoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return WorkoutViewHolder(holder)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val cursor = cursor

        if (cursor != null && cursor.count != 0) {
            if (!cursor.moveToPosition(position)) {
                throw IllegalStateException("Couldn't move cursor to position $position")
            }
            val item = Workout(
                cursor.getString(cursor.getColumnIndex(WorkoutDB.Columns.NAME)),
                cursor.getString(cursor.getColumnIndex(WorkoutDB.Columns.IMAGE_NAME)),
                cursor.getString(cursor.getColumnIndex(WorkoutDB.Columns.DESCRIPTION)),
                cursor.getString(cursor.getColumnIndex(WorkoutDB.Columns.TYPE)),
                cursor.getInt(cursor.getColumnIndex(WorkoutDB.Columns.DO_TIMES))
            )

            val workoutView = (holder as WorkoutViewHolder).workoutViewHolder
            workoutView.workoutRoutineLayout.setOnClickListener {
                listener.onRecyclerViewListItemClicked(position)
            }
            workoutView.workoutName.text = item.name
            workoutView.workoutDescription.text = item.description
            workoutView.workoutNumberTextView.text =
                cursor.getString(cursor.getColumnIndex(WorkoutDB.Columns.ID))
        }
    }

    override fun getItemCount(): Int = cursor?.count ?: 0//values?.size ?: 0

    inner class WorkoutViewHolder(val workoutViewHolder: ListItemWorkoutBinding) :
        RecyclerView.ViewHolder(workoutViewHolder.root)


    /**Следващата функция, е подхода, който съм избрал за актуализиране на данните в контейнера
     * и съответното им представяне на потребителя
     */
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