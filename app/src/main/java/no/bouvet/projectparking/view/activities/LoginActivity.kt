package no.bouvet.projectparking.view.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import no.bouvet.projectparking.R
import no.bouvet.projectparking.view.fragments.LoginFailedDialogFragment
import no.bouvet.projectparking.view.fragments.LoginWelcomeDialogFragment


//TODO: FIKSE history, vil ikke gå tilbake hit om logget inn!!!!!

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.login_layout)

        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        } else {

            val loginWelcomeMessage = LoginWelcomeDialogFragment()

            loginWelcomeMessage.isCancelable = false
            loginWelcomeMessage.show(supportFragmentManager, "Welcome")

        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                // Successfully signed in
                val user = FirebaseAuth.getInstance().currentUser
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                // ...
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
                val loginDialogFragment = LoginFailedDialogFragment()

                loginDialogFragment.isCancelable = false
                loginDialogFragment.show(supportFragmentManager, "Login")
            }
        }
    }

    fun performLogin(){

        val providers = arrayListOf(
                AuthUI.IdpConfig.PhoneBuilder().build())

        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                RC_SIGN_IN)



    }

    companion object {

        private const val RC_SIGN_IN = 123
    }

}