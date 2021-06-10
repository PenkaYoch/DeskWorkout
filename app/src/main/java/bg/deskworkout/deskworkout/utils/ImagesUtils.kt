package bg.deskworkout.deskworkout.utils

import bg.deskworkout.deskworkout.R

object ImagesUtils {

    fun getExerciseImage(exerciseName: String) : Int {
        return when(exerciseName){
            "Frog jumps" -> R.drawable.ic_ex_flog_jumps
            "Jump squats" -> R.drawable.ic_ex_jump_squates
            "Standing long jumps" -> R.drawable.ic_ex_standing_long_jumps
            "Plank jacks" -> R.drawable.ic_ex_plank_jacks
            "Crab walks" -> R.drawable.ic_ex_crab_walks
            "Standing arm circles" -> R.drawable.ic_ex_standing_arm_cicle
            "Tuck jumps" -> R.drawable.ic_ex_tuck_jumps
            "Bench tricep dips" -> R.drawable.ic_ex_bench_tricep
            "Duck walks" -> R.drawable.ic_ex_duck_walks
            else -> R.drawable.ic_ex_duck_walks
        }
    }

}