package bg.deskworkout.deskworkout.ui.home.listItemViews

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.FrameLayout
import bg.deskworkout.deskworkout.R
import bg.deskworkout.deskworkout.utils.inflateView

class UserWorkoutStatisticsListItemView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyle: Int = 0
) : FrameLayout(context, attrs, defStyle) {

    companion object {
        fun inflate(parent: ViewGroup): UserWorkoutStatisticsListItemView =
                inflateView(R.layout.list_item_user_workout_statistics, parent)
    }
}