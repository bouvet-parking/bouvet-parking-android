package no.bouvet.projectparking.models

import java.time.LocalDateTime

data class ParkingSpot (
        val pid : String,
        val documentType: String,
        val parkingSensorId: Int,
        val spotStatus: String,
        val distanceMeasured: Int,
        val timestamp: String
        )