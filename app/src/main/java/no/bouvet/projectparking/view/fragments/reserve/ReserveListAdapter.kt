package no.bouvet.projectparking.view.fragments.reserve

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.reserve_list_item.view.*
import no.bouvet.projectparking.R
import no.bouvet.projectparking.models.BookingSpot

class ReserveListAdapter(private val bookingSpotList : List<BookingSpot>, val context : Context?, val supportFragmentManager: FragmentManager?) : RecyclerView.Adapter<ReserveListAdapter.ReserveListHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReserveListHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.reserve_list_item, parent, false)

        return ReserveListHolder(view)
    }

    override fun getItemCount(): Int {
        return bookingSpotList.size
    }

    override fun onBindViewHolder(holder: ReserveListHolder, position: Int) {
        holder.pid.text = bookingSpotList[position].pid.toString()
    }


    class ReserveListHolder(view : View) : RecyclerView.ViewHolder(view){

        val pid = view.reserve_prNr

    }

}