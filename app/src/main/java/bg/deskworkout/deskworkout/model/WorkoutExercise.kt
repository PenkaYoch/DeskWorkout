package bg.deskworkout.deskworkout.model

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
@SuppressLint("ParcelCreator")
data class WorkoutExercise(
    var workoutID: Long,
    var exerciseID: Long,
    var seriesCount: Int): Parcelable
