package no.bouvet.projectparking.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import no.bouvet.projectparking.models.BookingSpot

class ReserveViewModel : ViewModel() {

    private lateinit var bookingSpots : MutableLiveData<List<BookingSpot>>
    val db = FirebaseFirestore.getInstance()

    init{
        bookingSpots = MutableLiveData()
        loadBookingSpots()
    }

    fun loadBookingSpots(){

        val spotsRef = db.collection("spaces")

        spotsRef.addSnapshotListener(EventListener<QuerySnapshot>{

            values, e ->

            if(e != null){
                Log.e("Firebase ERROR", "Listen Failed")
                return@EventListener
            }

            val tempSpots : MutableList<BookingSpot> = mutableListOf()
            for(doc in values!!) {
                if(doc.getLong("pid") != null){

                    val pid = doc.getLong("pid")?.toInt()!!
                    val bookable = doc.getBoolean("bookable")!!
                    val charger = doc.getBoolean("charger")!!
                    val dropin = doc.getBoolean("dropin")!!
                    val private = doc.getBoolean("private")!!

                    val tempSpot = BookingSpot(pid, bookable, private, dropin, charger)

                    tempSpots.add(tempSpot)
                }
            }

            bookingSpots.postValue(tempSpots)

        })

    }

    fun getBookingSpots() : MutableLiveData<List<BookingSpot>>{
        if(!::bookingSpots.isInitialized){
            bookingSpots = MutableLiveData()
            loadBookingSpots()
        }
        return bookingSpots
    }



}