package no.bouvet.projectparking.models


data class Booked (
        val date: String,
        val pid: String?,
        val booked: Boolean,
        val uid: String?,
        val userPhoneNumber : String?,
        val userName : String?,
        val userPlateNumber : String?,
        val bookingSpot : BookingSpot
)