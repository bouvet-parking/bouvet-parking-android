package no.bouvet.projectparking.network

import android.util.Log
import com.android.volley.AuthFailureError
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.firebase.firestore.FirebaseFirestore
import com.microsoft.identity.client.AuthenticationResult
import no.bouvet.projectparking.repositories.writeUser
import no.bouvet.projectparking.view.activities.MainActivity
import org.json.JSONObject
import java.util.HashMap

internal val MSGRAPH_URL = "https://graph.microsoft.com/v1.0/me"

fun userSetup(activity: MainActivity, authResult : AuthenticationResult, db : FirebaseFirestore) {
    Log.d("VOLLEY TO MSGRAPH", "Starting volley request to graph")

    /* Make sure we have a token to send to graph */
    if (authResult.accessToken == null) {
        return
    }

    val queue = Volley.newRequestQueue(activity.applicationContext)
    val parameters = JSONObject()

    try {
        parameters.put("key", "value")
    } catch (e: Exception) {
        Log.d("VOLLEY TO MSGRAPH", "Failed to put parameters: $e")
    }

    val request = object : JsonObjectRequest(Request.Method.GET, MSGRAPH_URL,
            parameters, Response.Listener { response ->
        /* Successfully called graph, process data and send to UI */
        Log.d("VOLLEY TO MSGRAPH", "Response: $response")
        activity.userData = response
        //WRITE USER USING REPO
        writeUser(db, response)

    }, Response.ErrorListener { error -> Log.d("VOLLEY TO MSGRAPH", "Error: $error") }) {
        @Throws(AuthFailureError::class)
        override fun getHeaders(): Map<String, String> {
            val headers = HashMap<String, String>()
            headers["Authorization"] = "Bearer " + authResult.accessToken
            return headers
        }
    }

    Log.d("VOLLEY TO MSGRAPH", "Adding HTTP GET to Queue, Request: $request")

    request.retryPolicy = DefaultRetryPolicy(
            3000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
    queue.add(request)
}