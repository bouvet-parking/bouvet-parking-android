package no.bouvet.projectparking.view.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.microsoft.identity.client.*
import com.microsoft.identity.client.exception.*
import no.bouvet.projectparking.R
import no.bouvet.projectparking.view.fragments.login.LoginFailedDialogFragment
import no.bouvet.projectparking.view.fragments.login.LoginWelcomeDialogFragment

class LoginActivity : AppCompatActivity() {


    /* Azure AD Variables */
    private var sampleApp: PublicClientApplication? = null
    private var authResult: AuthenticationResult? = null
    val TAG = "LOGIN"

    internal val SCOPES = arrayOf("https://graph.microsoft.com/User.Read")


    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)

        setContentView(R.layout.login_layout)

        /* Configure your sample app and save state for this activity */
        sampleApp = null
        if (sampleApp == null) {
            sampleApp = PublicClientApplication(
                    this.applicationContext,
                    R.raw.auth_config)
        }

        /* Attempt to get a user and acquireTokenSilent
         * If this fails we do an interactive request
         */
        var accounts: List<IAccount>? = null

        try {
            sampleApp?.let {

                accounts = it.getAccounts()
            }

            if (accounts != null && accounts!!.size == 1) {
                /* We have 1 account */
                intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
               //0 or more accounts - do nothing for now, login on user press button
            }
        } catch (e: IndexOutOfBoundsException) {
            Log.d("LOGIN!", "Account at this position does not exist: $e")
        }


        val loginWelcomeMessage = LoginWelcomeDialogFragment()

        loginWelcomeMessage.isCancelable = false
        loginWelcomeMessage.show(supportFragmentManager, "Welcome")

    }

    // perform login on dialog fragment press
    fun performLogin(){

        sampleApp?.let{
            it.acquireToken(this, SCOPES, getAuthInteractiveCallback())
        }


    }

    /* Callback used for interactive request.  If succeeds we use the access
     * token to call the Microsoft Graph. Does not check cache
     */
    private fun getAuthInteractiveCallback(): AuthenticationCallback {
        Log.d("LOGIN", "CALLBACK IS RUNNING")
        return object : AuthenticationCallback {
            override fun onSuccess(authenticationResult: AuthenticationResult) {
                /* Successfully got a token, call graph now */
                Log.d(TAG, "Successfully authenticated")
                Log.d(TAG, "ID Token: " + authenticationResult.idToken)

                /* Store the auth result */
                authResult = authenticationResult

                startMain()

            }

            override fun onError(exception: MsalException) {
                /* Failed to acquireToken */
                Log.d(TAG, "Authentication failed: $exception")

                if (exception is MsalClientException) {
                    /* Exception inside MSAL, more info inside MsalError.java */
                } else if (exception is MsalServiceException) {
                    /* Exception when communicating with the STS, likely config issue */
                }
                val loginFailedDialog = LoginFailedDialogFragment()
                loginFailedDialog.isCancelable = false
                loginFailedDialog.show(supportFragmentManager, "Login Failed")
            }

            override fun onCancel() {
                /* User canceled the authentication */
                Log.d("LOGIN", "User cancelled login.")
                val loginFailedDialog = LoginFailedDialogFragment()
                loginFailedDialog.isCancelable = false
                loginFailedDialog.show(supportFragmentManager, "Login Canceled")
            }
        }
    }

    /* Handles the redirect from the System Browser */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        sampleApp?.handleInteractiveRequestRedirect(requestCode, resultCode, data)
    }

    private fun startMain(){
        intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

}