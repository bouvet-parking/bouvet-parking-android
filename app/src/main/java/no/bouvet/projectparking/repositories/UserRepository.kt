package no.bouvet.projectparking.repositories

import com.google.firebase.firestore.FirebaseFirestore
import org.json.JSONObject
import java.util.HashMap

fun writeUser(db : FirebaseFirestore, userData: JSONObject){

    val userRef = db.collection("users").document(userData.getString("id"))
    val userDoc = userRef.get().addOnSuccessListener {
        if(!it.exists()){
            val user = HashMap<String, Any>()
            user["uid"] = userData["id"]
            user["phone"] = userData["mobilePhone"]
            user["fullName"] = userData["displayName"]
            userRef.set(user)
        }
    }


}