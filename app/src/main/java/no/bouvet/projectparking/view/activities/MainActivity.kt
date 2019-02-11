package no.bouvet.projectparking.view.activities

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth

import kotlinx.android.synthetic.main.activity_main.*
import no.bouvet.projectparking.R
import no.bouvet.projectparking.view.fragments.MainFragmentAdapter
import no.bouvet.projectparking.view.fragments.DropInFragment
import no.bouvet.projectparking.view.fragments.ReserveFragment

class MainActivity : AppCompatActivity() {

    private lateinit var mMainFragAdapter : MainFragmentAdapter
    private lateinit var viewPager : ViewPager

    private lateinit var auth: FirebaseAuth

    public override fun onStart() {

        //onStart checks if user is logged in

        //TODO: Possible to make app entry quicker?
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val user = auth.currentUser
        if (user != null) {
            // User is signed in
        } else {
            // No user is signed in
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()


        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        //Floating Action Button
        //TODO: Move Sign out function - this is temporary!!!
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Signed Out", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
            AuthUI.getInstance()
                    .signOut(this)
                    .addOnCompleteListener {
                        // ...
                    }
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        //Plugs in fragments for dropin and reserve screens

        mMainFragAdapter = MainFragmentAdapter(supportFragmentManager)
        viewPager = findViewById(R.id.container)

        setUpPager(viewPager)

        //NAVIGATION BUTTONS FUNCTIONS AND STYLE
        var btnDropIn = findViewById<Button>(R.id.di_di_button)
        var btnReserve = findViewById<Button>(R.id.res_di_button)

        val pColVal = ContextCompat.getColor(this, R.color.colorPrimary)
        val sColVal = ContextCompat.getColor(this, R.color.colorSecondary)


        btnDropIn.setOnClickListener {
            setViewPager(0)
        }
        btnReserve.setOnClickListener{
            setViewPager(1)
        }

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(p0: Int) {
                var i = viewPager.currentItem

                if(i == 0){
                    btnDropIn.setBackgroundColor(sColVal)
                    btnReserve.setBackgroundColor(pColVal)
                }
                else{
                    btnDropIn.setBackgroundColor(pColVal)
                    btnReserve.setBackgroundColor(sColVal)
                }
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
        mMainFragAdapter.addFragment( DropInFragment(), "DropInFragment" )
        mMainFragAdapter.addFragment(ReserveFragment(), "ReservationFragment")

        vP.adapter = mMainFragAdapter

    }

    public fun setViewPager(i : Int){
        viewPager.currentItem = i
    }
}
