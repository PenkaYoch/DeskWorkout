package bg.deskworkout.deskworkout.ui.common

import android.app.AlarmManager
import android.app.Dialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.format.DateFormat
import android.widget.TimePicker
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import bg.deskworkout.deskworkout.R
import bg.deskworkout.deskworkout.ui.notifications.NotificationService
import bg.deskworkout.deskworkout.utils.UserSharedPreferences
import java.util.*

class NotificationTimeDialog : DialogFragment(), TimePickerDialog.OnTimeSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the current time as the default values for the picker
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)

        // Create a new instance of TimePickerDialog and return it
        return TimePickerDialog(activity, R.style.DialogTheme,this, hour, minute, DateFormat.is24HourFormat(activity))
    }

    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
        // Do something with the time chosen by the user
        val notificationTime = Calendar.getInstance()
        notificationTime[Calendar.HOUR_OF_DAY] = hourOfDay
        notificationTime[Calendar.MINUTE] = minute
        notificationTime[Calendar.SECOND] = 0
        notificationTime[Calendar.AM_PM] = Calendar.PM

        UserSharedPreferences.notificationTime = notificationTime.timeInMillis
        setNotificationTime()
    }

    private fun setNotificationTime() {
        val am: AlarmManager = activity?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(activity, NotificationService::class.java)
        val pendingIntent = PendingIntent.getBroadcast(activity?.applicationContext, 0, intent, 0)
        am.setRepeating(AlarmManager.RTC_WAKEUP, UserSharedPreferences.notificationTime, 24*60*60*1000, pendingIntent)
        Toast.makeText(activity, "Notification is set!", Toast.LENGTH_SHORT).show()
    }
}
