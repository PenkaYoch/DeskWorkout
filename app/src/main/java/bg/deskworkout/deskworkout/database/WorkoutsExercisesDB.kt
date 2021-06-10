package bg.deskworkout.deskworkout.database

import android.content.ContentUris
import android.net.Uri
import android.provider.BaseColumns

object WorkoutsExercisesDB {
    internal const val TABLE_NAME = "WorkoutExercises"

    /**
     * The URI to access the table
     */
    val CONTENT_URI: Uri = Uri.withAppendedPath(CONTENT_AUTHORITY_URI, TABLE_NAME)

    const val CONTENT_TYPE = "vnd.android.cursor.dir/vnd.$CONTENT_AUTHORITY.${TABLE_NAME}"
    const val CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.$CONTENT_AUTHORITY.${TABLE_NAME}"

    object Columns {
        const val WORKOUT_ID = "Workout_ID"
        const val EXERCISE_ID = "Exercise_ID"
        const val SERIES_COUNT = "Series_count"
    }

    fun getId(uri: Uri):Long{
        return ContentUris.parseId(uri)
    }

    fun buildUriFromId(id:Long): Uri {
        return ContentUris.withAppendedId(CONTENT_URI,id)
    }

}