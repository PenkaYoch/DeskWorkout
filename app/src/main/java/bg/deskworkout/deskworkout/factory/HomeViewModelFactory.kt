package bg.deskworkout.deskworkout.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import bg.deskworkout.deskworkout.ui.home.HomeViewModel

class HomeViewModelFactory(private val context: Context) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T = HomeViewModel(context) as T
}