package com.george.flightcenter.data.network.datasource

import androidx.lifecycle.LiveData
import com.george.flightcenter.data.db.entity.FlightDetails

interface FlightDetailsDataSource {
    val downloadedFlights: LiveData<List<FlightDetails>>

    suspend fun fetchFlights()
}