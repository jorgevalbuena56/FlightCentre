package com.george.flightcenter.ui.adapter

import android.view.ViewGroup
import com.george.flightcenter.data.db.entity.FlightDetails
import com.george.flightcenter.databinding.FlightListItemBinding
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import com.george.flightcenter.R
import org.zakariya.stickyheaders.SectioningAdapter
import com.george.flightcenter.databinding.FlightItemHeaderBinding
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class FlightDetailsAdapter(
    private val flightClickListener: FlightClickListener
): SectioningAdapter() {

    private var flightSections = ArrayList<FlightDateSection>()

    interface FlightClickListener {
        fun onFlightClicked(id: Int)
    }

    override fun onCreateItemViewHolder(parent: ViewGroup, viewType: Int): FlightHolder {
        val binding : FlightListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
                        R.layout.flight_list_item, parent, false)
        return FlightHolder(binding)
    }

    override fun onCreateHeaderViewHolder(parent: ViewGroup, headerType: Int): FlightHeaderViewHolder {
        val binding : FlightItemHeaderBinding =
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.flight_item_header, parent, false)
        return FlightHeaderViewHolder(binding)
    }

    override fun onBindItemViewHolder(holder: ItemViewHolder, sectionIndex: Int, itemIndex: Int, itemType: Int) {
        val section = flightSections[sectionIndex]
        val flight = section.flights[itemIndex]
        (holder as FlightHolder).bind(flight, flightClickListener)
    }

    override fun onBindHeaderViewHolder(viewHolder: HeaderViewHolder, sectionIndex: Int, headerType: Int) {
        val section = flightSections[sectionIndex]
        (viewHolder as FlightHeaderViewHolder).bind(section.flightDateHeader)
    }

     override fun getNumberOfSections(): Int {
        return flightSections.size
    }

    override fun getNumberOfItemsInSection(sectionIndex: Int): Int {
        return flightSections[sectionIndex].flights.size
    }

    override fun doesSectionHaveHeader(sectionIndex: Int): Boolean {
        return true
    }

    override fun doesSectionHaveFooter(sectionIndex: Int): Boolean {
        return false
    }

    fun setData(flightListFetched: List<FlightDetails>) {
        flightSections.clear()

        // sort flights into buckets by departure date
        var currentDate = ""
        var currentSection : FlightDateSection? = null
        val parser =  SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val formatter = SimpleDateFormat("EEE, dd MMMM", Locale.getDefault())

        for (flight in flightListFetched) {
            val flightDate = formatter.format(parser.parse(flight.departure_date))
            if (flightDate != currentDate) {
                if (currentSection != null) {
                    flightSections.add(currentSection)
                }

                currentSection = FlightDateSection()
                currentSection.flightDateHeader = flightDate
                currentDate = flightDate
            }

            currentSection?.flights?.add(flight)
        }
        notifyAllSectionsDataSetChanged()
    }

    inner class FlightHolder(
        private val binding: FlightListItemBinding
    ) : ItemViewHolder(binding.root) {

        fun bind(flightDetails: FlightDetails, listener: FlightClickListener) {
            binding.flightDetails = flightDetails
            binding.flightClickHandler = listener
        }

    }

    inner class FlightHeaderViewHolder(
        private val binding: FlightItemHeaderBinding
    ) : SectioningAdapter.HeaderViewHolder(binding.root) {

        fun bind(flightDate: String) {
            binding.flightDepartureDateHeader.text = flightDate
        }

    }

    inner class FlightDateSection {
        internal var flightDateHeader: String = ""
        internal var flights: ArrayList<FlightDetails> = ArrayList()
    }
}