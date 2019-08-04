package com.george.flightcenter.data.repository

import androidx.lifecycle.LiveData
import com.george.flightcenter.data.db.FlightDAO
import com.george.flightcenter.data.db.entity.FlightDetails
import com.george.flightcenter.data.network.datasource.FlightDetailsDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FlightCenterRepositoryImpl(
    private val flightDetailsDAO: FlightDAO,
    private val flightDetailsDataSource: FlightDetailsDataSource
) : FlightCenterRepository {

    init {
        //we use forever because there's no need to worry about Life cycle events here
        flightDetailsDataSource.downloadedFlights.observeForever { flightDetails ->
            persistFlightDetails(flightDetails)
        }
    }

    override suspend fun getFlights(): LiveData<List<FlightDetails>> {
        //returns a value, launch does not return a value
        return withContext(Dispatchers.IO) {
            fetchFlights()
            return@withContext flightDetailsDAO.getFlights()
        }
    }

    private fun persistFlightDetails(response: List<FlightDetails>) {
        GlobalScope.launch(Dispatchers.IO) {
            for (flightDetails in response) {
                flightDetailsDAO.insertFlightDetails(flightDetails)
            }
        }
    }

    private suspend fun fetchFlights() {
        //this is going to fetched the data from the endpoint and automatically triggers the init block observer to
        //store the data into the DB.
        flightDetailsDataSource.fetchFlights()
    }
}