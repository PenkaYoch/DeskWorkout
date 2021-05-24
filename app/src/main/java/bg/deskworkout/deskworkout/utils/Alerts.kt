package bg.deskworkout.deskworkout.utils

import android.app.AlertDialog
import android.content.Context

object Alerts {

    fun showDefaultAlert(context: Context, title: String?, message: String, onClose: (() -> Unit)? = null) {
//        if (Application.isAppp)
        val builder = AlertDialog.Builder(context)
            .setMessage("Hey, Hey")
            .setPositiveButton("OK") { _, _ -> onClose?.invoke() }
            .setOnCancelListener { onClose?.invoke() }
            .show()
    }
}