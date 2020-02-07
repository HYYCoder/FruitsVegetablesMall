package com.huangyiyang.fruitsvegetablesmall.view.classification

import android.content.Context
import android.widget.Checkable
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import q.rorbin.badgeview.Badge

open abstract class TabView : Checkable,
    ITabView, FrameLayout {

    constructor(context: Context) : super(context)

    override fun getTabView(): TabView? {
        return this
    }

    @Deprecated("")
    open abstract fun getIconView(): ImageView?

    open abstract fun getTitleView(): TextView?

    open abstract fun getBadgeView(): Badge?

}