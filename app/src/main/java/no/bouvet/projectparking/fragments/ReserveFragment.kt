package no.bouvet.projectparking.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import kotlinx.android.synthetic.main.content_main.*
import no.bouvet.projectparking.MainActivity
import no.bouvet.projectparking.R

class ReserveFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var view : View = inflater.inflate(R.layout.content_main, container, false)
        
        return view
    }

    //https://www.youtube.com/watch?v=UqtsyhASW74
}