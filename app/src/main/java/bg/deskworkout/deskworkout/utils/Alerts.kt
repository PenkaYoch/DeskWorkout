package bg.deskworkout.deskworkout.utils

import android.app.AlertDialog
import android.content.Context
import bg.deskworkout.deskworkout.DeskWorkoutApplication

object Alerts {

    fun showDefaultAlert(context: Context, title: String?, message: String, onClose: (() -> Unit)? = null) {
        if (DeskWorkoutApplication.isAppInForeground) {
            AlertDialog.Builder(context)
                    .setTitle(title)
                    .setMessage(message)
                    .setPositiveButton("OK") { _, _ -> onClose?.invoke() }
                    .setOnCancelListener { onClose?.invoke() }
                    .show()
        }
    }
}