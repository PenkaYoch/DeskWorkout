package bg.deskworkout.deskworkout.ui.home

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import bg.deskworkout.deskworkout.databinding.ListItemBenefitsDeskWorkoutBinding
import bg.deskworkout.deskworkout.databinding.ListItemUserWorkoutStatisticsBinding
import bg.deskworkout.deskworkout.databinding.ListItemWorkoutRoutineBinding

class HomeViewModel(private val context: Context) : ViewModel() {

    companion object {
        const val WORKOUT_ROUTINE_VIEW_TYPE = 0
        const val BENEFITS_VIEW_TYPE = 1
        const val STATISTICS_VIEW_TYPE = 2
    }

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    lateinit var recyclerViewAdapter: HomeRecyclerViewAdapter
    var listener: HomeRecyclerViewAdapter.OnRecyclerViewItemClickedListener? = null

    fun initRecyclerViewAdapter(recyclerView: RecyclerView) {
        this.recyclerViewAdapter = HomeRecyclerViewAdapter(listener = this.listener!!)
//        this.recyclerViewAdapter.registerViewHolderProvider(WORKOUT_ROUTINE_VIEW_TYPE) {parent ->
//            WorkoutRoutineViewHolder(WorkoutRoutineListItemView.inflate(parent))
//        }
//        this.recyclerViewAdapter.registerViewHolderProvider(BENEFITS_VIEW_TYPE) { parent ->
//            BenefitsDeskWorkoutViewHolder(BenefitsDeskWorkoutListItemView.inflate(parent))
//        }
//        this.recyclerViewAdapter.registerViewHolderProvider(STATISTICS_VIEW_TYPE) { parent ->
//            UserWorkoutStatisticsViewHolder(UserWorkoutStatisticsListItemView.inflate(parent))
//        }
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = recyclerViewAdapter
        recyclerViewAdapter.notifyDataSetChanged()
    }
}