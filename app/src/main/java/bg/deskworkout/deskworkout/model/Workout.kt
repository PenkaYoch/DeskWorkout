package bg.deskworkout.deskworkout.model

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
@SuppressLint("ParcelCreator")
data class Workout(var name: String,
                    var imageName: String,
                    var description: String,
                    var type: String,
                    var doTimes: Int): Parcelable
