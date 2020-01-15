package com.huangyiyang.fruitsvegetablesmall.api

import com.google.gson.annotations.SerializedName

class ApiResult<T> {
    @SerializedName("code")
    var code: String? = null

    @SerializedName("message")
    var message: String? = null

    @SerializedName("data")
    var data: T? = null

    fun isOk(): Boolean {
        return code == "0"
    }
}