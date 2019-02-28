package no.bouvet.projectparking.view.fragments.reserve

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_sheet_booked.view.*
import no.bouvet.projectparking.R
import no.bouvet.projectparking.models.Booked

class ReserveBookedBottomSheet(val data : Booked) : BottomSheetDialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view : View = inflater.inflate(R.layout.bottom_sheet_booked, container, false)

        view.bsb_pid.text = "Plass #${data.pid}"
        view.bsb_bookedBy.text = "Reservert av ${data.userName}"
        view.bsb_plateNumber.text = "Registreringsnummer: ${data.userPlateNumber}"
        view.bsb_phone.text = data.userPhoneNumber

        view.bsb_phone.setOnClickListener {

            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:${data.userPhoneNumber}")
            startActivity(intent)
        }


        return view
    }
}