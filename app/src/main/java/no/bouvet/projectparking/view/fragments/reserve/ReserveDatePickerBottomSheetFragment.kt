package no.bouvet.projectparking.view.fragments.reserve

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton
import no.bouvet.projectparking.R
import java.util.*

class ReserveDatePickerBottomSheetFragment(cal : Calendar, fragContext: ReserveFragment) : BottomSheetDialogFragment(){

    val minDate = Calendar.getInstance()

    val parent = fragContext //TODO: NEED SOME GOOD WAY TO ALTER PARENT TIME ON SELECT

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view : View = inflater.inflate(R.layout.bottom_sheet_date_picker, container, false)

        val spinner = view.findViewById(R.id.date_picker) as DatePicker


        spinner.minDate = minDate.timeInMillis
        val maxDate = Calendar.getInstance()
        maxDate.add(Calendar.DATE, 14)
        spinner.maxDate = maxDate.timeInMillis

        val selectPicker = view.findViewById<MaterialButton>(R.id.select_picker)

        selectPicker.setOnClickListener {
            parent.updateDate(this, GregorianCalendar(spinner.year, spinner.month, spinner.dayOfMonth))
        }

        val cancelPicker = view.findViewById<MaterialButton>(R.id.cancel_picker)

        cancelPicker.setOnClickListener {
            parent.closeSheet(this)
        }

        return view
    }


}