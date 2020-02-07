package com.huangyiyang.fruitsvegetablesmall.view.classification

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.TextUtils
import android.view.Gravity
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.Px
import androidx.annotation.RequiresApi
import com.huangyiyang.fruitsvegetablesmall.R
import q.rorbin.badgeview.Badge

class QTabView :
    TabView {
    private var mContext: Context
    private var mTitle: TextView? = null
    private var mBadgeView: Badge? = null
    private var mTabIcon: ITabView.TabIcon?
    private var mTabTitle: ITabView.TabTitle?
    private var mTabBadge: ITabView.TabBadge?
    private var mChecked = false
    private var mDefaultBackground: Drawable? = null


    constructor(context: Context) :super(context){
        mContext = context
        mTabIcon = ITabView.TabIcon.Builder().build()
        mTabTitle = ITabView.TabTitle.Builder().build()
        mTabBadge = ITabView.TabBadge.Builder().build()
        initView()
        val attrs: IntArray
        attrs = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            intArrayOf(R.attr.selectableItemBackgroundBorderless)
        } else {
            intArrayOf(R.attr.selectableItemBackground)
        }
        val a = mContext?.theme.obtainStyledAttributes(attrs)
        mDefaultBackground = a.getDrawable(0)
        a.recycle()
        setDefaultBackground()
    }

    private fun initView() {
        minimumHeight = DisplayUtil()
            .dp2px(mContext, 25f)
        if (mTitle == null) {
            mTitle = TextView(mContext)
            val params =
                LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT)
            params.gravity = Gravity.CENTER
            mTitle!!.layoutParams = params
            this.addView(mTitle)
        }
        initTitleView()
        initIconView()
        initBadge()
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    override fun setPaddingRelative(@Px start: Int, @Px top: Int, @Px end: Int, @Px bottom: Int) {
        mTitle?.setPaddingRelative(start, top, end, bottom)
    }

    override fun setPadding(@Px left: Int, @Px top: Int, @Px right: Int, @Px bottom: Int) {
        mTitle?.setPadding(left, top, right, bottom)
    }

    private fun initBadge() {
        mBadgeView = TabBadgeView.bindTab(this)
        if (mTabBadge?.backgroundColor !== -0x17b1c0) {
            mBadgeView!!.badgeBackgroundColor = mTabBadge!!.backgroundColor
        }
        if (mTabBadge?.badgeTextColor !== -0x1) {
            mBadgeView!!.badgeTextColor = mTabBadge!!.badgeTextColor
        }
        if (mTabBadge?.strokeWidth?.toInt() !== Color.TRANSPARENT || mTabBadge?.strokeWidth?.toInt() !== 0 ) {
            mBadgeView!!.stroke(mTabBadge?.strokeWidth?.toInt()!!, mTabBadge?.strokeWidth!!, true)
        }
        if (mTabBadge?.drawableBackground != null || mTabBadge?.isDrawableBackgroundClip!!) {
            mBadgeView!!.setBadgeBackground(
                mTabBadge?.drawableBackground,
                mTabBadge?.isDrawableBackgroundClip!!
            )
        }
        if (mTabBadge?.badgeTextSize?.toInt() !== 11) {
            mBadgeView!!.setBadgeTextSize(mTabBadge?.badgeTextSize!!, true)
        }
        if (mTabBadge?.badgePadding?.toInt() !== 5) {
            mBadgeView!!.setBadgePadding(mTabBadge?.badgePadding!!, true)
        }
        if (mTabBadge?.badgeNumber?.toInt() !== 0) {
            mBadgeView!!.badgeNumber = mTabBadge?.badgeNumber!!
        }
        if (mTabBadge?.badgeText != null) {
            mBadgeView!!.badgeText = mTabBadge?.badgeText!!
        }
        if (mTabBadge?.badgeGravity !== Gravity.END or Gravity.TOP) {
            mBadgeView!!.badgeGravity = mTabBadge?.badgeGravity!!
        }
        if (mTabBadge?.gravityOffsetX !== 5 || mTabBadge?.gravityOffsetY !== 5) {
            mBadgeView!!.setGravityOffset(
                mTabBadge?.gravityOffsetX?.toFloat()!!,
                mTabBadge?.gravityOffsetY?.toFloat()!!,
                true
            )
        }
        if (mTabBadge?.isExactMode!!) {
            mBadgeView!!.isExactMode = mTabBadge?.isExactMode!!
        }
        if (!mTabBadge?.isShowShadow!!) {
            mBadgeView!!.isShowShadow = mTabBadge?.isShowShadow!!
        }
        if (mTabBadge?.onDragStateChangedListener != null) {
            mBadgeView!!.setOnDragStateChangedListener(mTabBadge?.onDragStateChangedListener)
        }
    }

    private fun initTitleView() {
        mTitle?.setTextColor(if (isChecked) mTabTitle?.colorSelected!! else mTabTitle?.colorNormal!!)
        mTitle!!.textSize = mTabTitle?.titleTextSize?.toFloat()!!
        mTitle?.text = mTabTitle?.content
        mTitle!!.gravity = Gravity.CENTER
        mTitle!!.ellipsize = TextUtils.TruncateAt.END
        refreshDrawablePadding()
    }

    private fun initIconView() {
        val iconResid: Int = if (mChecked) mTabIcon?.selectedIcon!! else mTabIcon?.normalIcon!!
        var drawable: Drawable? = null
        if (iconResid != 0) {
            drawable = mContext!!.resources.getDrawable(iconResid)
            val r =
                if (mTabIcon?.iconWidth !== -1) mTabIcon?.iconWidth else drawable.intrinsicWidth
            val b =
                if (mTabIcon?.iconHeight !== -1) mTabIcon?.iconHeight else drawable.intrinsicHeight
            drawable.setBounds(0, 0, r!!, b!!)
        }
        when (mTabIcon?.iconGravity) {
            Gravity.START -> mTitle!!.setCompoundDrawables(drawable, null, null, null)
            Gravity.TOP -> mTitle!!.setCompoundDrawables(null, drawable, null, null)
            Gravity.END -> mTitle!!.setCompoundDrawables(null, null, drawable, null)
            Gravity.BOTTOM -> mTitle!!.setCompoundDrawables(null, null, null, drawable)
        }
        refreshDrawablePadding()
    }

    private fun refreshDrawablePadding() {
        val iconResid: Int = if (mChecked) mTabIcon?.selectedIcon!! else mTabIcon?.normalIcon!!
        if (iconResid != 0) {
            if (!TextUtils.isEmpty(mTabTitle?.content) && mTitle!!.compoundDrawablePadding != mTabIcon?.margin) {
                mTitle!!.compoundDrawablePadding = mTabIcon?.margin!!
            } else if (TextUtils.isEmpty(mTabTitle?.content)) {
                mTitle!!.compoundDrawablePadding = 0
            }
        } else {
            mTitle!!.compoundDrawablePadding = 0
        }
    }

    override fun setBadge(badge: ITabView.TabBadge?): QTabView? {
        if (badge != null) {
            mTabBadge = badge
        }
        initBadge()
        return this
    }

    override fun setIcon(icon: ITabView.TabIcon?): QTabView? {
        if (icon != null) {
            mTabIcon = icon
        }
        initIconView()
        return this
    }

    override fun setTitle(title: ITabView.TabTitle?): QTabView? {
        if (title != null) {
            mTabTitle = title
        }
        initTitleView()
        return this
    }

    /**
     * @param resId The Drawable res to use as the background, if less than 0 will to remove the
     * background
     */
    override fun setBackground(resId: Int): QTabView? {
        if (resId == 0) {
            setDefaultBackground()
        } else if (resId <= 0) {
            background = null
        } else {
            super.setBackgroundResource(resId)
        }
        return this
    }

    override fun getBadge(): ITabView.TabBadge? {
        return mTabBadge
    }

    override fun getIcon(): ITabView.TabIcon? {
        return mTabIcon
    }

    override fun getTitle(): ITabView.TabTitle? {
        return mTabTitle
    }

    @Deprecated("")
    override fun getIconView(): ImageView? {
        return null
    }

    override fun getTitleView(): TextView? {
        return mTitle
    }

    override fun getBadgeView(): Badge? {
        return mBadgeView
    }

    override fun setBackground(background: Drawable?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            super.setBackground(background)
        } else {
            super.setBackgroundDrawable(background)
        }
    }

    override fun setBackgroundResource(resid: Int) {
        setBackground(resid)
    }

    private fun setDefaultBackground() {
        if (background !== mDefaultBackground) {
            background = mDefaultBackground
        }
    }

    override fun setChecked(checked: Boolean) {
        mChecked = checked
        isSelected = checked
        refreshDrawableState()
        mTitle?.setTextColor(if (checked) mTabTitle?.colorSelected!! else mTabTitle?.colorNormal!!)
        initIconView()
    }

    override fun isChecked(): Boolean {
        return mChecked
    }

    override fun toggle() {
        isChecked = !mChecked
    }
}