package com.huangyiyang.fruitsvegetablesmall.mvp.util

import android.content.Context
import android.net.ConnectivityManager

@Suppress("DEPRECATION")
class NetWorkUtil {
    /**
     * 判断设备有没有网络
     */
    companion object {
        fun isNetConnected(context: Context): Boolean {
            val localNetworkInfo =
                (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo
            return localNetworkInfo != null && localNetworkInfo.isAvailable
        }
    }
}