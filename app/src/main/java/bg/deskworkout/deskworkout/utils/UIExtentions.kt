package bg.deskworkout.deskworkout.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

fun <T: View> inflateView(resID: Int, parent: ViewGroup, attachToParent: Boolean = false): T {
    @Suppress("UNCHECKED_CAST")
    return LayoutInflater.from(parent.context).inflate(resID, parent, attachToParent) as T
}