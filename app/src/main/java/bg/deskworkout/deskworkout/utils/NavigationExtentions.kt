package bg.deskworkout.deskworkout.utils

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.NavGraph
import androidx.navigation.NavOptions
import androidx.navigation.NavController
import androidx.navigation.fragment.FragmentNavigator
import bg.deskworkout.deskworkout.R
import kotlin.reflect.KClass


fun <T : Fragment> NavController.navigate(
    resID: Int,
    clazz: KClass<T>,
    args: Bundle? = null,
    options: NavOptions? = null
    ){
    val currentGraph = this.currentGraph()
    if(currentGraph.findNode(resID) == null) {
        val fragmentNavigator = this.navigatorProvider.getNavigator(FragmentNavigator::class.java)
        val destination = fragmentNavigator.createDestination()
        destination.id = resID
        destination.className = clazz.qualifiedName!!
        currentGraph.addDestination(destination)
    }
    navigate(resID, args, options)
}

fun slideNavigationOptions(): NavOptions {
    return NavOptions.Builder()
        .setEnterAnim(R.anim.slide_in_right)
        .setExitAnim(R.anim.slide_out_right)
        .setPopEnterAnim(R.anim.slide_in_left)
        .setPopExitAnim(R.anim.slide_out_right)
        .build()
}

    private fun NavController.currentGraph(): NavGraph {
        val currentDestination = this.currentDestination ?: this.graph
        return (currentDestination as? NavGraph) ?: currentDestination.parent!!
    }
