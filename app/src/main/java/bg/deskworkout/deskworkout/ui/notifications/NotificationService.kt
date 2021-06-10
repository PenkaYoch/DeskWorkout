package bg.deskworkout.deskworkout.ui.notifications

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import bg.deskworkout.deskworkout.DeskWorkoutApplication
import bg.deskworkout.deskworkout.MainActivity
import bg.deskworkout.deskworkout.utils.UserSharedPreferences


class NotificationService : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val bigTextStyle =
            NotificationCompat.BigTextStyle()
                .bigText(GlobalNotificationBuilder.textStyleReminderData?.bigText)
                .setBigContentTitle(GlobalNotificationBuilder.textStyleReminderData?.bigContentTitle)
                .setSummaryText(GlobalNotificationBuilder.textStyleReminderData?.summaryText)
        val pendingIntent = PendingIntent.getActivity(
            context, 0, Intent(
                context,
                MainActivity::class.java
            ), 0
        )
        // Notification Channel Id is ignored for Android pre O (26).
        var notificationCompatBuilder =
            GlobalNotificationBuilder.getNotificationCompatBuilderInstance()
        if (notificationCompatBuilder == null) {
            notificationCompatBuilder = UserSharedPreferences.notificationChannelID.let {
                NotificationCompat.Builder(
                    DeskWorkoutApplication.applicationContext, it
                )
            }
            GlobalNotificationBuilder.setNotificationCompatBuilderInstance(notificationCompatBuilder)
        }
        val notification =
            notificationCompatBuilder
                .setStyle(bigTextStyle)
                .setContentTitle(GlobalNotificationBuilder.textStyleReminderData?.contentTitle)
                .setContentText(GlobalNotificationBuilder.textStyleReminderData?.contentText)
                .setSmallIcon(bg.deskworkout.deskworkout.R.drawable.ic_launcher)
                .setContentIntent(pendingIntent)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setColor(
                    ContextCompat.getColor(
                        DeskWorkoutApplication.applicationContext,
                        bg.deskworkout.deskworkout.R.color.colorPrimary
                    )
                )
                .setCategory(Notification.CATEGORY_REMINDER)
                .setPriority(
                    GlobalNotificationBuilder.textStyleReminderData?.priority ?: 0
                )
                .setVisibility(
                    GlobalNotificationBuilder.textStyleReminderData?.channelLockScreenVisibility
                        ?: 0
                )
        val notificationManager =
            context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(1, notification.build())
    }
}