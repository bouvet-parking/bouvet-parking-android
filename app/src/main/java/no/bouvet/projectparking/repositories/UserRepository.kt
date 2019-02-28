package no.bouvet.projectparking.repositories

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
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