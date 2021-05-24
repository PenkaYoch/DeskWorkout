package bg.deskworkout.deskworkout

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner

class DeskWorkoutApplication : Application(), LifecycleObserver {

    companion object {
        lateinit var applicationContext: Context
            private set
        var isAppInForeground = false
            private set
    }

    override fun onCreate() {
        super.onCreate()

        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onAppBackground() {
        Log.d("Desk Workout","App is in background")
        isAppInForeground = false
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onAppForeground() {
        Log.d("Desk Workout","App is in foreground")
        isAppInForeground = true
    }
}