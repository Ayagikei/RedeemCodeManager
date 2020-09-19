package net.sarasarasa.redeemcode

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import net.sarasarasa.redeemcode.db.MainDataRepository

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
class MainViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(
                mainDataRepository = MainDataRepository()
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}