package com.george.flightcenter.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.george.flightcenter.data.db.FlightDAO

/**
 * Factory to avoid creating the view model in the views
 *
 * @author Jorge Valbuena
 */

@Suppress("UNCHECKED_CAST")
class FlightDetailsViewModelFactory(
    private val flightCenterDAO: FlightDAO
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FlightDetailsViewModel(flightCenterDAO) as T
    }
}