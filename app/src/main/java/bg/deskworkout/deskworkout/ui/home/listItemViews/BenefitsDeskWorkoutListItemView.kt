package bg.deskworkout.deskworkout.ui.home.listItemViews

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.FrameLayout
import bg.deskworkout.deskworkout.R
import bg.deskworkout.deskworkout.utils.inflateView

class BenefitsDeskWorkoutListItemView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyle: Int = 0
) : FrameLayout(context, attrs, defStyle) {

    companion object {
        fun inflate(parent: ViewGroup): BenefitsDeskWorkoutListItemView =
                inflateView(R.layout.list_item_benefits_desk_workout, parent)
    }
}