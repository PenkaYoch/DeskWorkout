package bg.deskworkout.deskworkout.model

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
@SuppressLint("ParcelCreator")
data class Exercise(
    var name: String,
    var imageName: String,
    var description: String): Parcelable
