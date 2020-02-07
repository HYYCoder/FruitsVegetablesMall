package com.huangyiyang.fruitsvegetablesmall.view.classification

import android.content.Context
import android.view.ViewGroup
import android.widget.FrameLayout
import q.rorbin.badgeview.QBadgeView

class TabBadgeView : QBadgeView {

    constructor(context: Context?) : super(context)

    companion object {
        fun bindTab(tab: TabView): TabBadgeView? {
            var badge: TabBadgeView? = null
            for (i in 0 until tab.childCount) {
                val child = tab.getChildAt(i)
                if (child != null && child is TabBadgeView) {
                    badge = child
                    break
                }
            }
            if (badge == null) {
                badge =
                    TabBadgeView(
                        tab.context
                    )
                tab.addView(
                    badge,
                    ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                )
            }
            badge.mTargetView = tab
            return badge
        }
    }

    override fun screenFromWindow(screen: Boolean) {
        if (parent != null) {
            (parent as ViewGroup).removeView(this)
        }
        if (screen) {
            mActivityRoot.addView(
                this, FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT
                )
            )
        } else {
            if (mTargetView is TabView) {
                (mTargetView as TabView).addView(
                    this,
                    ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                )
            } else {
                bindTarget(mTargetView)
            }
        }
    }
}