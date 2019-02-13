package no.bouvet.projectparking

import android.util.Log
import no.bouvet.projectparking.models.ParkingSpot
import org.json.JSONArray
import org.json.JSONObject
import java.util.*

fun parseParkingSpots(json : JSONArray) : MutableList<ParkingSpot>{

    val parkingSpotList : MutableList<ParkingSpot> = mutableListOf()

    for( i in 0 until json.length() ){
        val l = (json[i] as JSONObject)
        val parkingSpot = ParkingSpot(
                l.getString("id"),
                l.getString("documentType"),
                l.getInt("parkingSpotNumber"),
                l.getString("spotStatus"),
                l.getInt("distanceMeasured"),
                l.getString("timeOfMeasurement")
        )
        parkingSpotList.add(parkingSpot)
    }
    Log.d("LIST", parkingSpotList.toString())

    return parkingSpotList
}

fun parseDate(day: Int, month: Int, year: Int) : String{

    val today = Calendar.getInstance()

    if(today.get(Calendar.DATE) == day && today.get(Calendar.MONTH) == month && today.get(Calendar.YEAR) == year){
        return "I dag"
    }
    today.add(Calendar.DATE, 1)
    if(today.get(Calendar.DATE) == day && today.get(Calendar.MONTH) == month && today.get(Calendar.YEAR) == year){
        return "I morgen"
    }

    when(month){
        1 -> return "$day. Januar"
        2 -> return "$day. Februar"
        3 -> return "$day. Mars"
        4 -> return "$day. April"
        5 -> return "$day. Mai"
        6 -> return "$day. Juni"
        7 -> return "$day. Juli"
        8 -> return "$day. August"
        9 -> return "$day. September"
        10 -> return "$day. Oktober"
        11 -> return "$day. November"
        12 -> return "$day. Desember"
        else -> return "Date error"
    }

}