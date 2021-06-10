package bg.deskworkout.deskworkout.ui.createWorkout

import android.app.Application
import android.content.ContentValues
import android.database.ContentObserver
import android.database.Cursor
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import bg.deskworkout.deskworkout.database.ExerciseDB
import bg.deskworkout.deskworkout.database.WorkoutDB
import bg.deskworkout.deskworkout.model.Exercise
import bg.deskworkout.deskworkout.model.Workout
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CreateWorkoutViewModel(application: Application) : AndroidViewModel(application) {

    private val contentObserver = object : ContentObserver(null) {
        override fun onChange(selfChange: Boolean, uri: Uri?) {
            loadStudents()
        }
    }

    private val databaseCursor = MutableLiveData<Cursor>()
    val cursor: LiveData<Cursor>
        get() = databaseCursor

    init {
        getApplication<Application>().contentResolver.registerContentObserver(
            ExerciseDB.CONTENT_URI,
            true, contentObserver
        )
        loadStudents()

    }

    private fun loadStudents() {
        GlobalScope.launch {
            val cursor = getApplication<Application>().contentResolver
                .query(ExerciseDB.CONTENT_URI, null, null, null, ExerciseDB.Columns.ID) //order
            databaseCursor.postValue(cursor)
        }
    }

    fun putExercise(newExercise: Exercise) { //, isUpdate: Boolean, oldFacNumber: Long
        val values = ContentValues()
//        values.put(WorkoutDB.Columns.ID, newStudent.id)
        values.put(ExerciseDB.Columns.NAME, newExercise.name)
        values.put(ExerciseDB.Columns.IMAGE_NAME, newExercise.imageName)
        values.put(ExerciseDB.Columns.DESCRIPTION, newExercise.description)

//        if (!isUpdate){
        GlobalScope.launch {
            val uri =
                getApplication<Application>().contentResolver?.insert(
                    ExerciseDB.CONTENT_URI,
                    values
                )
        }
//        } else {
//            GlobalScope.launch {
//                getApplication<Application>().contentResolver?.update(
//                        WorkoutDB.buildUriFromId(oldFacNumber),values,null,null)
//            }
//        }
    }

    fun fillDatabaseWithDefaultExercises() {
        putExercise(Exercise(name = "Frog jumps", imageName = "ic_ex_frog_jumps", description = ""))
        putExercise(Exercise(name = "Static squat hold", imageName = "ic_ex_static_squat_hold", description = ""))
        putExercise(Exercise(name = "Jump squats", imageName = "ic_ex_jump_squates", description = ""))
        putExercise(Exercise(name = "Standing long jumps", imageName = "ic_ex_standing_long_jumps", description = ""))
        putExercise(Exercise(name = "Plank jacks", imageName = "ic_ex_plank_jacks", description = ""))
        putExercise(Exercise(name = "Crab walks", imageName = "ic_ex_crab_walks", description = ""))
        putExercise(Exercise(name = "Standing arm circles", imageName = "ic_ex_standing_arm_circle", description = ""))
        putExercise(Exercise(name = "Tuck jumps", imageName = "ic_ex_tuck_jumps", description = ""))
        putExercise(Exercise(name = "Bench tricep dips", imageName = "ic_bench_tricep", description = ""))
        putExercise(Exercise(name = "Duck walks", imageName = "ic_ex_duck_walks", description = ""))
    }

    fun putWorkout(newWorkout: Workout) { //, isUpdate: Boolean, oldFacNumber: Long
        val values = ContentValues()
//        values.put(WorkoutDB.Columns.ID, newStudent.id)
        values.put(WorkoutDB.Columns.NAME, newWorkout.name)
        values.put(WorkoutDB.Columns.IMAGE_NAME, newWorkout.imageName)
        values.put(WorkoutDB.Columns.DESCRIPTION, newWorkout.description)
        // SET DEFAULT VALUES FOR THESE PROPERTIES
        values.put(WorkoutDB.Columns.TYPE, newWorkout.type)
        values.put(WorkoutDB.Columns.DO_TIMES, newWorkout.doTimes)

        GlobalScope.launch {
            val uri =
                getApplication<Application>().contentResolver?.insert(
                    WorkoutDB.CONTENT_URI,
                    values
                )
        }
    }

    override fun onCleared() {
        getApplication<Application>().contentResolver.unregisterContentObserver(contentObserver)
    }
}
