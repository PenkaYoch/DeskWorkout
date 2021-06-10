package bg.deskworkout.deskworkout.ui.exercises

import android.database.Cursor
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import bg.deskworkout.deskworkout.databinding.FragmentExercisesBinding

class ExercisesFragment : Fragment() {

    private lateinit var binding: FragmentExercisesBinding
    private val viewModel by lazy { ViewModelProvider(this).get(ExercisesViewModel::class.java) }

    private val mAdapter = ExercisesRecyclerViewAdapter(null)

    private var cursor: Cursor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.cursor.observe(this, { cursor: Cursor ->
            if (cursor.count == 0) {
                viewModel.fillDatabaseWithDefaultExercises()
            } else {
                mAdapter.swapCursor(cursor)?.close()
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExercisesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.exerciseListRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.exerciseListRecyclerView.adapter = mAdapter
    }

    override fun onDestroy() {
        super.onDestroy()
        cursor?.close()
    }
}