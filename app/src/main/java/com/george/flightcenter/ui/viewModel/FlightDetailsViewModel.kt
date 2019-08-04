package com.george.flightcenter.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.george.flightcenter.data.db.FlightDAO
import com.george.flightcenter.data.db.entity.FlightDetails
import com.george.flightcenter.internal.lazyDeferred
import kotlinx.coroutines.Deferred

/**
 * View model will expose data to the view and it is use as a middle man with the database
 */
class FlightDetailsViewModel(
    private val flightDAO: FlightDAO
): ViewModel() {

    fun getFlightDetails(flightId: Int): Deferred<LiveData<FlightDetails>> {
        val flightDetails by lazyDeferred {
            flightDAO.getFlightDetails(flightId)
        }

        return flightDetails
    }
}