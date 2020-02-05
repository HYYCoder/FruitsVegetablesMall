package com.huangyiyang.fruitsvegetablesmall.api

import java.util.*

class Const {
    companion object {
        const val verType = 0 //0是测试1是正式
        val BASE_URL =
            if (verType == 0) "http://10.0.2.2:8080/" else "http://10.0.2.2:8080/"

        val IMAHE_URL ="D:/Scripts/Project/MyProject/FruitsVegetablesMallServer/target/classes/static/images/goods/"

        fun header(): Map<String, String>? {
            val header: MutableMap<String, String> =
                HashMap()
//            header["auth-" + UserManager.getInstance().getUserId()] =
//                UserManager.getInstance().getUserToken()
//            header["User-Agent"] =
//                Build.BRAND + " " + Build.MODEL + " " + Build.VERSION.RELEASE + " " + Build.VERSION.SDK_INT + " " + BuildConfig.VERSION_NAME
            return header
        }
    }
}