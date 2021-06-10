package bg.deskworkout.deskworkout.ui.workoutBenefits

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import bg.deskworkout.deskworkout.R

class DeskWorkoutBenefits : Fragment() {

    companion object {
        fun newInstance() = DeskWorkoutBenefits()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.desk_workout_benefits_fragment, container, false)
    }

}