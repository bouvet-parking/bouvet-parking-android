package no.bouvet.projectparking.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import no.bouvet.projectparking.models.ParkingSpot
import no.bouvet.projectparking.parseParkingSpots
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
        //TODO: This method should probably be moved to Network
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
        loadParkingSpots()
    }

    fun refreshParkingSpots(view: SwipeRefreshLayout){
        doAsync {
            loadParkingSpots()
            uiThread {
                view.isRefreshing = false
            }
        }
    }

}