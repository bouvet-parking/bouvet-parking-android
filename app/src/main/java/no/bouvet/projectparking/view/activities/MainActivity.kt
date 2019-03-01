package no.bouvet.projectparking.view.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import androidx.viewpager.widget.ViewPager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity

import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.firebase.firestore.FirebaseFirestore
import com.microsoft.identity.client.AuthenticationCallback
import com.microsoft.identity.client.AuthenticationResult
import com.microsoft.identity.client.IAccount
import com.microsoft.identity.client.PublicClientApplication
import com.microsoft.identity.client.exception.MsalClientException
import com.microsoft.identity.client.exception.MsalException
import com.microsoft.identity.client.exception.MsalServiceException
import com.microsoft.identity.client.exception.MsalUiRequiredException

import kotlinx.android.synthetic.main.activity_main.*
import no.bouvet.projectparking.R
import no.bouvet.projectparking.view.fragments.MainFragmentAdapter
import no.bouvet.projectparking.view.fragments.dropin.DropInFragment
import no.bouvet.projectparking.view.fragments.reserve.ReserveFragment
import org.json.JSONObject
import no.bouvet.projectparking.network.userSetup
class MainActivity : AppCompatActivity() {

    private lateinit var mMainFragAdapter : MainFragmentAdapter
    private lateinit var viewPager : ViewPager

    var TAG = "PROJECT_PARKING:MAIN_ACTIVITY:"

    /* Azure AD v2 Configs */
    internal val SCOPES = arrayOf("https://graph.microsoft.com/User.Read")

    /* Azure AD Variables */
    companion object {
        var sampleApp: PublicClientApplication? = null
        private var authResult: AuthenticationResult? = null
    }


    val db = FirebaseFirestore.getInstance()

    lateinit var userData : JSONObject


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Check Login
        checkLoggedIn()
        //Setup view
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar as (androidx.appcompat.widget.Toolbar))
        supportActionBar?.setDisplayShowTitleEnabled(false)

        //Center Toolbar Image
        mainImage.viewTreeObserver.addOnGlobalLayoutListener {
            val offset = (toolbar.width/2 - mainImage.width/2).toFloat()
            mainImage.x = offset
        }


        //Floating Action Button
        fab.setOnClickListener { view ->
            intent = Intent(this, ProfileActivity::class.java)
            intent.putExtra("uid", userData.getString("id"))
            startActivity(intent)
        }

        //Plug in fragments for dropin and reserve screens

        mMainFragAdapter = MainFragmentAdapter(supportFragmentManager)
        viewPager = findViewById(R.id.container) as ViewPager

        setUpPager(viewPager)

        //NAVIGATION BUTTONS FUNCTIONS AND STYLE
        val mTabs = tabs as TabLayout

        mTabs.setupWithViewPager(viewPager)
        mTabs.getTabAt(0)?.text = "Drop-In"
        mTabs.getTabAt(1)?.text = "Reserver"


        //Handle tab navigation
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(p0: Int) {
                var i = viewPager.currentItem
            }
            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
            }
            override fun onPageSelected(p0: Int) {
            }
        })

    }



    //Settings Menu

    //TODO: Add signout to this + other options?!?
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
    
    
    //Sets up the page viewer, with dropin and reservation view
    private fun setUpPager(vP : ViewPager){
        mMainFragAdapter.addFragment(DropInFragment(), "DropInFragment" )
        mMainFragAdapter.addFragment(ReserveFragment(), "ReservationFragment")

        vP.adapter = mMainFragAdapter

    }

    //AZURE METHODS for handling logged in user
    private fun checkLoggedIn(){
        //AZURE AD LOGIN
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
                sampleApp?.let {
                    it.acquireTokenSilentAsync(SCOPES, accounts!![0], getAuthSilentCallback())
                }
            } else {
                goToLogin()
            }
        } catch (e: IndexOutOfBoundsException) {
            Log.d(TAG, "Account at this position does not exist: $e")
        }
    }

    private fun getAuthSilentCallback(): AuthenticationCallback {
        return object : AuthenticationCallback {
            override fun onSuccess(authenticationResult: AuthenticationResult) {
                /* Successfully got a token, call graph now */
                Log.d(TAG, "Successfully authenticated")

                /* Store the authResult */
                authResult = authenticationResult
                //Make sure user is stored in firebase, and set up userData variable
                userSetup(getActivity(), authenticationResult, db)


            }

            override fun onError(exception: MsalException) {
                /* Failed to acquireToken */
                Log.d(TAG, "Authentication failed: $exception")

                if (exception is MsalClientException) {
                    /* Exception inside MSAL, more info inside MsalError.java */
                } else if (exception is MsalServiceException) {
                    /* Exception when communicating with the STS, likely config issue */
                } else if (exception is MsalUiRequiredException) {
                    /* Tokens expired or no session, retry with interactive */
                }
                goToLogin()
            }

            override fun onCancel() {
                /* User canceled the authentication */
                Log.d(TAG, "User cancelled login.")
                goToLogin()
            }
        }
    }

    fun goToLogin(){
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun getActivity() : MainActivity{
        return this
    }

}
