package no.bouvet.projectparking.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import no.bouvet.projectparking.models.Booking
import java.util.*
import kotlin.collections.HashMap

class BookingViewModel(date: Calendar) : ViewModel() {

    lateinit var bookingList : MutableLiveData<HashMap<String, Booking>>
    val db = FirebaseFirestore.getInstance()


    init {
        bookingList = MutableLiveData()
        loadBooking(date)
    }

    private fun loadBooking(date : Calendar) {

        val bookDateString = makeDateString(date)

        Log.d("BOOKING", bookDateString)

        val bookRef = db.collection("bookings")

        bookRef.whereEqualTo("date", bookDateString).addSnapshotListener(EventListener<QuerySnapshot>{
            values, e ->

            if(e != null){
                Log.e("Firebase ERROR", "Listen Failed")
                return@EventListener
            }

            val tempBookings : HashMap<String, Booking> = hashMapOf()
            for(doc in values!!){
                if(doc.get("uid") != null){
                    val pid = doc.getString("pid")!!
                    val uid = doc.getString("uid")!!
                    val date = doc.getString("date")!!

                    val cal = GregorianCalendar(date.substring(0,3).toInt(), date.substring(4,5).toInt(), date.substring(6,7).toInt())

                    tempBookings.put(pid, Booking(pid, uid, cal))
                    Log.d("BOOKING", tempBookings.toString())
                }
            }

            bookingList.postValue(tempBookings)



        })
    }


    fun getBooking(date : Calendar) : MutableLiveData<HashMap<String, Booking>> {
        if(!::bookingList.isInitialized){
            bookingList = MutableLiveData()
            loadBooking(date)
        }

        return bookingList

    }

    fun makeDateString(date : Calendar) : String{
        val month = if(date.get(Calendar.MONTH).toString().length == 2) (date.get(Calendar.MONTH) + 1).toString()
                    else "0${date.get(Calendar.MONTH) + 1}"
        val day = if(date.get(Calendar.DATE).toString().length == 2)(date.get(Calendar.DATE).toString())
                    else "0${date.get(Calendar.DATE)}"



        val bookDateString = "${date.get(Calendar.YEAR)}${month}${day}"

        return bookDateString
    }

}