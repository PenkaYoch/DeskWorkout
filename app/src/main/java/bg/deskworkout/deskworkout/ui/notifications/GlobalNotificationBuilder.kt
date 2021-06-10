package bg.deskworkout.deskworkout.ui.notifications

import androidx.core.app.NotificationCompat

object GlobalNotificationBuilder {

    private var globalNotificationCompatBuilder: NotificationCompat.Builder? = null
    var textStyleReminderData: UserNotification? = null

    fun setNotificationCompatBuilderInstance(builder: NotificationCompat.Builder?) {
        globalNotificationCompatBuilder = builder
    }

    fun getNotificationCompatBuilderInstance(): NotificationCompat.Builder? {
        return globalNotificationCompatBuilder
    }
}
