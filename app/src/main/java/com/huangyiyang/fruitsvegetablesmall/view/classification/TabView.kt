package com.huangyiyang.fruitsvegetablesmall.view.classification

import android.content.Context
import android.widget.Checkable
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import q.rorbin.badgeview.Badge

abstract class TabView(context: Context) : Checkable,
    ITabView, FrameLayout(context) {

    override fun getTabView(): TabView? {
        return this
    }

    @Deprecated("")
    abstract fun getIconView(): ImageView?

    abstract fun getTitleView(): TextView?

    abstract fun getBadgeView(): Badge?

}