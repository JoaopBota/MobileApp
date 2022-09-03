package com.example.mobileproject5

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MarkerViewModel(application: Application): AndroidViewModel(application) {

    private val readAllData: LiveData<List<Marker>>
    private val repository: MarkerRepository

    init {
        val markerDao = MarkerDatabase.getDatabase(application).markerDao()
        repository = MarkerRepository(markerDao)
        readAllData = repository.readAllData
    }

    fun addMarker(marker: Marker){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addMarker(marker)
        }
    }

}