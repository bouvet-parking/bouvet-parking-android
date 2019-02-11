package no.bouvet.projectparking.view.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
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
import android.support.v4.content.ContextCompat


class ReserveFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var view : View = inflater.inflate(R.layout.content_main, container, false)

        val cV = view.findViewById<com.prolificinteractive.materialcalendarview.MaterialCalendarView>(no.bouvet.projectparking.R.id.calendarView)

        //SETUP CALENDER
        val startDate = Calendar.getInstance()
        val endDate = Calendar.getInstance()
        endDate.add(Calendar.DATE, 14)

        cV.newState()
                .setMinimumDate(CalendarDay.from(
                        startDate.get(Calendar.YEAR),
                        startDate.get(Calendar.MONTH),
                        startDate.get(Calendar.DATE)))
                .setMaximumDate(CalendarDay.from(
                        endDate.get(Calendar.YEAR),
                        endDate.get(Calendar.MONTH),
                        endDate.get(Calendar.DATE)))
                .commit()
        cV.selectedDate = CalendarDay.today()

        //Decorate days in calendar (Custom decorator defined in bottom of this file)

        context?.let{
        cV.addDecorator(AvailableDayDecorator(ContextCompat.getColor(it,R.color.availableColor), listOf(
                CalendarDay.from(
                        startDate.get(Calendar.YEAR),
                        startDate.get(Calendar.MONTH),
                        startDate.get(Calendar.DATE ))
                , CalendarDay.from(
                endDate.get(Calendar.YEAR),
                endDate.get(Calendar.MONTH),
                endDate.get(Calendar.DATE))
        )))
        cV.addDecorator(AvailableDayDecorator(ContextCompat.getColor(it,R.color.unavailableColor), listOf(
                CalendarDay.from(
                        startDate.get(Calendar.YEAR),
                        startDate.get(Calendar.MONTH),
                        startDate.get(Calendar.DATE)+1)
                , CalendarDay.from(
                endDate.get(Calendar.YEAR),
                endDate.get(Calendar.MONTH),
                endDate.get(Calendar.DATE)-1)
        )))}

        var oldDecorator : DayViewDecorator? = null

        //Do on select date
        cV.setOnDateChangedListener{ view, date, sel ->

            var myDate : Collection<CalendarDay> = emptyList()
            cV.selectedDate?.let {
                myDate = listOf(it)
            }

            context?.let {

                if (oldDecorator != null) {
                    cV.removeDecorator(oldDecorator)
                }
                oldDecorator = AvailableDayDecorator(
                        ContextCompat.getColor(it, R.color.colorSecondary), myDate)

                cV.addDecorator(oldDecorator
                )


            }

        }

        return view

    }

    //https://www.youtube.com/watch?v=UqtsyhASW74
}

//Custom Decorator
class AvailableDayDecorator(private val coloring: Int, dates : Collection<CalendarDay>) : DayViewDecorator {

    private val datesSet = HashSet<CalendarDay>(dates)

    override fun shouldDecorate(day :  CalendarDay?): Boolean {
        return datesSet.contains(day)
    }

    override fun decorate(p0: DayViewFacade?) {
        val drawable = GradientDrawable()
        drawable.shape = GradientDrawable.OVAL
        drawable.color = ColorStateList.valueOf(coloring)
        p0?.setBackgroundDrawable(drawable)
    }
}