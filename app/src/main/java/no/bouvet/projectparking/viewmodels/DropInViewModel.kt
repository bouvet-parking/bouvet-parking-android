package no.bouvet.projectparking.viewmodels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.support.v4.widget.SwipeRefreshLayout
import no.bouvet.projectparking.models.ParkingSpot
import no.bouvet.projectparking.services.parseParkingSpots
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.json.JSONArray
import java.net.URL

class DropInViewModel : ViewModel() {

    private lateinit var  parkingSpotList : MutableLiveData<List<ParkingSpot>>

    init {
        parkingSpotList = MutableLiveData()
        loadParkingSpots()
    }


    private fun loadParkingSpots(){
        doAsync {
            val result = URL("https://projectparking.azurewebsites.net/api/ParkingSpotsStatus/").readText()
            uiThread {
                val json = JSONArray(result)

                val plist  = parseParkingSpots(json)

                parkingSpotList.postValue(plist)
            }
        }
    }

    fun getParkingSpots() : LiveData<List<ParkingSpot>> {
        if (!::parkingSpotList.isInitialized) {
            parkingSpotList = MutableLiveData()
            loadParkingSpots()
        }
        return parkingSpotList
    }

    fun reloadParkingSpots(){
        // Todo: ANY checks here?
        loadParkingSpots()
    }

    fun refreshParkingSpots(view: SwipeRefreshLayout){
        //TODO: Is nested async okay?
        doAsync {
            loadParkingSpots()
            uiThread {
                view.isRefreshing = false
            }
        }
    }

}