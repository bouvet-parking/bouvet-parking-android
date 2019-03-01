package no.bouvet.projectparking.view.fragments.reserve

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.content_reserve.*
import kotlinx.android.synthetic.main.content_reserve.view.*
import no.bouvet.projectparking.R
import no.bouvet.projectparking.models.BookingSpot
import no.bouvet.projectparking.parseDate
import no.bouvet.projectparking.parseDateString
import no.bouvet.projectparking.viewmodels.ReserveViewModel
import java.util.*


class ReserveFragment : Fragment() {

    lateinit var time : Calendar

    lateinit var bookingSpotList : List<BookingSpot>

    //Grid List view varialbes for available spots
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    //ViewModel for getting all spots
    private val reserveViewModel : ReserveViewModel = ReserveViewModel()

    //Grid List view varialbes for booked spots
    private lateinit var recyclerViewBooked: RecyclerView
    private lateinit var viewAdapterBooked: RecyclerView.Adapter<*>
    private lateinit var viewManagerBooked: RecyclerView.LayoutManager




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        time = Calendar.getInstance()

        //setup Grid Views

        viewManager = GridLayoutManager(context, 3)

        reserveViewModel.getUnBookedSpots().observe(this, androidx.lifecycle.Observer{
            listBookingSpots -> bookingSpotList = listBookingSpots
            Log.d("LIVEDATA" , listBookingSpots.toString())

            viewAdapter = ReserveListAdapter(bookingSpotList, context, fragmentManager)

            recyclerView = reserve_list.apply {

                setHasFixedSize(false)

                layoutManager = viewManager

                // specify an viewAdapter
                adapter = viewAdapter

            }

        })
        viewManagerBooked = GridLayoutManager(context, 3)
        reserveViewModel.getBookedSpots().observe(this, androidx.lifecycle.Observer {
            listBooked ->

                viewAdapterBooked = ReserveBookedListAdapter(listBooked, context, fragmentManager)
                recyclerViewBooked = booked_list.apply {

                    setHasFixedSize(false)
                    layoutManager = viewManagerBooked
                    adapter = viewAdapterBooked
                }
        })




    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var view : View = inflater.inflate(R.layout.content_reserve, container, false)

        //Setup datepicker with todays date

        view.date_picker_button.text = parseDate(time.get(Calendar.DATE), time.get(Calendar.MONTH), time.get(Calendar.YEAR))

        view.date_picker_button.setOnClickListener{
            ReserveDatePickerBottomSheetFragment(time, this).show(fragmentManager, "DATEPICKER")
        }

        //Move icon to start of text dynamically

        view.date_picker_button.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                view.date_picker_button.iconPadding = view.date_picker_button.text.length * 8
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
        })



        return view

    }

    fun updateDate(sheet : ReserveDatePickerBottomSheetFragment, newDate : Calendar){

        //Function is called from DatePicker Bottom Sheet

        time = newDate
        view?.let {
            it.date_picker_button.text = parseDate(time.get(Calendar.DATE), time.get(Calendar.MONTH), time.get(Calendar.YEAR))

            if(sheet.isVisible) sheet.dismiss()
            val buttonView = it.findViewById<MaterialButton>(R.id.date_picker_button)
            buttonView.invalidate()
            buttonView.refreshDrawableState()
            buttonView.iconGravity = MaterialButton.ICON_GRAVITY_TEXT_START
        }
        reserveViewModel.loadData(parseDateString(time))


    }

    fun closeSheet(sheet : ReserveDatePickerBottomSheetFragment){
        if(sheet.isVisible) sheet.dismiss()
    }

}
