package no.bouvet.projectparking

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem
import android.widget.Button

import kotlinx.android.synthetic.main.activity_main.*
import no.bouvet.projectparking.fragments.DropInFragment
import no.bouvet.projectparking.fragments.MainFragmentAdapter
import no.bouvet.projectparking.fragments.ReserveFragment

class MainActivity : AppCompatActivity() {

    private lateinit var mMainFragAdapter : MainFragmentAdapter
    private lateinit var viewPager : ViewPager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        mMainFragAdapter = MainFragmentAdapter(supportFragmentManager)
        viewPager = findViewById(R.id.container)

        setUpPager(viewPager)


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
