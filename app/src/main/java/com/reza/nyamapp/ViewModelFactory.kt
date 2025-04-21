package com.reza.nyamapp

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.reza.nyamapp.data.ResultRepository
import com.reza.nyamapp.ui.detail.HistoryDetailViewModel
import com.reza.nyamapp.ui.history.HistoryViewModel
import com.reza.nyamapp.ui.home.HomeViewModel
import com.reza.nyamapp.ui.result.ScanResultViewModel

class ViewModelFactory private constructor(private val resultRepository: ResultRepository) :
    ViewModelProvider.Factory {

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(
                    ResultRepository.getInstance(context)
                )
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(ScanResultViewModel::class.java) -> {
                ScanResultViewModel(resultRepository) as T
            }
            modelClass.isAssignableFrom(HistoryViewModel::class.java) -> {
                HistoryViewModel(resultRepository) as T
            }
            modelClass.isAssignableFrom(HistoryDetailViewModel::class.java) -> {
                HistoryDetailViewModel(resultRepository) as T
            }
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(resultRepository) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
}
