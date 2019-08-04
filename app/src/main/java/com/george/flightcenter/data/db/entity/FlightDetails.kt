package com.george.flightcenter.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "flights")
data class FlightDetails (
    @PrimaryKey
    @SerializedName("id") val id : Int,
    @SerializedName("departure_date") val departure_date : String,
    @SerializedName("airline_code") val airline_code : String,
    @SerializedName("flight_number") val flight_number : Int,
    @SerializedName("departure_city") val departure_city : String,
    @SerializedName("departure_airport") val departure_airport : String,
    @SerializedName("arrival_city") val arrival_city : String,
    @SerializedName("arrival_airport") val arrival_airport : String,
    @SerializedName("scheduled_duration") val scheduled_duration : String,
    @SerializedName("arrival_date") val arrival_date : String
)