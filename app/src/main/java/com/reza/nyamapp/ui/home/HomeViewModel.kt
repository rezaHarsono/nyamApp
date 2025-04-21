package com.reza.nyamapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.reza.nyamapp.data.ResultRepository

class HomeViewModel(private val repository: ResultRepository) : ViewModel() {

    fun getCaloriesToday(date: String): LiveData<Int> {
        return repository.getCaloriesToday(date)
    }
}