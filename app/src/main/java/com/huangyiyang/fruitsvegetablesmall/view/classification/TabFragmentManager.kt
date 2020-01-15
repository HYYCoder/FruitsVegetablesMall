package com.huangyiyang.fruitsvegetablesmall.view.classification

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

class TabFragmentManager {
    private var mManager: FragmentManager? = null
    private var mContainerResid = 0
    private var mFragments: List<Fragment>? = null
    private var mTabLayout: VerticalTabLayout? = null
    private var mListener: VerticalTabLayout.OnTabSelectedListener? = null

    constructor()

    constructor(
        manager: FragmentManager?,
        fragments: List<Fragment>?,
        tabLayout: VerticalTabLayout?
    ) {
        mManager = manager
        mFragments = fragments
        mTabLayout = tabLayout
        mListener =
            OnFragmentTabSelectedListener()
        mTabLayout!!.addOnTabSelectedListener(mListener)
    }

    constructor(
        manager: FragmentManager?,
        containerResid: Int,
        fragments: List<Fragment>?,
        tabLayout: VerticalTabLayout?
    ) : this(manager, fragments, tabLayout) {
        mContainerResid = containerResid
        changeFragment()
    }

    fun changeFragment() {
        val ft: FragmentTransaction = mManager?.beginTransaction()!!
        val position = mTabLayout!!.getSelectedTabPosition()
        val addedFragments: List<Fragment> = mManager?.fragments!!
        for (i in mFragments!!.indices) {
            val fragment: Fragment = mFragments!![i]
            if ((!addedFragments.contains(fragment)) && mContainerResid != 0) {
                ft.add(mContainerResid, fragment)
            }
            if (mFragments!!.size > position && i == position
                || mFragments!!.size <= position && i == mFragments!!.size - 1
            ) {
                ft.show(fragment)
            } else {
                ft.hide(fragment)
            }
        }
        ft.commit()
        mManager?.executePendingTransactions()
    }

    fun detach() {
        val ft: FragmentTransaction = mManager?.beginTransaction()!!
        for (fragment in mFragments!!) {
            ft.remove(fragment)
        }
        ft.commit()
        mManager?.executePendingTransactions()
        mManager = null
        mFragments = null
        mTabLayout!!.removeOnTabSelectedListener(mListener)
        mListener = null
        mTabLayout = null
    }


    private class OnFragmentTabSelectedListener :
        VerticalTabLayout.OnTabSelectedListener {
        override fun onTabSelected(tab: TabView?, position: Int) {
            TabFragmentManager()
                .changeFragment()
        }

        override fun onTabReselected(tab: TabView?, position: Int) {}
    }
}