package bg.deskworkout.deskworkout.ui.exercises

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
import bg.deskworkout.deskworkout.database.WorkoutsExercisesDB
import bg.deskworkout.deskworkout.model.Exercise
import bg.deskworkout.deskworkout.model.Workout
import bg.deskworkout.deskworkout.model.WorkoutExercise
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ExercisesViewModel(application: Application) : AndroidViewModel(application) {

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
                .query(WorkoutsExercisesDB.CONTENT_URI, null, null, null, WorkoutsExercisesDB.Columns.WORKOUT_ID) //order
            databaseCursor.postValue(cursor)
        }
    }

    fun putRelation(newRelation: WorkoutExercise) { //, isUpdate: Boolean, oldFacNumber: Long
        val values = ContentValues()
//        values.put(WorkoutDB.Columns.ID, newStudent.id)
        values.put(WorkoutsExercisesDB.Columns.WORKOUT_ID,  newRelation.workoutID)
        values.put(WorkoutsExercisesDB.Columns.EXERCISE_ID, newRelation.exerciseID)
        values.put(WorkoutsExercisesDB.Columns.SERIES_COUNT,newRelation.seriesCount)

//        if (!isUpdate){
        GlobalScope.launch {
            val uri =
                getApplication<Application>().contentResolver?.insert(
                    WorkoutsExercisesDB.CONTENT_URI,
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
        putRelation(WorkoutExercise(workoutID = 1L, exerciseID = 1, seriesCount = 15))
        putRelation(WorkoutExercise(workoutID = 1L, exerciseID = 2, seriesCount = 15))
        putRelation(WorkoutExercise(workoutID = 1L, exerciseID = 3, seriesCount = 15))
    }

    override fun onCleared() {
        getApplication<Application>().contentResolver.unregisterContentObserver(contentObserver)
    }
}
