package com.huangyiyang.fruitsvegetablesmall.view.shoppingCar

import android.app.Activity
import android.content.Context
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.huangyiyang.fruitsvegetablesmall.R

class ToolbarUtil : View.OnClickListener{

    private var mToolbarView: View? = null
    private var mLeftTv: TextView? = null
    private var mLeftIv: ImageView? = null
    private var mTitleTv: TextView? = null
    private var mRightTv: TextView? = null
    private var mRightIv: ImageView? = null
    private var mLeftFl: FrameLayout? = null
    private var mRightFl: FrameLayout? = null
    private var mLine: TextView? = null
    private var mActivity: Activity? = null
    private var mContext : Context? = null
    var LEFT = 0
    var TOP = 1
    var RIGHT = 2
    var BOTTOM = 3

    constructor(activity: Activity?,context : Context?){
        mActivity = activity
        mContext = context
        init(activity)
    }

    constructor(parentView: View,context : Context?) {
        mActivity = parentView.context as Activity
        mContext = context
        init(parentView)
    }

    private fun init(activity: Activity?) {
        mToolbarView =
            mActivity!!.findViewById(R.id.frame_toolbar)
        mLeftTv =
            mToolbarView?.findViewById<View>(R.id.tv_toolbar_left) as TextView
        mLeftIv =
            mToolbarView?.findViewById<View>(R.id.ib_toolbar_left) as ImageView
        mLeftFl =
            mToolbarView?.findViewById<View>(R.id.fl_toolbar_left) as FrameLayout
        mTitleTv =
            mToolbarView?.findViewById<View>(R.id.tv_toolbar_title) as TextView
        mRightTv =
            mToolbarView?.findViewById<View>(R.id.tv_toolbar_right) as TextView
        mRightIv =
            mToolbarView?.findViewById<View>(R.id.ib_toolbar_right) as ImageView
        mRightFl =
            mToolbarView?.findViewById<View>(R.id.fl_toolbar_right) as FrameLayout
        mLine =
            mToolbarView?.findViewById<View>(R.id.line) as TextView
        mLeftFl!!.setOnClickListener(this)
    }

    private fun init(parentView: View) {
        mToolbarView =
            parentView.findViewById(R.id.frame_toolbar)
        mLeftTv =
            mToolbarView?.findViewById<View>(R.id.tv_toolbar_left) as TextView
        mLeftIv =
            mToolbarView?.findViewById<View>(R.id.ib_toolbar_left) as ImageView
        mLeftFl =
            mToolbarView?.findViewById<View>(R.id.fl_toolbar_left) as FrameLayout
        mTitleTv =
            mToolbarView?.findViewById<View>(R.id.tv_toolbar_title) as TextView
        mRightTv =
            mToolbarView?.findViewById<View>(R.id.tv_toolbar_right) as TextView
        mRightIv =
            mToolbarView?.findViewById<View>(R.id.ib_toolbar_right) as ImageView
        mRightFl =
            mToolbarView?.findViewById<View>(R.id.fl_toolbar_right) as FrameLayout
        mLine =
            mToolbarView?.findViewById<View>(R.id.line) as TextView
        mLeftFl!!.setOnClickListener(this)
    }

    fun setToolbarVisibility(visibility: Int) {
        mToolbarView!!.visibility = visibility
    }

    override fun onClick(v: View) {
        val id = v.id
        if (id == R.id.fl_toolbar_left) {
            if (mActivity != null) {
                mActivity!!.onBackPressed()
            }
        }
    }

    fun setBackgroundResource(resId: Int) {
        mToolbarView!!.setBackgroundResource(resId)
    }

    fun setBackgroundColor(color: Int) {
        mToolbarView!!.setBackgroundColor(color)
    }

    fun setTitle(resId: Int) {
        mTitleTv!!.setText(resId)
    }

    fun setTitleLeft() {
        mTitleTv!!.gravity = Gravity.LEFT or Gravity.CENTER_VERTICAL
    }

    fun setTitle(title: String?) {
        mTitleTv!!.text = title
    }

    fun setLeftText(text: String?) {
        mLeftTv!!.text = text
    }

    fun setRightText(text: String?) {
        mRightTv!!.text = text
        mRightTv!!.visibility = View.VISIBLE
        mRightIv!!.visibility = View.INVISIBLE
    }

    fun setLeftText(resId: Int) {
        mLeftTv!!.setText(resId)
        mLeftTv!!.visibility = View.VISIBLE
        mLeftIv!!.visibility = View.INVISIBLE
    }

    fun setLeftImage(resId: Int) {
        mLeftIv!!.setImageResource(resId)
        mLeftIv!!.visibility = View.VISIBLE
        mLeftTv!!.visibility = View.INVISIBLE
    }

    fun setLineBackgroundColor(color: Int) {
        mLine!!.setBackgroundColor(ContextCompat.getColor(mContext!!, color))
    }

    fun setRightText(resId: Int) {
        mRightTv!!.setText(resId)
        mRightTv!!.visibility = View.VISIBLE
        mRightIv!!.visibility = View.INVISIBLE
    }


    fun setRightTextCompoundDrawables(drawable: Drawable, directionType: Int) {
        drawable.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
        when (directionType) {
            0 -> mRightTv!!.setCompoundDrawables(drawable, null, null, null)
            1 -> mRightTv!!.setCompoundDrawables(null, drawable, null, null)
            2 -> mRightTv!!.setCompoundDrawables(null, null, drawable, null)
            3 -> mRightTv!!.setCompoundDrawables(null, null, null, drawable)
        }
    }

    fun setRightTextColor(resId: Int) {
        mRightTv!!.setTextColor(resId)
    }

    fun setRightImage(resId: Int) {
        mRightIv!!.setImageResource(resId)
        mRightIv!!.visibility = View.VISIBLE
        mRightTv!!.visibility = View.INVISIBLE
    }

    fun setOnLeftClickListener(listener: View.OnClickListener?) {
        mLeftFl!!.setOnClickListener(listener)
    }

    fun setOnRightClickListener(listener: View.OnClickListener?) {
        mRightFl!!.setOnClickListener(listener)
    }

    fun setLightBackTheme(resId: Int) {
        setLightBackTheme(mLeftFl!!.context.getString(resId))
    }

    fun setLightBackTheme(title: String?) {
        setTitle(title)
        setBackgroundColor(ContextCompat.getColor(mContext!!, R.color.white_ffffff))
        setLeftImage(R.drawable.icon_back)
    }

    fun setTitleColor(titleColor: Int) {
        mTitleTv!!.setTextColor(titleColor)
    }

    fun setLeftVisibility(visibility: Int) {
        mLeftIv!!.visibility = visibility
        mLeftFl!!.isEnabled = false
    }

    fun setTitleStyle(style: Int) {
        mTitleTv!!.typeface = Typeface.create(Typeface.DEFAULT, style)
    }

    fun setTitleStyle(family: Typeface?, style: Int) {
        mTitleTv!!.typeface = Typeface.create(family, style)
    }
}