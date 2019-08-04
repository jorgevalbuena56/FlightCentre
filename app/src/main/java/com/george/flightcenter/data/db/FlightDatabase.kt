package com.george.flightcenter.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.george.flightcenter.data.db.entity.FlightDetails

@Database(
    entities = [FlightDetails::class],
    version = 1
)
/**
 * Singleton to handle the access to the database
 */
abstract class FlightDatabase : RoomDatabase() {
    abstract fun flightDao(): FlightDAO

    companion object {
        //all threads has immediately access
        @Volatile private var instance: FlightDatabase? = null
        //synchronization
        private val LOCK = Any()

        //static instance to the database
        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance= it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                FlightDatabase::class.java, "flightCenter.db")
                .build()
    }
}

