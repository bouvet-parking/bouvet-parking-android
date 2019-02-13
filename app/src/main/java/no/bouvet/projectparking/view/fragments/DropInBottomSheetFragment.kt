package no.bouvet.projectparking.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_sheet_dropin.view.*
import no.bouvet.projectparking.R
import no.bouvet.projectparking.models.ParkingSpot

class DropInBottomSheetFragment(val data : ParkingSpot) : BottomSheetDialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view : View = inflater.inflate(R.layout.bottom_sheet_dropin, container, false)

        view.bs_spotNr.text = "Plass #" + data.parkingSensorId.toString()
        view.bs_status.text = "Status: "+ (if(data.spotStatus == "available") ("Ledig") else ("Error"))
        view.bs_dist.text = "Målt distanse: "+data.distanceMeasured.toString()
        view.bs_timestamp.text = "Sist oppdatert: "+data.timestamp

        return view
    }
}