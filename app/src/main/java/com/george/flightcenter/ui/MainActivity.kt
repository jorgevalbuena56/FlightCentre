package com.george.flightcenter.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.george.flightcenter.R
import com.george.flightcenter.base.ScopedActivity
import com.george.flightcenter.databinding.ActivityMainBinding
import com.george.flightcenter.ui.adapter.FlightDetailsAdapter
import com.george.flightcenter.ui.viewModel.FlightMainListViewModel
import com.george.flightcenter.ui.viewModel.FlightMainListViewModelFactory
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance


class MainActivity : ScopedActivity(), KodeinAware {

    override val kodein: Kodein by closestKodein()
    private val viewModelFactory: FlightMainListViewModelFactory by instance()

    private lateinit var viewModel: FlightMainListViewModel
    private lateinit var mBinding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setSupportActionBar(mBinding.root.findViewById(R.id.toolbar_top))


        mBinding.flightProgress.indeterminateDrawable.setColorFilter(ContextCompat.getColor(this, R.color.colorPrimary),
            android.graphics.PorterDuff.Mode.MULTIPLY)
        mBinding.flightProgress.visibility = View.VISIBLE
        mBinding.flightsNotFound.text = getString(R.string.label_loading_trips)
        mBinding.flightsNotFound.visibility = View.VISIBLE

        mBinding.flightsAdapter = FlightDetailsAdapter(object : FlightDetailsAdapter.FlightClickListener {
            override fun onFlightClicked(id: Int) {
                val intent = Intent(this@MainActivity, FlightDetailsActivity::class.java)
                intent.putExtra("id", id)
                startActivity(intent)
            }
        })

        //use the factory in case a new view model needs to be instantiated
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(FlightMainListViewModel::class.java)

        bindUI()
    }

    private fun bindUI() = launch {
        val flightMainList = viewModel.flightList.await()
        flightMainList.observe(this@MainActivity, Observer {
            if (it == null) return@Observer

            if (it.isEmpty()) {
                mBinding.flightsNotFound.text = getString(R.string.label_flights_not_found)
            } else {
                mBinding.flightsNotFound.visibility = View.GONE
                mBinding.flightsAdapter?.setData(it)
            }
            mBinding.flightProgress.visibility = View.GONE
        })
    }
}
