package com.codedamon.myworkshops.ui.dashboard

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.codedamon.myworkshops.data.WorkshopDatabase
import com.codedamon.myworkshops.data.WorkshopRepository
import com.codedamon.myworkshops.model.Workshop

class DashboardViewModel(application: Application) : AndroidViewModel(application) {

    val allWorkshop: LiveData<List<Workshop>>
    private val repository: WorkshopRepository

    init {
        val dao = WorkshopDatabase.getDatabase(application).getWorkshopDao()
        repository = WorkshopRepository(dao)
        allWorkshop = repository.allWorkshop
    }
}