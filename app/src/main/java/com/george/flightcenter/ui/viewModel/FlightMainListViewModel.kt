package com.george.flightcenter.ui.viewModel

import androidx.lifecycle.ViewModel
import com.george.flightcenter.data.repository.FlightCenterRepository
import com.george.flightcenter.internal.lazyDeferred

/**
 * View model will expose data to the view and it is use as a middle man with the repository
 */
class FlightMainListViewModel(
    private val flightCenterRepository: FlightCenterRepository
): ViewModel() {

    val flightList by lazyDeferred {
        flightCenterRepository.getFlights()
    }
}