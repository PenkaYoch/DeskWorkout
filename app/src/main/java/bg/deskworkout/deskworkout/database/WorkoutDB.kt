package bg.deskworkout.deskworkout.database

import android.content.ContentUris
import android.net.Uri
import android.provider.BaseColumns

object WorkoutDB {
    internal const val TABLE_NAME = "Workouts"

    /**
     * The URI to access the table
     */
    val CONTENT_URI: Uri = Uri.withAppendedPath(CONTENT_AUTHORITY_URI, TABLE_NAME)

    const val CONTENT_TYPE = "vnd.android.cursor.dir/vnd.$CONTENT_AUTHORITY.${TABLE_NAME}"
    const val CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.$CONTENT_AUTHORITY.${TABLE_NAME}"

    object Columns {
        const val ID = BaseColumns._ID
        const val NAME = "Name"
        const val IMAGE_NAME = "Image_name"
        const val DESCRIPTION = "Description"
        const val TYPE = "Type"
        const val DO_TIMES = "Do_times"
    }

    fun getId(uri: Uri):Long{
        return ContentUris.parseId(uri)
    }

    fun buildUriFromId(id:Long): Uri {
        return ContentUris.withAppendedId(CONTENT_URI,id)
    }

}