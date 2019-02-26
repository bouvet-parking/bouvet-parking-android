package no.bouvet.projectparking.repositories

import com.google.firebase.firestore.FirebaseFirestore
import no.bouvet.projectparking.models.BookingSpot


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