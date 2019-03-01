package no.bouvet.projectparking.view.fragments.reserve

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.bottom_sheet_reserve.view.*
import no.bouvet.projectparking.R
import no.bouvet.projectparking.models.BookingSpot

class ReserveBottomSheet(val spot : BookingSpot) : BottomSheetDialogFragment(){

    //Sets up the bottom sheet for available spots

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.bottom_sheet_reserve, container)

        view.bsr_pid.text = "Plass #${spot.pid}"
        view.bsr_charger.text = "Lader: " + if(spot.charger != null && spot.charger){"Ja"}else{"Nei"}

        view.bsr_reserve_button.setOnClickListener {

            val rootView = activity?.window?.decorView?.findViewById<View>(R.id.cal_layout)

            var mView : View = view

            rootView?.let {
                mView = rootView

            }
            //Open dialog on hit confirm
            val ab = AlertDialog.Builder(view.context, R.style.materialDialog)
            ab.setMessage(R.string.bekreft_bread)
            ab.setTitle(R.string.bekreft_res)
            ab.setPositiveButton(R.string.bekreft){
                dialog, id ->
                dialog.dismiss()
                this.dismiss()
                Snackbar.make(mView, "Reservasjon vellykket", Snackbar.LENGTH_LONG).show()
                //TODO: BOOK ATOMIC IN DATABASE (Through cloud functions?)

            }
            ab.setNegativeButton(R.string.avbryt){
                dialog, id ->
                    dialog.cancel()
                    this.dismiss()
                    Snackbar.make(mView, "Reservasjon avbrutt", Snackbar.LENGTH_LONG).show()
            }

            ab.show()
        }


        return view

    }

}