package com.huangyiyang.fruitsvegetablesmall.api

import android.os.Build
import com.huangyiyang.fruitsvegetablesmall.BuildConfig
import java.util.*

class Const {
    companion object {
        const val verType = 1 //0是测试1是正式

        //    public static final String BASE_URL = "http://api.client.ygfmlt.infoloop.cn"; //正式更改之后
        val BASE_URL =
            if (verType == 0) "http://localhost:8080/" else "http://localhost:8080/"
        const val APP_KEY = "MOXUOLSRGKNT1HRSSHBJ8EQSUWTZO1Z5"

        fun header(): Map<String?, String?>? {
            val header: MutableMap<String?, String?> =
                HashMap()
//            header["auth-" + UserManager.getInstance().getUserId()] =
//                UserManager.getInstance().getUserToken()
//            header["User-Agent"] =
//                Build.BRAND + " " + Build.MODEL + " " + Build.VERSION.RELEASE + " " + Build.VERSION.SDK_INT + " " + BuildConfig.VERSION_NAME
            return header
        }
    }
}