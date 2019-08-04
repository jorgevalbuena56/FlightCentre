package com.george.flightcenter.ui

import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter(value = ["hour"])
fun TextView.formatHours(hour: String) {
    val parser =  SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
    val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())
    text = formatter.format(parser.parse(hour))

}