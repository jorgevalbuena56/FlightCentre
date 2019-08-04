package com.george.flightcenter.ui

import android.graphics.drawable.ClipDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.george.flightcenter.R
import com.george.flightcenter.base.ScopedActivity
import com.george.flightcenter.databinding.FlightDetailsBinding
import com.george.flightcenter.ui.viewModel.FlightDetailsViewModel
import com.george.flightcenter.ui.viewModel.FlightDetailsViewModelFactory
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance
import java.text.SimpleDateFormat
import java.util.*

class FlightDetailsActivity : ScopedActivity(), KodeinAware {
    override val kodein: Kodein by closestKodein()
    private val viewModelFactory: FlightDetailsViewModelFactory by instance()

    private val dateParser =  SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
    private val dateFormatter = SimpleDateFormat("EEE, d MMM HH:mm aaa", Locale.getDefault())

    private lateinit var viewModel: FlightDetailsViewModel
    private lateinit var mBinding: FlightDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = DataBindingUtil.setContentView(this, R.layout.flight_details)

        setSupportActionBar(mBinding.root.findViewById(R.id.toolbar_top))

        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(FlightDetailsViewModel::class.java)

        val rightCircle  = mBinding.ticketOvalRight.background as ClipDrawable
        rightCircle.level = rightCircle.level + 7000

        val leftCircle  = mBinding.ticketOvalLeft.background as ClipDrawable
        leftCircle.level = leftCircle.level + 7000

        bindUI()
    }

    private fun bindUI() = launch {
        val flightId = intent?.extras?.getInt("id") ?: 0
        val flightDetails = viewModel.getFlightDetails(flightId).await()

        flightDetails.observe(this@FlightDetailsActivity, Observer {
            if (it == null) return@Observer

            mBinding.departDateText.text =
                dateFormatter.format(dateParser.parse(it.departure_date))

            mBinding.arriveDateText.text =
                dateFormatter.format(dateParser.parse(it.arrival_date))

            mBinding.flightDetails = it
        })
    }
}