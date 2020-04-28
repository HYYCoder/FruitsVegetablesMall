package com.huangyiyang.fruitsvegetablesmall.mvp.util

import android.content.Context

class ImageMeasureUtil {

    companion object {
        // banner
        val BANNER_RATIO = 72 / 35f
        //首页  banner
        val MAIN_BANNER_SIZE = 750 / 468f
        //商品详情 banner
        val PRODUCT_DETAIL_BANNER_SIZE = 750 / 700f


        private var sScreenWidth = 0

        private var dp24 = 0

        // 视频宽高缩小倍数
        val VIDEO_NARROW = 3

        fun init(ctx: Context) {
            val resources = ctx.resources
            sScreenWidth = resources.displayMetrics.widthPixels
            dp24 = ValueUtil.dpToPx(ctx, 24)
        }

        fun getBannerMeasure(): IntArray? {
            val measure = IntArray(2)
            measure[0] = sScreenWidth
            measure[1] = Math.round(sScreenWidth / BANNER_RATIO)
            return measure
        }

        fun getMainHomeBannerMeasure(): IntArray? {
            val measure = IntArray(2)
            measure[0] = sScreenWidth
            measure[1] = Math.round(sScreenWidth / MAIN_BANNER_SIZE)
            return measure
        }

        fun getProductDetailBannerMeasure(): IntArray? {
            val measure = IntArray(2)
            measure[0] = sScreenWidth
            measure[1] = Math.round(sScreenWidth / PRODUCT_DETAIL_BANNER_SIZE)
            return measure
        }

        fun getVideoMeasure(ratio: Float): IntArray? {
            val measure = IntArray(2)
            measure[0] = sScreenWidth
            measure[1] = Math.round((sScreenWidth - dp24) / ratio)
            return measure
        }


        /**
         * 获取视频宽高缩放
         */
        fun getVideosWHRatio(video: Int): Int {
            return Math.round(video / VIDEO_NARROW.toFloat())
        }

        fun getHeight(width: Int, radio: Float): Int {
            return Math.round(width / radio)
        }
    }
}