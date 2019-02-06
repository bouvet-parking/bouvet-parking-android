package no.bouvet.projectparking.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import no.bouvet.projectparking.R
import no.bouvet.projectparking.models.ParkingSpot
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.json.JSONArray
import org.json.JSONObject
import java.net.URL

class DropInFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var view : View = inflater.inflate(R.layout.content_dropin, container, false)


        //TODO: Maybe change to Gson?

        doAsync {
            val result = URL("https://projectparking.azurewebsites.net/api/ParkingSpotsStatus/").readText()
            uiThread {
                Log.d("Request", result)
                val json = JSONArray(result)
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
                    Log.d("JSPON", parkingSpot.toString())
                }
                Log.d("LIST", parkingSpotList.toString())

            }
        }
        Log.d("HELLO", "TESTOIM;GGMG")
        return view
    }


    fun handleResult(result : String){

    }
}