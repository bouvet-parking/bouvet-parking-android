package no.bouvet.projectparking.view.fragments

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class MainFragmentAdapter : FragmentPagerAdapter {

    private val fragList  = ArrayList<Fragment>()
    private val fragTitleList = ArrayList<String>()


    constructor(fm: FragmentManager?) : super(fm)

    fun addFragment(frag: Fragment, title : String){
        fragList.add(frag)
        fragTitleList.add(title)
    }

    override fun getItem(p0: Int): Fragment {
        return fragList[p0]
    }

    override fun getCount(): Int {
        return fragList.size
    }
}