package com.codedamon.myworkshops.data

import androidx.lifecycle.LiveData
import com.codedamon.myworkshops.model.Workshop

class WorkshopRepository (private val workshopDao: WorkshopDao) {

    val allWorkshop: LiveData<List<Workshop>> = workshopDao.getAllWorkshops()

    suspend fun insert(workshop: Workshop){
        workshopDao.insert(workshop)
    }

    fun update(name: String, value: Boolean){
        workshopDao.changeAppliedStatus(name, value)
    }
}