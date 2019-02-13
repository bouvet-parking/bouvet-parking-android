package no.bouvet.projectparking.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.content_reserve.view.*
import no.bouvet.projectparking.R
import no.bouvet.projectparking.parseDate
import java.util.*


class ReserveFragment : Fragment() {

    lateinit var time : Calendar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        time = Calendar.getInstance()

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var view : View = inflater.inflate(R.layout.content_reserve, container, false)
        view.date_picker_button.text = parseDate(time.get(Calendar.DATE), time.get(Calendar.MONTH), time.get(Calendar.YEAR))

        view.date_picker_button.setOnClickListener{
            ReserveDatePickerBottomSheetFragment(time).show(fragmentManager, "DATEPICKER")
        }



        return view

    }

}
