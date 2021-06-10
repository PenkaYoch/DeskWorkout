package bg.deskworkout.deskworkout.ui.workoutRoutine

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.ContentValues
import android.content.Intent
import android.database.ContentObserver
import android.database.Cursor
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import bg.deskworkout.deskworkout.database.WorkoutDB
import bg.deskworkout.deskworkout.model.Workout
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


private const val TAG = "Workouts ViewModel"

//enum class SortColumns{
//    ID,
//    NAME,
//    COURSE
//}

class WorkoutRoutineViewModel(application: Application) : AndroidViewModel(application) {

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
            WorkoutDB.CONTENT_URI,
            true, contentObserver
        )
        loadStudents()

    }

    private fun loadStudents() {
        GlobalScope.launch {
            val cursor = getApplication<Application>().contentResolver
                .query(WorkoutDB.CONTENT_URI, null, null, null, WorkoutDB.Columns.ID) //order
            databaseCursor.postValue(cursor)
        }
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

//        if (!isUpdate){
        GlobalScope.launch {
            val uri =
                getApplication<Application>().contentResolver?.insert(
                    WorkoutDB.CONTENT_URI,
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

    fun fillDatabaseWithDefaultWorkouts() {
        putWorkout(Workout("Workout 1", "", "Workout 1 description...", "Default", 0))
        putWorkout(Workout("Workout 2", "", "Workout 2 description...", "Default", 0))
        putWorkout(Workout("Workout 3", "", "Workout 3 description...", "Default", 0))
        putWorkout(Workout("Workout 4", "", "Workout 4 description...", "Default", 0))
        putWorkout(Workout("Workout 5", "", "Workout 5 description...", "Default", 0))
        putWorkout(Workout("Workout 6", "", "Workout 6 description...", "Default", 0))
    }

    override fun onCleared() {
        getApplication<Application>().contentResolver.unregisterContentObserver(contentObserver)
    }
}
