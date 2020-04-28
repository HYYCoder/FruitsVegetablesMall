package com.huangyiyang.fruitsvegetablesmall.mvp.http

import android.annotation.SuppressLint
import android.app.Application
import com.huangyiyang.fruitsvegetablesmall.mvp.util.ImageMeasureUtil

class FrameConst {
    companion object {
        @SuppressLint("StaticFieldLeak")
        private var sApp: Application? = null
        private var instance: ApiClient? = null

        fun getContext(): Application? {
            if (sApp == null) {
                throw NullPointerException("Const需要初始化")
            }
            return sApp
        }

        fun init(application: Application, baseUrl: String) {
            sApp = application
            ImageMeasureUtil.init(application)
            instance = ApiClient.getInstance(baseUrl)
//        val builder: L.Builder = Builder()
//        L.set(
//            builder.addLogCat().addLocalLog(application.applicationContext).logCrash(
//                application.applicationContext
//            ).create()
//        )
        }

        fun getInstance(): ApiClient? {
            return instance
        }

        fun <T> apiService(clz: Class<T>): T {
            return getInstance()!!.createApi(clz)
        }

        val PAGE_SIZE = 10

        /**
         * OSS
         */
//    public static final String OSS_TST = BASE_URL + "QianGuizhou/foreground/getTst";
        val OSS_TST = ""
        val OSS_END_POINT = "http://oss-cn-shenzhen.aliyuncs.com"

        val OSS_BUCKET = "qianshijie"

        val OSS_ROOM_IMAGE_CALLBACK =
            "http://neufmer.iok.la/QianGuizhou/foreground/rRoomCategoryImgCallback"

        /**
         * 用户信息
         */
        val USER_ID = "userId"

        val USER_NAME = "userName"

        val USER_LEVEL = "userLevel"

        val USER_TOKEN = "userToken"

        val USER_STATUS = "userStatus"

        /**
         * 经纬度
         */
        val LONGITUDE = "longitude"

        val LATITUDE = "latitude"

        val CITY_NAME = "city"
    }
}