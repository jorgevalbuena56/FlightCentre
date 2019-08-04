package com.george.flightcenter.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.george.flightcenter.data.repository.FlightCenterRepository

/**
 * Factory to avoid creating the view model in the views
 *
 * @author Jorge Valbuena
 */

@Suppress("UNCHECKED_CAST")
class FlightMainListViewModelFactory(
    private val flightCenterRepository: FlightCenterRepository
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FlightMainListViewModel(flightCenterRepository) as T
    }
}