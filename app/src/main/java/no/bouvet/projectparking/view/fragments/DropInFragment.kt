package no.bouvet.projectparking.view.fragments

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.Activity
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ShapeDrawable
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.fragment.app.Fragment
import androidx.core.content.ContextCompat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.content_dropin.*
import no.bouvet.projectparking.R
import no.bouvet.projectparking.models.ParkingSpot
import no.bouvet.projectparking.viewmodels.DropInViewModel


class DropInFragment : Fragment() {


    //List View Variables
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //TODO: Loadingwheel!
        val model = ViewModelProviders.of(this).get(DropInViewModel::class.java)
        model.getParkingSpots().observe(this, Observer<List<ParkingSpot>> { parkingSpots ->
            //Todo: DO SOMETHING
            Log.d("UPDATE_DROPINVIEW", "SOMETHING HAS HAPPENED, DATA LOADED")
            Log.d("DROPINGVIEW:RESULTS", parkingSpots.toString())
            var count = 0
            for (i in parkingSpots.orEmpty()) {
                if (i.spotStatus == "available") count += 1
            }


            //SHAPE
            val circ = GradientDrawable()
            circ.shape = GradientDrawable.OVAL
            circ.setColor(0xFFFFFF)

            availableSpots.text = count.toString()
            availableSpots.background = circ

            when (count) {
                1 -> {
                    availableText.text = getString(R.string.avail)
                    context?.let {
                        availableSpots.setTextColor(ContextCompat.getColor(it, R.color.availableColor))
                        circ.setStroke( (resources.displayMetrics.widthPixels*0.011).toInt() , ContextCompat.getColor(it, R.color.availableColor))
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
                        circ.setStroke((resources.displayMetrics.widthPixels*0.011).toInt(), ContextCompat.getColor(it, R.color.availableColor))
                        availableSpots.setTextColor(ContextCompat.getColor(it, R.color.availableColor))
                    }
                }
            }
            //Handle List Dropdown Button
            context?.let {
                if(spotList.visibility == View.GONE){
                    listButton.setBackgroundResource(R.drawable.down_button)
                    spotList.alpha = 0f
                    drop_text.visibility = View.GONE}
                listButton.setOnClickListener {
                    if(spotList.visibility == View.VISIBLE){
                        listButton.setBackgroundResource(R.drawable.down_button)
                        spotList.animate().alpha(0f)
                                .setDuration(300)
                                .setListener(object : AnimatorListenerAdapter() {
                                    override fun onAnimationEnd(animation: Animator?) {
                                        super.onAnimationEnd(animation)
                                        spotList.visibility = View.GONE
                                        spotList.clearAnimation()
                                        hor_divider.visibility = View.VISIBLE
                                        drop_text.visibility = View.GONE
                                    }
                                })

                    }
                    else {
                        drop_text.visibility = View.VISIBLE
                        hor_divider.visibility = View.GONE
                        spotList.visibility = View.VISIBLE
                        spotList.animate().alpha(1f)
                                .setDuration(300)
                                .setListener(
                        object : AnimatorListenerAdapter(){
                            override fun onAnimationEnd(animation: Animator?) {
                                super.onAnimationEnd(animation)
                                spotList.clearAnimation()

                            }
                        }
                        )
                        listButton.setBackgroundResource(R.drawable.up_button)

                    }
                }
            }
            //Handle List

            viewManager = GridLayoutManager(context, 4)
            parkingSpots?.let {

                val parkingSpotAvailableList = it.filter { it.spotStatus == "available" }
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

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dropInRefreshContainer.setOnRefreshListener {
            val model = ViewModelProviders.of(this).get(DropInViewModel::class.java)
            model.refreshParkingSpots(dropInRefreshContainer)
            Log.d("REFRESH", "REFRESHED")
        }
    }

}