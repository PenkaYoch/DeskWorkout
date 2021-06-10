package bg.deskworkout.deskworkout.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import bg.deskworkout.deskworkout.ui.notifications.UserNotification

object NotificationUtils {

    fun createNotificationChannel(
        context: Context,
        mockNotificationData: UserNotification
    ): String? {

        // NotificationChannels are required for Notifications on O (API 26) and above.
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            // The id of the channel.
            val channelId: String = mockNotificationData.channelId ?: ""

            // The user-visible name of the channel.
            val channelName: CharSequence = mockNotificationData.channelName ?: ""
            // The user-visible description of the channel.
            val channelDescription: String = mockNotificationData.channelDescription ?: ""
            val channelImportance: Int = mockNotificationData.channelImportance
            val channelEnableVibrate: Boolean = mockNotificationData.isChannelEnableVibrate
            val channelLockScreenVisibility: Int =
                mockNotificationData.channelLockScreenVisibility

            // Initializes NotificationChannel.
            val notificationChannel = NotificationChannel(channelId, channelName, channelImportance)
            notificationChannel.description = channelDescription
            notificationChannel.enableVibration(channelEnableVibrate)
            notificationChannel.lockscreenVisibility = channelLockScreenVisibility

            // Adds NotificationChannel to system. Attempting to create an existing notification
            // channel with its original values performs no operation, so it's safe to perform the
            // below sequence.
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)
            channelId
        } else {
            // Returns null for pre-O (26) devices.
            null
        }
    }
}