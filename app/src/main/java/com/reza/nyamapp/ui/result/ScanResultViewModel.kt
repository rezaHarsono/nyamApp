package com.reza.nyamapp.ui.result

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.reza.nyamapp.data.ResultRepository
import com.reza.nyamapp.data.ScanResult
import com.reza.nyamapp.utils.Event

class ScanResultViewModel(private val repository: ResultRepository) : ViewModel() {

    private val _totalCalories = MutableLiveData<Double>()
    val totalCalories: LiveData<Double> = _totalCalories

    private val _saved: MutableLiveData<Event<Boolean>> = MutableLiveData(Event(false))
    val saved: LiveData<Event<Boolean>>
        get() = _saved

    fun insertResult(result: ScanResult) {
        repository.insertResult(result)
        _saved.value = Event(true)
    }

    fun loadCaloriesToday(date: String) {
        repository.getCaloriesToday(date).observeForever { scanResults ->
            val total = scanResults
            _totalCalories.value = total.toDouble()
        }
    }
}