package no.bouvet.projectparking.repositories

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import no.bouvet.projectparking.models.Booked
import no.bouvet.projectparking.models.BookingSpot
import no.bouvet.projectparking.models.User
import org.json.JSONObject
import java.util.HashMap

fun setupFirebaseUser(db : FirebaseFirestore, userData: JSONObject){

    Log.d("WRITEUSER", "Starting setupFirebaseUser")
    val userRef = db.collection("users").document(userData.getString("id"))
    val userDoc = userRef.get().addOnSuccessListener {
        if(!it.exists()){
            val user = HashMap<String, Any>()
            user["uid"] = userData["id"]
            user["phone"] = userData["mobilePhone"]
            user["fullName"] = userData["displayName"]
            user["plateNumber"] = ""
            userRef.set(user)
        }
        else{
            if(it.getString("phone") != userData["mobilePhone"] ||
                    it.getString("fullName") != userData["displayName"]){
                val user = HashMap<String, Any>()
                user["phone"] = userData["mobilePhone"]
                user["fullName"] = userData["displayName"]
                userRef.update(user)
                Log.d("USER UPDATE", it.getString("fullName"))

            }
        }
    }


}

fun getUser(uid : String, after : (User) -> Unit){
    val db = FirebaseFirestore.getInstance()
    val ref = db.collection("users").document(uid)
    ref.get().addOnSuccessListener {
        val userData = it
        ref.collection("bookings").get().addOnSuccessListener {
            val userBookingList : MutableList<Booked> = mutableListOf()

            for (date in it) {

                userBookingList.add(Booked(date.id, date.getString("pid")!!, true, uid, userData.getString("phone")
                        , userData.getString("fullName"),
                        userData.getString("plateNumber"),
                        BookingSpot(
                                date.getString("pid")?.toInt(),
                                date.getBoolean("bookable"),
                                date.getBoolean("private"),
                                date.getBoolean("dropin"),
                                date.getBoolean("charger")


                        )))

            }
            val user = User(uid, userData.getString("phone"), userData.getString("plateNumber"), userData.getString("fullName"), userBookingList)
            after(user)
        }


    }
}