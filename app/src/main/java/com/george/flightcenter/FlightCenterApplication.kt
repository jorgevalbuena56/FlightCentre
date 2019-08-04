package com.george.flightcenter

import android.app.Application
import com.george.flightcenter.data.db.FlightDatabase
import com.george.flightcenter.data.network.FlightCenterAPIService
import com.george.flightcenter.data.network.datasource.FlightDetailsDataSource
import com.george.flightcenter.data.network.datasource.FlightDetailsDataSourceImpl
import com.george.flightcenter.data.network.interceptor.ConnectivityInterceptor
import com.george.flightcenter.data.network.interceptor.ConnectivityInterceptorImpl
import com.george.flightcenter.data.repository.FlightCenterRepository
import com.george.flightcenter.data.repository.FlightCenterRepositoryImpl
import com.george.flightcenter.ui.viewModel.FlightDetailsViewModelFactory
import com.george.flightcenter.ui.viewModel.FlightMainListViewModelFactory
import okhttp3.logging.HttpLoggingInterceptor
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

/**
 * All the dependency injection happens here when the application starts
 */
class FlightCenterApplication : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        //provides with instances of context, services, android related, etc
        import(androidXModule(this@FlightCenterApplication))

        bind() from singleton { FlightDatabase(instance()) }
        bind() from singleton { instance<FlightDatabase>().flightDao() }

        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind<HttpLoggingInterceptor>() with singleton {
           HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        }
        //this bind will use the instances previously created for the interceptors
        bind() from singleton { FlightCenterAPIService(instance(), instance()) }
        bind<FlightDetailsDataSource>() with singleton { FlightDetailsDataSourceImpl(instance()) }
        bind<FlightCenterRepository>() with singleton { FlightCenterRepositoryImpl(instance(), instance()) }

        //bind to the view model factory, not a singleton
        bind() from provider { FlightMainListViewModelFactory(instance()) }
        bind() from provider { FlightDetailsViewModelFactory(instance()) }
    }
}