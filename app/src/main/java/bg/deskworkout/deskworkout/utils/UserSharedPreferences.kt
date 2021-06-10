package bg.deskworkout.deskworkout.utils

import android.content.Context
import bg.deskworkout.deskworkout.DeskWorkoutApplication
import java.util.*

object UserSharedPreferences {

    private const val SHARED_PREFERENCES_NAME = "bg.deskworkout.deskworkout.Prefs"

    private val sharedPreferences = DeskWorkoutApplication.applicationContext.getSharedPreferences(
            SHARED_PREFERENCES_NAME,
            Context.MODE_PRIVATE
    )

    var notificationTime: Long
        set(value) { putLong("notificationTime", value) }
        get() = getLong("notificationTime") ?: 0

    var lastSelectedNotification: String
        set(value) { putString("lastSelectedNotification", value) }
        get() = getString("lastSelectedNotification") ?: ""

    var notificationChannelID: String
        set(value) { putString("notificationChannelID", value) }
        get() = getString("notificationChannelID") ?: ""

    private fun putString(key: String, value: String) {
        this.sharedPreferences.edit().putString(key, value).apply()
    }

    private fun getString(key: String): String? {
        return if (this.sharedPreferences.contains(key)) {
            this.sharedPreferences.getString(key, "")
        } else {
            null
        }
    }

    private fun putLong(key: String, value: Long) {
        this.sharedPreferences.edit().putLong(key, value).apply()
    }

    private fun getLong(key: String): Long? {
        return if (this.sharedPreferences.contains(key)) {
            this.sharedPreferences.getLong(key, 0)
        } else {
            null
        }
    }
}