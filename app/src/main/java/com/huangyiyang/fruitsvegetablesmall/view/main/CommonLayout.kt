package com.huangyiyang.fruitsvegetablesmall.view.main

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewStub
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat

import com.huangyiyang.fruitsvegetablesmall.R

// 内容布局ID必须为common_content
class CommonLayout : FrameLayout {

    private var mLoadingStub: ViewStub? = null
    private var mLoadingView: View? = null
    private var mErrorStub: ViewStub? = null
    private var mErrorView: View? = null
    private var mEmptyStub: ViewStub? = null
    private var mEmptyView: View? = null
    private var mContentView: View? = null
    private var mOnErrorClickListener: OnClickListener? = null
    private var mEmptyImage: ImageView? = null
    private var mProgressBar: ProgressBar? = null
    private var mErrorTv: TextView? = null
    private var mEmptyImageId: Int = R.drawable.icon_empty_view
    private var mLoadingStyleId: Int = R.drawable.loading_dialog_progressbar

    fun setResources(eId: Int, lId: Int) {
        if (eId != 0) {
            mEmptyImageId = eId
        }
        if (lId != 0) {
            mLoadingStyleId = lId
        }
    }

    fun getLoadingId(): Int {
        return mLoadingStyleId
    }

    fun create(context: Context?, layoutId: Int): CommonLayout? {
        val layout = LayoutInflater.from(context).inflate(
           R.layout.common_layout,
            null
        ) as CommonLayout
        val view = LayoutInflater.from(context).inflate(layoutId, layout, false)
        layout.addView(view)
        layout.setContentView(view)
        return layout
    }

    fun create(context: Context?, view: View?): CommonLayout? {
        val layout = LayoutInflater.from(context).inflate(
            R.layout.common_layout,
            null
        ) as CommonLayout
        layout.addView(view)
        layout.setContentView(view)
        return layout
    }

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(
        context: Context,
        attrs: AttributeSet?
    ):super(context,attrs) {
        init()
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ):super(context,attrs,defStyleAttr) {
        init()
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ):this(context) {

    }

    fun setContentView(view: View?) {
        mContentView = view
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        if (mContentView == null) {
            val viewId = context.resources
                .getIdentifier("common_content", "id", context.packageName)
            mContentView = findViewById(viewId)
        }
    }

    private fun init() {
        if (mLoadingStub == null) {
            mLoadingStub = ViewStub(context, R.layout.common_loading)
            mErrorStub = ViewStub(context, R.layout.common_error)
            mEmptyStub = ViewStub(context, R.layout.common_empty)
            addView(mLoadingStub)
            addView(mErrorStub)
            addView(mEmptyStub)
        }
    }

    fun showLoading() {
        mContentView!!.visibility = View.GONE
        setErrorViewVisible(View.GONE)
        setEmptyViewVisible(View.GONE)
        setLoadingViewVisible(View.VISIBLE)
    }

    fun showEmpty() {
        mContentView!!.visibility = View.GONE
        setErrorViewVisible(View.GONE)
        setEmptyViewVisible(View.VISIBLE)
        setLoadingViewVisible(View.GONE)
    }

    fun cancelShow() {
        setErrorViewVisible(View.GONE)
        setLoadingViewVisible(View.GONE)
    }

    fun showError() {
        mContentView!!.visibility = View.GONE
        setErrorViewVisible(View.VISIBLE)
        setEmptyViewVisible(View.GONE)
        setLoadingViewVisible(View.GONE)
    }

    fun showError(errorText: Int) {
        mContentView!!.visibility = View.GONE
        setErrorViewVisible(View.VISIBLE)
        setEmptyViewVisible(View.GONE)
        setLoadingViewVisible(View.GONE)
        mErrorTv!!.setText(errorText)
    }

    fun showError(errorText: String?) {
        mContentView!!.visibility = View.GONE
        setErrorViewVisible(View.VISIBLE)
        setEmptyViewVisible(View.GONE)
        setLoadingViewVisible(View.GONE)
        mErrorTv!!.text = errorText
    }

    fun showContent() {
        mContentView!!.visibility = View.VISIBLE
        setErrorViewVisible(View.GONE)
        setEmptyViewVisible(View.GONE)
        setLoadingViewVisible(View.GONE)
    }

    private fun setEmptyViewVisible(visible: Int) {
        if (mEmptyView != null) {
            mEmptyView!!.visibility = visible
        } else if (visible == View.VISIBLE) {
            mEmptyView = mEmptyStub!!.inflate()
            mEmptyImage = mEmptyView?.findViewById(R.id.iv_empty)
            mEmptyImage?.setImageResource(mEmptyImageId)
            mEmptyView?.setVisibility(View.VISIBLE)
        }
    }

    private fun setErrorViewVisible(visible: Int) {
        if (mErrorView != null) {
            mErrorView!!.visibility = visible
        } else if (visible == View.VISIBLE) {
            mErrorView = mErrorStub!!.inflate()
            mErrorView?.setVisibility(View.VISIBLE)
            mErrorTv =
                mErrorView?.findViewById<View>(R.id.tv_common_error) as TextView
            mErrorTv!!.setOnClickListener(mOnErrorClickListener)
        }
    }

    private fun setLoadingViewVisible(visible: Int) {
        if (mLoadingView != null) {
            mLoadingView!!.visibility = visible
        } else if (visible == View.VISIBLE) {
            mLoadingView = mLoadingStub!!.inflate()
            mProgressBar = mLoadingView?.findViewById(R.id.progress_Bar)
            mProgressBar?.setIndeterminateDrawable(
                ContextCompat.getDrawable(
                    context,
                    mLoadingStyleId
                )
            )
            mLoadingView?.setVisibility(View.VISIBLE)
        }
    }

    fun setOnErrorClickListener(listener: OnClickListener?) {
        mOnErrorClickListener = listener
    }

    fun setEmptyImage(imageId: Int) {
        mEmptyImage!!.setImageResource(imageId)
    }

}