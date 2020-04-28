package com.huangyiyang.fruitsvegetablesmall

import com.huangyiyang.fruitsvegetablesmall.mvp.manage.UserManager
import java.util.*

class Const {
    companion object {
        val BASE_URL = "http://10.0.2.2:8080/"

        //Const.IMAHE_URL添加前缀，相当于http://10.0.2.2:8080/images/+图片名,目前BannerUtil和ImageLoaderUtil用到
        val IMAHE_URL = BASE_URL+"images/"

        fun header(): Map<String, String>? {
            val header: MutableMap<String, String> =
                HashMap()
            header["Authorization"] = UserManager.getInstance()?.getUserToken()!!
//            header["User-Agent"] =
//                Build.BRAND + " " + Build.MODEL + " " + Build.VERSION.RELEASE + " " + Build.VERSION.SDK_INT + " " + BuildConfig.VERSION_NAME
            return header
        }
    }
}