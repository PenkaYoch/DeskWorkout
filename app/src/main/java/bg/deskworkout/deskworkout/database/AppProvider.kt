package bg.deskworkout.deskworkout.database

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteQueryBuilder
import android.net.Uri
import android.util.Log

private const val TAG = "AppProvider"

const val CONTENT_AUTHORITY = "bg.deskworkout.deskworkout.provider"
val CONTENT_AUTHORITY_URI: Uri = Uri.parse("content://$CONTENT_AUTHORITY")

const val WORKOUTS = 100
const val WORKOUTS_ID = 101

const val EXERCISES = 200
const val EXERCISES_ID = 201

const val RELATIONS = 300
const val RELATIONS_ID = 301


class AppProvider: ContentProvider() {

    private val uriMatcher by lazy { buildUriMatcher() }


    private fun buildUriMatcher(): UriMatcher {
        Log.d(TAG, "buildUriMatcher starts")
        val matcher = UriMatcher(UriMatcher.NO_MATCH)

        matcher.addURI(CONTENT_AUTHORITY, WorkoutDB.TABLE_NAME, WORKOUTS)
        matcher.addURI(CONTENT_AUTHORITY, "${WorkoutDB.TABLE_NAME}/#", WORKOUTS_ID)

        matcher.addURI(CONTENT_AUTHORITY, ExerciseDB.TABLE_NAME, EXERCISES)
        matcher.addURI(CONTENT_AUTHORITY, "${ExerciseDB.TABLE_NAME}/#", EXERCISES_ID)

        matcher.addURI(CONTENT_AUTHORITY, WorkoutsExercisesDB.TABLE_NAME, RELATIONS)

        return matcher
    }

    override fun onCreate(): Boolean {
        return true
    }

    override fun getType(uri: Uri): String {

        return when (uriMatcher.match(uri)) {

            WORKOUTS -> WorkoutDB.CONTENT_TYPE
            WORKOUTS_ID -> WorkoutDB.CONTENT_ITEM_TYPE

            EXERCISES -> ExerciseDB.CONTENT_TYPE
            EXERCISES_ID -> ExerciseDB.CONTENT_ITEM_TYPE

            RELATIONS -> WorkoutsExercisesDB.CONTENT_TYPE
            RELATIONS_ID -> WorkoutsExercisesDB.CONTENT_ITEM_TYPE

            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
    }

    override fun query(
            uri: Uri, projection: Array<out String>?, selection: String?,
            selectionArgs: Array<out String>?, sortOrder: String?
    ): Cursor? {

        val match = uriMatcher.match(uri)

        val queryBuilder = SQLiteQueryBuilder()

        when (match) {

            WORKOUTS -> queryBuilder.tables = WorkoutDB.TABLE_NAME
            WORKOUTS_ID -> {
                queryBuilder.tables = WorkoutDB.TABLE_NAME
                val queryId = WorkoutDB.getId(uri)
                queryBuilder.appendWhere("${WorkoutDB.Columns.ID} = ")
                queryBuilder.appendWhereEscapeString("$queryId")
            }

            EXERCISES -> queryBuilder.tables = ExerciseDB.TABLE_NAME
            EXERCISES_ID -> {
                queryBuilder.tables = ExerciseDB.TABLE_NAME
                val queryId = ExerciseDB.getId(uri)
                queryBuilder.appendWhere("${ExerciseDB.Columns.ID} = ")
                queryBuilder.appendWhereEscapeString("$queryId")
            }
            RELATIONS -> queryBuilder.tables = WorkoutsExercisesDB.TABLE_NAME
            RELATIONS_ID -> {
                queryBuilder.tables = WorkoutsExercisesDB.TABLE_NAME
                val queryId = WorkoutsExercisesDB.getId(uri)
                queryBuilder.appendWhere("${WorkoutsExercisesDB.Columns.WORKOUT_ID} = ")
                queryBuilder.appendWhereEscapeString("$queryId")
            }

            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }

        val context = context ?: throw NullPointerException("Context can't be null here")
        val db = AppDatabase.getInstance(context).readableDatabase
        val cursor =
                queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder)

        Log.d(TAG, "query: rows in returned cursor = ${cursor.count}")

        return cursor
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri {
        val match = uriMatcher.match(uri)

        val recordId: Long
        val returnUri: Uri

        val context = context ?: throw NullPointerException("Context can't be null here")

        when (match) {
            WORKOUTS -> {
                val db = AppDatabase.getInstance(context).writableDatabase
                recordId = db.insert(WorkoutDB.TABLE_NAME, null, values)
                if (recordId != -1L) {
                    returnUri = WorkoutDB.buildUriFromId(recordId)
                } else {
                    throw SQLException("Failed to insert, Uri was $uri")
                }
            }

            EXERCISES -> {
                val db = AppDatabase.getInstance(context).writableDatabase
                recordId = db.insert(ExerciseDB.TABLE_NAME, null, values)
                if (recordId != -1L) {
                    returnUri = ExerciseDB.buildUriFromId(recordId)
                } else {
                    throw SQLException("Failed to insert, Uri was $uri")
                }
            }

            RELATIONS -> {
                val db = AppDatabase.getInstance(context).writableDatabase
                recordId = db.insert(WorkoutsExercisesDB.TABLE_NAME, null, values)
                if (recordId != -1L) {
                    returnUri = WorkoutsExercisesDB.buildUriFromId(recordId)
                } else {
                    throw SQLException("Failed to insert, Uri was $uri")
                }
            }

            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
        if (recordId > 0) {
            context.contentResolver?.notifyChange(uri, null)
        }
        return returnUri
    }

    override fun update(
            uri: Uri, values: ContentValues?, selection: String?,
            selectionArgs: Array<out String>?
    ): Int {
        val match = uriMatcher.match(uri)

        val count: Int
        var selectionCriteria: String

        val context = context ?: throw NullPointerException("Context can't be null here")

        when (match) {
            WORKOUTS -> {
                val db = AppDatabase.getInstance(context).writableDatabase
                count = db.update(WorkoutDB.TABLE_NAME, values, selection, selectionArgs)
            }
            WORKOUTS_ID -> {
                val db = AppDatabase.getInstance(context).writableDatabase
                val id = WorkoutDB.getId(uri)
                selectionCriteria = "${WorkoutDB.Columns.ID} = $id"

                if (selection != null && selection.isNotEmpty()) {
                    selectionCriteria += "AND ($selection)"
                }
                count = db.update(WorkoutDB.TABLE_NAME, values, selectionCriteria, selectionArgs)
            }

            EXERCISES -> {
                val db = AppDatabase.getInstance(context).writableDatabase
                count = db.update(ExerciseDB.TABLE_NAME, values, selection, selectionArgs)
            }
            EXERCISES_ID -> {
                val db = AppDatabase.getInstance(context).writableDatabase
                val id = ExerciseDB.getId(uri)
                selectionCriteria = "${ExerciseDB.Columns.ID} = $id"

                if (selection != null && selection.isNotEmpty()) {
                    selectionCriteria += "AND ($selection)"
                }
                count = db.update(ExerciseDB.TABLE_NAME, values, selectionCriteria, selectionArgs)
            }

            RELATIONS -> {
                val db = AppDatabase.getInstance(context).writableDatabase
                count = db.update(WorkoutsExercisesDB.TABLE_NAME, values, selection, selectionArgs)
            }
            RELATIONS_ID -> {
                val db = AppDatabase.getInstance(context).writableDatabase
                val id = WorkoutsExercisesDB.getId(uri)
                selectionCriteria = "${WorkoutsExercisesDB.Columns.WORKOUT_ID} = $id"
                if (selection != null && selection.isNotEmpty()) {
                    selectionCriteria += "AND ($selection)"
                }
                count = db.update(WorkoutsExercisesDB.TABLE_NAME, values, selectionCriteria, selectionArgs)
            }

            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }

        if (count > 0) {
            context.contentResolver?.notifyChange(uri, null)
        }

        return count
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        val match = uriMatcher.match(uri)

        val count: Int
        var selectionCriteria: String

        val context = context ?: throw NullPointerException("Context can't be null here")

        when (match) {
            WORKOUTS -> {
                val db = AppDatabase.getInstance(context).writableDatabase
                count = db.delete(WorkoutDB.TABLE_NAME, selection, selectionArgs)
            }
            WORKOUTS_ID -> {
                val db = AppDatabase.getInstance(context).writableDatabase
                val id = WorkoutDB.getId(uri)
                selectionCriteria = "${WorkoutDB.Columns.ID} = $id"

                if (selection != null && selection.isNotEmpty()) {
                    selectionCriteria += "AND ($selection)"
                }
                count = db.delete(WorkoutDB.TABLE_NAME, selectionCriteria, selectionArgs)
            }

            EXERCISES -> {
                val db = AppDatabase.getInstance(context).writableDatabase
                count = db.delete(ExerciseDB.TABLE_NAME, selection, selectionArgs)
            }
            EXERCISES_ID -> {
                val db = AppDatabase.getInstance(context).writableDatabase
                val id = ExerciseDB.getId(uri)
                selectionCriteria = "${ExerciseDB.Columns.ID} = $id"

                if (selection != null && selection.isNotEmpty()) {
                    selectionCriteria += "AND ($selection)"
                }
                count = db.delete(ExerciseDB.TABLE_NAME, selectionCriteria, selectionArgs)
            }

            RELATIONS -> {
                val db = AppDatabase.getInstance(context).writableDatabase
                count = db.delete(WorkoutsExercisesDB.TABLE_NAME, selection, selectionArgs)
            }
            RELATIONS_ID -> {
                val db = AppDatabase.getInstance(context).writableDatabase
                val id = WorkoutsExercisesDB.getId(uri)
                selectionCriteria = "${WorkoutsExercisesDB.Columns.WORKOUT_ID} = $id"

                if (selection != null && selection.isNotEmpty()) {
                    selectionCriteria += "AND ($selection)"
                }
                count = db.delete(WorkoutsExercisesDB.TABLE_NAME, selectionCriteria, selectionArgs)
            }
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }

        if (count > 0) {
            context.contentResolver?.notifyChange(uri, null)
        }
        return count
    }
}