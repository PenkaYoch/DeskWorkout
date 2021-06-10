package bg.deskworkout.deskworkout.ui.notifications

/** Represents standard data needed for a Notification.  */
class UserNotification(
    var contentTitle: String? = null,
    var contentText: String? = null,
    var priority: Int = 0,
    var channelId: String? = null,
    var channelName: CharSequence? = null,
    var channelDescription: String? = null,
    var channelImportance: Int = 0,
    var isChannelEnableVibrate: Boolean = false,
    var channelLockScreenVisibility: Int = 0,
    var bigText: String? = null,
    var bigContentTitle: String? = null,
    var summaryText: String? = null
)
