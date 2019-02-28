package no.bouvet.projectparking.view.fragments.dropin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_sheet_dropin.view.*
import no.bouvet.projectparking.R
import no.bouvet.projectparking.models.ParkingSpot
import no.bouvet.projectparking.parseDate
import java.util.*

class DropInBottomSheetFragment(val data : ParkingSpot) : BottomSheetDialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view : View = inflater.inflate(R.layout.bottom_sheet_dropin, container, false)

        view.bs_spotNr.text = "Plass #" + data.pid.toString()
        view.bs_status.text = (if(data.spotStatus == "available") ("Ledig") else ("Error"))
        view.bs_dist.text = "Målt distanse: "+data.distanceMeasured.toString()

        val timestamp = data.timestamp.split("T")


        view.bs_timestamp.text = "Sist oppdatert: " + timestamp[0] + " kl. " + timestamp[1].substring(0, 8)

        return view
    }
}