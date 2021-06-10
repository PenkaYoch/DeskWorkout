package bg.deskworkout.deskworkout.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import bg.deskworkout.deskworkout.databinding.ListItemBenefitsDeskWorkoutBinding
import bg.deskworkout.deskworkout.databinding.ListItemUserWorkoutStatisticsBinding
import bg.deskworkout.deskworkout.databinding.ListItemWorkoutRoutineBinding
import bg.deskworkout.deskworkout.utils.errorException


public class HomeRecyclerViewAdapter(private val listener: OnRecyclerViewItemClickedListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class WorkoutRoutineViewHolder(val workoutViewHolder: ListItemWorkoutRoutineBinding) : RecyclerView.ViewHolder(workoutViewHolder.root)
    inner class BenefitsDeskWorkoutViewHolder(val benefitsViewHolder: ListItemBenefitsDeskWorkoutBinding) : RecyclerView.ViewHolder(benefitsViewHolder.root)
    inner class UserWorkoutStatisticsViewHolder(val statisticsViewHolder: ListItemUserWorkoutStatisticsBinding) : RecyclerView.ViewHolder(statisticsViewHolder.root)

    interface OnRecyclerViewItemClickedListener {
        fun onRecyclerViewListItemClicked(position: Int)
    }

    private val viewHolderProviders = mutableMapOf<Int, (parent: ViewGroup) -> RecyclerView.ViewHolder>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when(viewType) {
            0 -> {
                val holder = ListItemWorkoutRoutineBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                holder.workoutLayout.setOnClickListener {
                    listener.onRecyclerViewListItemClicked(0)
                }
                return WorkoutRoutineViewHolder(holder)
            }
            1 -> {
                val holder = ListItemBenefitsDeskWorkoutBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                holder.benefitsLayout.setOnClickListener {
                    listener.onRecyclerViewListItemClicked(1)
                }
                return BenefitsDeskWorkoutViewHolder(holder)
            }
            else -> {
                val holder = ListItemUserWorkoutStatisticsBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                holder.userWorkoutLayout.setOnClickListener {
                    listener.onRecyclerViewListItemClicked(2)
                }
                return UserWorkoutStatisticsViewHolder(holder)
            }
        }
    }

    override fun getItemCount(): Int {
        return 3
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            0 -> {
                val workoutView = (holder as WorkoutRoutineViewHolder).workoutViewHolder
            }
            1 -> {
                val benefitsView = (holder as BenefitsDeskWorkoutViewHolder).benefitsViewHolder
            }
            2 -> {
                val statisticsView = (holder as UserWorkoutStatisticsViewHolder).statisticsViewHolder
            }
            else -> errorException(message = "Not found viewType")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    fun registerViewHolderProvider(viewType: Int, viewHolderProvider: (parent: ViewGroup) -> RecyclerView.ViewHolder) {
        this.viewHolderProviders[viewType] = viewHolderProvider
    }
}