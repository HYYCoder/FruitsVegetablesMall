package com.huangyiyang.fruitsvegetablesmall.api

import com.google.gson.annotations.SerializedName

data class ApiResult<T>(
    var code: String,
    var message: String,
    var data: T
){
    fun isOk(): Boolean {
        return code == "0"
    }
}