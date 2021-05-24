package bg.deskworkout.deskworkout.utils

import android.content.Context
import bg.deskworkout.deskworkout.DeskWorkoutApplication

object UserSharedPreferences {

    private const val SHARED_PREFERENCES_NAME = "bg.deskworkout.deskworkout.Prefs"

    private val sharedPreferences = DeskWorkoutApplication.applicationContext.getSharedPreferences(
        SHARED_PREFERENCES_NAME,
        Context.MODE_PRIVATE
    )
}