package com.george.flightcenter.data.repository

import androidx.lifecycle.LiveData
import com.george.flightcenter.data.db.entity.FlightDetails

interface FlightCenterRepository {
    suspend fun getFlights()
            : LiveData<List<FlightDetails>>
}