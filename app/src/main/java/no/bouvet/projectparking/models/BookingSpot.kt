package no.bouvet.projectparking.models

data class BookingSpot(
        val pid : Int,
        val bookable : Boolean,
        val private : Boolean,
        val dropin : Boolean,
        val charger : Boolean
)