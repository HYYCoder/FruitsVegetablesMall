package com.huangyiyang.fruitsvegetablesmall.util

import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.ImageView
import com.huangyiyang.fruitsvegetablesmall.R
import com.youth.banner.Banner
import com.youth.banner.BannerConfig
import com.youth.banner.listener.OnBannerListener
import com.youth.banner.loader.ImageLoader
import com.bumptech.glide.Glide
import com.huangyiyang.fruitsvegetablesmall.api.Const

import java.util.*


class BannerUtil {

    private val DELAY_TIME = 3000 // 3s

    private var mContentView: View? = null
    private var banner: Banner? = null

    private var mBanners: List<DataBean>? = null
    private var mOnLoadFinish: OnLoadFinish? = null

    constructor(view: View?) {
        initViews(view)
        initPlayAnimations()
        //        initBannerHeight();
    }

    constructor(view: View, measure: IntArray) {
        initViews(view)
        initPlayAnimations()
        initBannerHeight(measure)
    }

    constructor(activity: Activity) {
        initViews(activity)
        initPlayAnimations()
        //        initBannerHeight();
    }

    constructor(activity: Activity, measure: IntArray) {
        initViews(activity)
        initPlayAnimations()
        initBannerHeight(measure)
    }

    // 初始化banner播放动画
    private fun initPlayAnimations() { //        banner.setBannerAnimation(CubeOutTransformer.class);
    }

    private fun initBannerHeight() {
        val measure = getBannerSize()
        //mFlipper.getLayoutParams().height = measure[1];
        mContentView!!.layoutParams.height = measure!![1]
        //        int height = f.getHeight();
        mContentView!!.requestLayout()
    }

    private fun initBannerHeight(measure: IntArray) { //mFlipper.getLayoutParams().height = measure[1];
        mContentView!!.layoutParams.height = measure[1]
        //        int height = f.getHeight();
        mContentView!!.requestLayout()
    }

    protected fun getBannerSize(): IntArray? {
        return ImageMeasureUtil.getMainHomeBannerMeasure()
    }


    // 初始化banner views
    private fun initViews(view: View?) { //        mContentView = view.findViewById(com.wangxing.code.R.id.fl_home_banner);
        banner = view?.findViewById<View>(R.id.banner) as Banner
    }

    // 初始化banner views
    private fun initViews(activity: Activity) {
        mContentView =
            activity.findViewById(R.id.fl_home_banner)
        banner = activity.findViewById<View>(R.id.banner) as Banner
    }

    fun setBanner(banners: List<DataBean>?) {
        mBanners = banners
        val images: MutableList<String?> =
            ArrayList()
        if (!(mBanners == null || mBanners!!.isEmpty())) {
            if (mContentView != null) mContentView!!.visibility = View.VISIBLE
            for (i in mBanners!!.indices) {
                images.add(mBanners!![i].imageUrl)
            }
            banner!!.setImages(images)
                .setDelayTime(DELAY_TIME)
                .setImageLoader(GlideImageLoader())
                .setIndicatorGravity(BannerConfig.RIGHT)
                .start()
            if (banner != null) {
                banner!!.visibility = View.VISIBLE
            }
        } else {
            if (mContentView != null) mContentView!!.visibility = View.GONE
            if (banner != null) {
                banner!!.visibility = View.INVISIBLE
            }
        }
        if (mOnLoadFinish != null) {
            mOnLoadFinish!!.onLoadFinish()
        }
    }

    fun setOnLoadFinish(finish: OnLoadFinish?) {
        mOnLoadFinish = finish
    }

    fun setBannerHeight(bannerSize: IntArray) {
        mContentView!!.layoutParams.height = bannerSize[1]
        mContentView!!.requestLayout()
    }

    fun setBannerClickable(listener: OnBannerListener?) {
        banner?.setOnBannerListener(listener)
    }

    interface OnLoadFinish {
        fun onLoadFinish()
    }


    class DataBean(var id: String,var orders: String,var imageUrl: String,var detail: String)

    class GlideImageLoader : ImageLoader() {
        override fun displayImage(context: Context?, path: Any?, imageView: ImageView?) {
            //具体方法内容自己去选择，次方法是为了减少banner过多的依赖第三方包，所以将这个权限开放给使用者去选择
            Glide.with(context!!.applicationContext)
                .load(Const.IMAHE_URL + path) //Const.IMAHE_URL添加前缀，相当于http://10.0.2.2:8080/images/+图片名
                .into(imageView!!)
        }

    }

}