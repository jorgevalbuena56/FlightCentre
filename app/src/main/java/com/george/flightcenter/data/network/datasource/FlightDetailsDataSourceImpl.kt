package com.george.flightcenter.data.network.datasource

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.george.flightcenter.data.db.entity.FlightDetails
import com.george.flightcenter.data.network.FlightCenterAPIService
import com.george.flightcenter.internal.NoConnectivityException
import java.lang.Exception

/**
 * Data source implementation used to obtain the information from the server
 */
class FlightDetailsDataSourceImpl(
    private val flightDetailsAPIService: FlightCenterAPIService
) : FlightDetailsDataSource {
    private val flightList = MutableLiveData<List<FlightDetails>>()

    override val downloadedFlights: LiveData<List<FlightDetails>>
        //will automatically cast the mutable live data to live data, to serve the information to external entities
        //asking for the flight list and they will not be able to change the information, only this class has control
        //of the changes in the data
        get() = flightList

    override suspend fun fetchFlights() {
        try {
            val fetchedFlightList =
                flightDetailsAPIService.getFlightsAsync().await()
            flightList.postValue(fetchedFlightList)
        } catch (e: NoConnectivityException) {
            Log.e("Connectivity", "No Internet Exception", e)
        } catch (ex: Exception) {
            Log.e("GeneralException", "Exception: " + ex.message, ex)
        }
    }
}