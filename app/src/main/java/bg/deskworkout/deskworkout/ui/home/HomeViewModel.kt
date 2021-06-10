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

    lateinit var recyclerViewAdapter: HomeRecyclerViewAdapter
    var listener: HomeRecyclerViewAdapter.OnRecyclerViewItemClickedListener? = null

    fun initRecyclerViewAdapter(recyclerView: RecyclerView) {
        this.recyclerViewAdapter = HomeRecyclerViewAdapter(listener = this.listener!!)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = recyclerViewAdapter
        recyclerViewAdapter.notifyDataSetChanged()
    }
}