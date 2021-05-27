package com.codedamon.myworkshops.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workshop_table")
class Workshop(val name: String,
               val img: String,
               val date_time: String,
               val by: String,
               val language: String,
               val applied: Boolean,
               val attendees: Int,
               val place: String
               ){
    @PrimaryKey(autoGenerate = true)var id:Int=0
}