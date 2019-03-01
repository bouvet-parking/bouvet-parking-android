package no.bouvet.projectparking.view.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.microsoft.identity.client.IAccount
import kotlinx.android.synthetic.main.activity_profile.*
import no.bouvet.projectparking.R
import no.bouvet.projectparking.repositories.getUser

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        //TODO: Should be moved to UserViewModel
        getUser(intent.getStringExtra("uid")){
            profile_phone.text = it.phone
            profile_plate_nr.text = it.plateNumber
        }

        //Set button listeners

        profile_logout_button.setOnClickListener {

            onSignOut()

        }

        profile_back_button.setOnClickListener {
            finish()
        }


    }

    private fun onSignOut() {

        /* Attempt to get a account and remove their cookies from cache */
        /* User is stored in main activity companion object, which we can remove from here */
        var accounts: List<IAccount>? = null

        try {
            accounts = MainActivity.sampleApp?.getAccounts()

            if (accounts == null) {
                /* We have no accounts */

            } else if (accounts.size == 1) {
                /* We have 1 account */
                /* Remove from token cache */
                MainActivity.sampleApp?.removeAccount(accounts[0])

            } else {
                /* We have multiple accounts */
                for (i in accounts.indices) {
                    MainActivity.sampleApp?.removeAccount(accounts[i])
                }
            }
            goToLogin()

        } catch (e: IndexOutOfBoundsException) {
            Log.d("LOGOUT", "User at this position does not exist: $e")
        }

    }

    private fun goToLogin(){
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }



}