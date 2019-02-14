package no.bouvet.projectparking.view.fragments.dropin

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.parking_list_item.view.*
import no.bouvet.projectparking.R
import no.bouvet.projectparking.models.ParkingSpot

class DropInListAdapter(private val parkingSpotData : List<ParkingSpot>, val context : Context?, val supportFragmentManager: FragmentManager?) :
    RecyclerView.Adapter<DropInListAdapter.DropInListViewHolder>(){

    class DropInListViewHolder(view: View) :
            RecyclerView.ViewHolder(view){
        val nr = view.prNr
        //val status = view.prStat

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DropInListViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.parking_list_item,
                parent, false)

        return DropInListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return parkingSpotData.size
    }

    override fun onBindViewHolder(holder : DropInListViewHolder, position: Int) {
        holder.nr?.text = parkingSpotData[position].pid.toString()
        //holder.status?.text = parkingSpotData[position].spotStatus
        holder.itemView.setOnClickListener{
            supportFragmentManager?.let {
                val data = parkingSpotData[position]
                val fragment = DropInBottomSheetFragment(data)
                fragment.show(it, "DropInSheet")
            }
        }
    }
}