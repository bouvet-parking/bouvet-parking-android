package no.bouvet.projectparking.view.fragments.reserve

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.content_reserve.*
import kotlinx.android.synthetic.main.content_reserve.view.*
import no.bouvet.projectparking.R
import no.bouvet.projectparking.models.BookingSpot
import no.bouvet.projectparking.parseDate
import no.bouvet.projectparking.viewmodels.BookingViewModel
import no.bouvet.projectparking.viewmodels.ReserveViewModel
import java.util.*


class ReserveFragment : Fragment() {

    lateinit var time : Calendar

    lateinit var bookingSpotList : List<BookingSpot>

    //List View Variables
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        time = Calendar.getInstance()

        viewManager = GridLayoutManager(context, 4)

        val reserveSpots = ReserveViewModel()
        reserveSpots.getBookingSpots().observe(this, androidx.lifecycle.Observer{
            listBookingSpots -> bookingSpotList = listBookingSpots
            Log.d("LIVEDATA" , listBookingSpots.toString())

            viewAdapter = ReserveListAdapter(bookingSpotList, context, fragmentManager)

            recyclerView = reserve_list.apply {

                setHasFixedSize(false)

                layoutManager = viewManager

                // specify an viewAdapter (see also next example)
                adapter = viewAdapter

            }

        })

        val bookings = BookingViewModel(time)
        val bookmap = bookings.getBooking(time).observe(this, androidx.lifecycle.Observer {
            bookmap ->

        })



    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var view : View = inflater.inflate(R.layout.content_reserve, container, false)
        view.date_picker_button.text = parseDate(time.get(Calendar.DATE), time.get(Calendar.MONTH), time.get(Calendar.YEAR))

        view.date_picker_button.setOnClickListener{
            ReserveDatePickerBottomSheetFragment(time, this).show(fragmentManager, "DATEPICKER")
        }

        //TODO: REMOVE THIS BIT - ONLY FOR TESTING




        return view

    }

    fun updateDate(sheet : ReserveDatePickerBottomSheetFragment, newDate : Calendar){

        time = newDate
        view?.let {
            it.date_picker_button.text = parseDate(time.get(Calendar.DATE), time.get(Calendar.MONTH), time.get(Calendar.YEAR))

            if(sheet.isVisible) sheet.dismiss()
        }

    }

    fun closeSheet(sheet : ReserveDatePickerBottomSheetFragment){
        if(sheet.isVisible) sheet.dismiss()
    }

}
