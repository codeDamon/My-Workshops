package com.codedamon.myworkshops.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.codedamon.myworkshops.model.Workshop

@Database(entities = [Workshop::class], version = 1, exportSchema = false)
abstract class WorkshopDatabase : RoomDatabase() {

    abstract fun getWorkshopDao(): WorkshopDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: WorkshopDatabase? = null

        fun getDatabase(context: Context): WorkshopDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WorkshopDatabase::class.java,
                    "workshop_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}