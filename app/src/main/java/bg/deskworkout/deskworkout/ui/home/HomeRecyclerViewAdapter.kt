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

    data class GroupPosition(var groupIndex: Int, val itemIndex: Int)

    private val viewHolderProviders = mutableMapOf<Int, (parent: ViewGroup) -> RecyclerView.ViewHolder>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//        val viewHolderProvider = this.viewHolderProviders[viewType]
//                ?: errorException(message = "This viewType: $viewType  is not registered. Please, do it to continue.")
//        return viewHolderProvider.invoke(parent)
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
                workoutView.textView1.text = "Text Text"
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
//    class ViewHolder0 extends RecyclerView.ViewHolder {
//        ...
//        public ViewHolder0(View itemView){
//            ...
//        }
//    }
//
//    class ViewHolder2 extends RecyclerView.ViewHolder {
//        ...
//        public ViewHolder2(View itemView){
//            ...
//        }
//
//        @Override
//        public int getItemViewType(int position) {
//            // Just as an example, return 0 or 2 depending on position
//            // Note that unlike in ListView adapters, types don't have to be contiguous
//            return position % 2 * 2;
//        }
//
//        @Override
//        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            switch (viewType) {
//                case 0: return new ViewHolder0(...);
//                case 2: return new ViewHolder2(...);
//                ...
//            }
//        }
//
//        @Override
//        public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
//            switch (holder.getItemViewType()) {
//                case 0:
//                ViewHolder0 viewHolder0 = (ViewHolder0)holder;
//                ...
//                break;
//
//                case 2:
//                ViewHolder2 viewHolder2 = (ViewHolder2)holder;
//                ...
//                break;
//            }
//        }
}