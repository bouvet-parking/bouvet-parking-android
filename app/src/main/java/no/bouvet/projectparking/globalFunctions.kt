package no.bouvet.projectparking

import android.util.Log
import no.bouvet.projectparking.models.ParkingSpot
import org.json.JSONArray
import org.json.JSONObject

fun parseParkingSpots(json : JSONArray) : MutableList<ParkingSpot>{

    val parkingSpotList : MutableList<ParkingSpot> = mutableListOf()

    for( i in 0 until json.length() ){
        val l = (json[i] as JSONObject)
        val parkingSpot = ParkingSpot(
                l.getString("id"),
                l.getString("documentType"),
                l.getInt("parkingSpotNumber"),
                l.getString("spotStatus"),
                l.getInt("distanceMeasured")
        )
        parkingSpotList.add(parkingSpot)
    }
    Log.d("LIST", parkingSpotList.toString())

    return parkingSpotList
}