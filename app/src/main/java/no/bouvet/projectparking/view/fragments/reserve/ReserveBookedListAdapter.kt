package no.bouvet.projectparking.view.fragments.reserve

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.reserve_list_item.view.*
import no.bouvet.projectparking.R
import no.bouvet.projectparking.models.Booked

class ReserveBookedListAdapter(private val bookedSpotList : List<Booked>, val context : Context?, val supportFragmentManager: FragmentManager?) : RecyclerView.Adapter<ReserveBookedListAdapter.ReserveBookedListHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReserveBookedListHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.reserve_list_item, parent, false)

        return ReserveBookedListHolder(view)
    }

    override fun getItemCount(): Int {
        return bookedSpotList.size
    }

    override fun onBindViewHolder(holder: ReserveBookedListHolder, position: Int) {
        holder.pid.text = bookedSpotList[position].pid
        holder.itemView.setOnClickListener {
            supportFragmentManager?.let{
                val data = bookedSpotList[position]
                val fragment = ReserveBookedBottomSheet(data)
                fragment.show(it, "BookedFragment")
            }
        }
    }



    class ReserveBookedListHolder(view : View) : RecyclerView.ViewHolder(view){
        val pid = view.reserve_prNr

    }

}