package com.george.flightcenter.data.network

import com.george.flightcenter.data.db.entity.FlightDetails
import com.george.flightcenter.data.network.interceptor.ConnectivityInterceptor
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

const val BASE_URL = "https://firebasestorage.googleapis.com/v0/b/flight-centre-brand.appspot.com/o/"
const val TOKEN_KEY = "81d93056-9c7f-451d-94b6-3e88eb6fa9ad"
const val MEDIA_KEY = "media"

//https://firebasestorage.googleapis.com/v0/b/flight-centre-brand.appspot.com/o/developer-test-flights-list.json?alt=media&token=81d93056-9c7f-451d-94b6-3e88eb6fa9ad

interface FlightCenterAPIService {
    @GET("developer-test-flights-list.json")
    fun getFlightsAsync() : Deferred<List<FlightDetails>>

    companion object {
        operator fun invoke(
            connectivityInterceptor: ConnectivityInterceptor,
            loggingInterceptor: HttpLoggingInterceptor
        ) : FlightCenterAPIService {
            //Interceptor to add common keys to every request
            val requestInterceptor = Interceptor { chain ->
                val url = chain.request().url()
                    .newBuilder()
                    .addQueryParameter("alt", MEDIA_KEY)
                    .addQueryParameter("token", TOKEN_KEY)
                    .build()

                //build new request with the url appended the api key
                val request = chain.request()
                    .newBuilder()
                    .url(url).build()

                return@Interceptor chain.proceed(request)
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                //to avoid tight coupling, the interceptor are passed by params in he constructor
                .addInterceptor(connectivityInterceptor)
                .addInterceptor(loggingInterceptor)
                .build()

            return Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(BASE_URL)
                    .addCallAdapterFactory(CoroutineCallAdapterFactory())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(FlightCenterAPIService::class.java)
        }
    }
}