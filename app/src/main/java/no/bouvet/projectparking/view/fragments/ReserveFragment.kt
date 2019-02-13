package no.bouvet.projectparking.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import no.bouvet.projectparking.R
import java.util.*
import kotlin.collections.HashSet
import android.content.res.ColorStateList
import android.graphics.drawable.GradientDrawable
import androidx.core.content.ContextCompat
import com.prolificinteractive.materialcalendarview.MaterialCalendarView


class ReserveFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var view : View = inflater.inflate(R.layout.content_main, container, false)




        return view

    }

}
