package no.bouvet.projectparking.models

import java.time.LocalDateTime

data class ParkingSpot (
        val pid : Int,
        val documentType: String,
        val parkingSensorId: String,
        val spotStatus: String,
        val distanceMeasured: Int,
        val timestamp: String
        )