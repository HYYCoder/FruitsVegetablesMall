package com.huangyiyang.fruitsvegetablesmall.mvp.http

import android.content.Context
import com.huangyiyang.fruitsvegetablesmall.mvp.util.ToastUtil

open class ServerException : Exception{
    val ERROR_NO_DATA = "400"
    val ERROR_EXCEPTION = "401"

    var mErrorCode: String? = null
    var mErrorMsg: String? = null

    constructor()

    constructor(code: String?, message: String?): super(message) {
        mErrorCode = code
        mErrorMsg = message
    }

    fun ToastError(context: Context) {
        if (null != mErrorMsg) {
            ToastUtil.showShort(context, mErrorMsg!!)
        }
    }
}