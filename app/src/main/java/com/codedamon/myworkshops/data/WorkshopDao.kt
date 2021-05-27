package com.codedamon.myworkshops.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.codedamon.myworkshops.model.Workshop

@Dao
interface WorkshopDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(workshop: Workshop)

    @Query("Select * from workshop_table order by name ASC")
    fun getAllWorkshops(): LiveData<List<Workshop>>

    @Query("UPDATE workshop_table SET applied =:value WHERE name = :name")
    fun changeAppliedStatus(name: String, value: Boolean)

}