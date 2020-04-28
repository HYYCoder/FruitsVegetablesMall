package com.huangyiyang.fruitsvegetablesmall.mvp.http

data class ApiResult<T>(
    var code: String,
    var message: String,
    var data: T
){
    fun isOk(): Boolean {
        return code == "0"
    }
}