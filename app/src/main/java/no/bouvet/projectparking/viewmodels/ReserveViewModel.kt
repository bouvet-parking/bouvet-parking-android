package no.bouvet.projectparking.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import no.bouvet.projectparking.models.Booked
import no.bouvet.projectparking.models.BookingSpot
import no.bouvet.projectparking.parseDateString
import no.bouvet.projectparking.repositories.loadBookingList
import java.util.*

class ReserveViewModel : ViewModel() {
    private lateinit var bookingSpots : MutableLiveData<List<BookingSpot>>
    private lateinit var  bookedSpots : MutableLiveData<List<Booked>>

    val bookedList : MutableList<Booked> = mutableListOf()
    val unbookedList : MutableList<BookingSpot> = mutableListOf()


    init{
        bookingSpots = MutableLiveData()
        bookedSpots = MutableLiveData()
        val date = Calendar.getInstance()
        loadData(parseDateString(date))
    }

    fun loadData(date : String){

        loadBookingList(date, bookedList, unbookedList) {
                bookingSpots.postValue(unbookedList)
                bookedSpots.postValue(bookedList)
            }



    }



    fun getBookedSpots() : MutableLiveData<List<Booked>>{
        return bookedSpots
    }

    fun getUnBookedSpots() : MutableLiveData<List<BookingSpot>>{
        Log.d("RESERVEVIEWMODEL", unbookedList.toString())
        return bookingSpots
    }



}