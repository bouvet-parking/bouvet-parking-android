package no.bouvet.projectparking.models

import java.util.*

data class BookableWithStatus (
        val pid: Int,
        val booked: Boolean,
        val uid: String?,
        val uNumber : String?,
        val uCarNumbers: List<String>,
        val date: Calendar
)