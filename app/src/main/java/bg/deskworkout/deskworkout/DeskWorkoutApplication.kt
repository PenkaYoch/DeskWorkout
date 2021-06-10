package bg.deskworkout.deskworkout

import android.app.Application
import android.app.NotificationManager
import android.content.Context
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import bg.deskworkout.deskworkout.ui.notifications.GlobalNotificationBuilder
import bg.deskworkout.deskworkout.ui.notifications.UserNotification
import bg.deskworkout.deskworkout.utils.NotificationUtils
import bg.deskworkout.deskworkout.utils.UserSharedPreferences

class DeskWorkoutApplication : Application(), LifecycleObserver {

    companion object {
        lateinit var applicationContext: Context
            private set
        var isAppInForeground = false
            private set
    }

    override fun onCreate() {
        super.onCreate()

        Companion.applicationContext = this.applicationContext
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
        /*
           Create notification channel
        */
        this.createNotificationCahnnel()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onAppBackground() {
        Log.d("Desk Workout", "App is in background")
        isAppInForeground = false
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onAppForeground() {
        Log.d("Desk Workout", "App is in foreground")
        isAppInForeground = true
    }

    private fun createNotificationCahnnel() {
        val textStyleReminderData = UserNotification(
            "Time for Desk Workout",
            UserSharedPreferences.lastSelectedNotification,//"Content Text",
            NotificationCompat.PRIORITY_DEFAULT,
            "channel_reminder_1",
            "Sample Reminder",
            "Sample Reminder Notifications",
            NotificationManager.IMPORTANCE_DEFAULT,
            false,
            NotificationCompat.VISIBILITY_PUBLIC
        )
        GlobalNotificationBuilder.textStyleReminderData = textStyleReminderData
        val notificationChannelId: String? =
            NotificationUtils.createNotificationChannel(
                DeskWorkoutApplication.applicationContext,
                textStyleReminderData
            )
        notificationChannelId?.let {
            UserSharedPreferences.notificationChannelID = it
        }
    }
}