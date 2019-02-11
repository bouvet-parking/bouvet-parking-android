package no.bouvet.projectparking.view.fragments

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
            availableSpots.text = count.toString()

            when (count) {
                1 -> {
                    availableText.text = "ledig plass!"
                    context?.let {
                        availableSpots.setTextColor(ContextCompat.getColor(it, R.color.availableColor))
                    }
                }

                0 -> {
                    context?.let {
                        availableSpots.setTextColor(ContextCompat.getColor(it, R.color.unavailableColor))
                    }
                    availableText.text = "ledige plasser :("
                }
                else -> {
                    availableText.text = "ledige plasser!"
                    context?.let {
                        availableSpots.setTextColor(ContextCompat.getColor(it, R.color.availableColor))
                    }
                }
            }
            //Handle List Dropdown Button
            context?.let {
                listButton.setBackgroundResource(R.drawable.down_button)
                spotList.alpha = 0f
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
                                    }
                                })

                    }
                    else {
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

            viewManager = LinearLayoutManager(context)
            parkingSpots?.let {
                viewAdapter = DropInListAdapter(it, context)
            }
            recyclerView = spotList.apply {
                // use this setting to improve performance if you know that changes
                // in content do not change the layout size of the RecyclerView
                setHasFixedSize(true)

                // use a linear layout manager
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