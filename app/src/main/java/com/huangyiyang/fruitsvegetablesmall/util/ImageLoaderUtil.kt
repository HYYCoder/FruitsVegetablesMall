package com.huangyiyang.fruitsvegetablesmall.util

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

class ImageLoaderUtil {
    companion object {
        private var sInstance: ImageLoaderUtil? = null
        private var mPlaceholder: Drawable? = null

        fun getInstance(): ImageLoaderUtil? {
            if (sInstance == null) {
                sInstance = ImageLoaderUtil()
            }
            return sInstance
        }

        fun init(placeholder: Drawable?) {
            mPlaceholder = placeholder
            if (mPlaceholder == null) {
                throw NullPointerException("ImageLoader需要初始化")
            }
        }
    }

    constructor()



    // view不能设置tag
    fun load(view: ImageView, uri: String?) {
        if (uri == null) return
        //        L.e(uri);
        Glide.with(view.context)
            .load(uri)
            .apply(
                RequestOptions()
                    .centerCrop()
                    .dontAnimate()
                    .placeholder(mPlaceholder)
                    .diskCacheStrategy(DiskCacheStrategy.DATA)
            )
            .into(view)
    }

    // view不能设置tag
    fun loadWithOutCenterCrop(
        view: ImageView,
        uri: String?
    ) {
        if (uri == null) return
        //        L.e(uri);
        Glide.with(view.context)
            .load(uri)
            .apply(
                RequestOptions()
                    .dontAnimate()
                    .placeholder(mPlaceholder)
                    .diskCacheStrategy(DiskCacheStrategy.DATA)
            )
            .into(view)
    }


    fun loadUserBigAvatar(view: ImageView, uri: String?) {
        if (uri == null) return
        Glide.with(view.context)
            .load(uri)
            .apply(
                RequestOptions()
                    .centerCrop()
                    .dontAnimate()
                    .placeholder(mPlaceholder)
            )
            .into(view)
    }

    fun loadShopBanner(view: ImageView, uri: String?) {
        if (uri == null) return
        Glide.with(view.context)
            .load(uri)
            .apply(
                RequestOptions()
                    .centerCrop()
                    .dontAnimate()
                    .placeholder(mPlaceholder)
            )
            .into(view)
    }

    fun loadFitCenter(view: ImageView, uri: String?) {
        if (uri == null) return
        Glide.with(view.context)
            .load(uri)
            .apply(
                RequestOptions()
                    .fitCenter()
                    .dontAnimate()
                    .placeholder(mPlaceholder)
                    .diskCacheStrategy(DiskCacheStrategy.DATA)
            )
            .into(view)
    }

    fun clearMemoryCache(ctx: Context?) { // 必须在主线程上调用此方法
        Glide.get(ctx!!).clearMemory()
    }
}