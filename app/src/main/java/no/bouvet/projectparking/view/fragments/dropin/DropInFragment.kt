package no.bouvet.projectparking.view.fragments.dropin

import android.graphics.drawable.GradientDrawable
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.core.content.ContextCompat
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.ScrollView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.content_dropin.*
import no.bouvet.projectparking.R
import no.bouvet.projectparking.models.ParkingSpot
import no.bouvet.projectparking.viewmodels.DropInViewModel
import org.jetbrains.anko.firstChildOrNull


class DropInFragment : Fragment() {


    //List View Variables
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //TODO: Loadingwheel!

        //Load and observe Drop in parking spots
        val model = ViewModelProviders.of(this).get(DropInViewModel::class.java)
        model.getParkingSpots().observe(this, Observer<List<ParkingSpot>> { parkingSpots ->
            Log.d("UPDATE_DROPINVIEW", "SOMETHING HAS HAPPENED, DATA LOADED")
            Log.d("DROPINGVIEW:RESULTS", parkingSpots.toString())
            var count = 0
            for (i in parkingSpots.orEmpty()) {
                if (i.spotStatus == "available") count += 1
            }

            //Set up available spots presenter
            //SHAPE
            val circ = GradientDrawable()
            circ.shape = GradientDrawable.OVAL
            circ.setColor(0xFFFFFF)

            availableSpots.text = count.toString()
            availableSpots.background = circ
            //TEXT
            when (count) {
                1 -> {
                    availableText.text = getString(R.string.avail)
                    context?.let {
                        availableSpots.setTextColor(ContextCompat.getColor(it, R.color.secondaryColor))
                        circ.setStroke( (resources.displayMetrics.widthPixels*0.011).toInt() , ContextCompat.getColor(it, R.color.secondaryColor))
                    }
                }

                0 -> {
                    context?.let {
                        availableSpots.setTextColor(ContextCompat.getColor(it, R.color.unavailableColor))
                        circ.setStroke((resources.displayMetrics.widthPixels*0.011).toInt(), ContextCompat.getColor(it, R.color.unavailableColor))
                    }
                    availableText.text = getString(R.string.unavail)
                }
                else -> {
                    availableText.text = getString(R.string.avail_plur)
                    context?.let {
                        circ.setStroke((resources.displayMetrics.widthPixels*0.011).toInt(), ContextCompat.getColor(it, R.color.secondaryColor))
                        availableSpots.setTextColor(ContextCompat.getColor(it, R.color.secondaryColor))
                    }
                }
            }

            //Set up grid-view of available spots
            viewManager = GridLayoutManager(context, 3)
            parkingSpots?.let {
                val parkingSpotAvailableList  = it.filter { it.spotStatus == "available" }
                viewAdapter = DropInListAdapter(parkingSpotAvailableList, context, fragmentManager)
            }
            recyclerView = spotList.apply {
                // use this setting to improve performance if you know that changes
                // in content do not change the layout size of the RecyclerView
                setHasFixedSize(false)

                // use a linear/grid layout manager
                layoutManager = viewManager

                // specify an viewAdapter (see also next example)
                adapter = viewAdapter

            }


        })


    }

    override fun onResume() {
        super.onResume()

        Log.d("DROPIN_FRAGMENT: RESUME", "RESUMED")
        val model = ViewModelProviders.of(this).get(DropInViewModel::class.java)
        model.reloadParkingSpots()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var view: View = inflater.inflate(R.layout.content_dropin, container, false)

        //Currently scrapped code for changing toolbar size on scroll programatically

       /* val toolbar = activity!!.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        val dropInScroll = view.findViewById<ScrollView>(R.id.dropinscroll)
        val layoutBelow = activity!!.findViewById<RelativeLayout>(R.id.main_content) as RelativeLayout
        val toolbarMaxHeight = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 156f, resources.displayMetrics
        ).toInt()
        var oldY = 0
        var relY = 0
        dropInScroll.viewTreeObserver.addOnScrollChangedListener {
            var y = dropInScroll.scrollY

            relY += (y - oldY)

            Log.d("scroll", "${y} $toolbarMaxHeight")
            if(relY < (2*toolbarMaxHeight)/3){
               val layoutParams = toolbar.layoutParams
                       layoutParams.height = toolbarMaxHeight-relY
                toolbar.layoutParams = layoutParams
                val belowParams = layoutBelow.layoutParams as ViewGroup.MarginLayoutParams
                belowParams.topMargin = toolbarMaxHeight - relY
                layoutBelow.layoutParams = belowParams
            }
            oldY = y

        }*/

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //Listen for refresh gesture
        dropInRefreshContainer.setOnRefreshListener {
            val model = ViewModelProviders.of(this).get(DropInViewModel::class.java)
            model.refreshParkingSpots(dropInRefreshContainer)
        }
    }

}