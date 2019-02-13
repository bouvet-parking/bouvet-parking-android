package no.bouvet.projectparking.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import no.bouvet.projectparking.R
import java.util.*

class ReserveDatePickerBottomSheetFragment(cal : Calendar) : BottomSheetDialogFragment(){

    val minDate = Calendar.getInstance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view : View = inflater.inflate(R.layout.bottom_sheet_date_picker, container, false)

        val spinner = view.findViewById(R.id.date_picker) as DatePicker


        spinner.minDate = minDate.timeInMillis
        val maxDate = Calendar.getInstance()
        maxDate.add(Calendar.DATE, 14)
        spinner.maxDate = maxDate.timeInMillis




        return view
    }


}