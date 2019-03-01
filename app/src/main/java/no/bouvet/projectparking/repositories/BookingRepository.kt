package no.bouvet.projectparking.repositories

import android.util.Log
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import no.bouvet.projectparking.models.Booked
import no.bouvet.projectparking.models.BookingSpot
import no.bouvet.projectparking.models.User


fun loadallSpaces( funct : (MutableList<BookingSpot>) -> Unit){

    val db = FirebaseFirestore.getInstance()

    val spotsRef = db.collection("spaces")

    spotsRef.get().addOnSuccessListener {

        values ->

        val tempSpots: MutableList<BookingSpot> = mutableListOf()
        for (doc in values!!) {
            if (doc.getLong("pid") != null) {

                val pid = doc.getLong("pid")?.toInt()!!
                val bookable = doc.getBoolean("bookable")!!
                val charger = doc.getBoolean("charger")!!
                val dropin = doc.getBoolean("dropin")!!
                val private = doc.getBoolean("private")!!

                val tempSpot = BookingSpot(pid, bookable, private, dropin, charger)

                tempSpots.add(tempSpot)
            }
        }

        funct(tempSpots)

    }

}

/*

data class Booked (
        val pid: Int,
        val booked: Boolean,
        val uid: String?,
        val uNumber : String?,
        val uCarNumbers: List<String>,
        val date: Calendar
)
 */



//Function takes in the date to load, and the lists in which the user wants to store the loaded data
fun loadBookingList(date: String, bookedList : MutableList<Booked>, unBookedList : MutableList<BookingSpot>,
                    after : () -> Unit){
    val db = FirebaseFirestore.getInstance()

    loadallSpaces { spaces ->
        val dbRef = db.collection("bookings").document(date).collection("spaces")
        dbRef.addSnapshotListener(EventListener<QuerySnapshot>{
            snapshot, e ->
            bookedList.clear()
            unBookedList.clear()
            unBookedList.addAll(spaces)
            for(document in snapshot!!){

                val bookingSpot = spaces.filter{ it.pid.toString() == document.id }[0]
                unBookedList.remove(bookingSpot)

                bookedList.add(Booked(date, document.id,true, document.getString("uid"),
                        document.getString("phone"), document.getString("fullName"),document.getString("plateNumber"), bookingSpot))

            }
            after()
            Log.d("BOOKEDLIST", bookedList.toString())
            Log.d("UNBOOKEDLIST", unBookedList.toString())

    })

    }




}

