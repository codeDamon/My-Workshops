package com.codedamon.myworkshops.ui.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.codedamon.myworkshops.data.WorkshopDatabase
import com.codedamon.myworkshops.data.WorkshopRepository
import com.codedamon.myworkshops.model.Workshop
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    val allWorkshop: LiveData<List<Workshop>>
    private val repository: WorkshopRepository

    init {
        val dao = WorkshopDatabase.getDatabase(application).getWorkshopDao()
        repository = WorkshopRepository(dao)
        allWorkshop = repository.allWorkshop
    }

    fun addMedicine(workshop: Workshop) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(workshop)
    }
}