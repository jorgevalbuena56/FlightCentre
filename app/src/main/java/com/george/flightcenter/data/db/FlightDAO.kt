package com.george.flightcenter.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.george.flightcenter.data.db.entity.FlightDetails

@Dao
interface FlightDAO {
    //always replace the flight information if the ids are equal
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFlightDetails(flightDetails: FlightDetails)

    @Query(value = "select * from flights where id = :flightId")
    fun getFlightDetails(flightId: Int): LiveData<FlightDetails>

    @Query(value = "select * from flights")
    fun getFlights(): LiveData<List<FlightDetails>>
}