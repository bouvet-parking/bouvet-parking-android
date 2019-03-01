package no.bouvet.projectparking.models

data class User(
        val uid : String?,
        val phone : String?,
        val plateNumber : String?,
        val fullName : String?,
        val bookings : List<Booked>
)