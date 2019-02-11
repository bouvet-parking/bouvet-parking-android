package no.bouvet.projectparking.view.fragments

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.parking_list_item.view.*
import no.bouvet.projectparking.R
import no.bouvet.projectparking.models.ParkingSpot

class DropInListAdapter(private val parkingSpotData : List<ParkingSpot>, val context : Context?) :
    RecyclerView.Adapter<DropInListAdapter.DropInListViewHolder>(){

    class DropInListViewHolder(view: View) :
            RecyclerView.ViewHolder(view){
        val nr = view.prNr
        val status = view.prStat

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DropInListViewHolder {
        return DropInListViewHolder(LayoutInflater.from(context).inflate(R.layout.parking_list_item,
                parent, false))
    }

    override fun getItemCount(): Int {
        return parkingSpotData.size
    }

    override fun onBindViewHolder(holder : DropInListViewHolder, position: Int) {
        holder.nr?.text = parkingSpotData[position].parkingSensorId.toString()
        holder.status?.text = parkingSpotData[position].spotStatus
    }
}