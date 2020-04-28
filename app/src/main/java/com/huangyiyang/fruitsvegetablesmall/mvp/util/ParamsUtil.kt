package com.huangyiyang.fruitsvegetablesmall.mvp.util

import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject

class ParamsUtil {

    constructor()

    companion object {

        @Volatile
        private var sInstance: ParamsUtil? = null

        fun getInstance(): ParamsUtil? {
            if (sInstance == null) {
                val var0: Class<*> = ParamsUtil::class.java
                synchronized(ParamsUtil::class.java) {
                    if (sInstance == null) {
                        sInstance =
                            ParamsUtil()
                    }
                }
            }
            return sInstance
        }
    }

    fun getBody(param: Map<String, String>?): RequestBody? {
        return RequestBody.create(
            MediaType.parse("application/json; charset=utf-8"),
            GsonUtil.GsonString(param)
        )
    }

    fun getBodyInteger(param: Map<String, Int>?): RequestBody? {
        return RequestBody.create(
            MediaType.parse("application/json; charset=utf-8"),
            GsonUtil.GsonString(param)
        )
    }

    fun getBodyNumber(param: Map<String, Number>?): RequestBody? {
        return RequestBody.create(
            MediaType.parse("application/json; charset=utf-8"),
            GsonUtil.GsonString(param)
        )
    }

    fun getBodyIntegerList(param: Map<String, List<Int>>?): RequestBody? {
        return RequestBody.create(
            MediaType.parse("application/json; charset=utf-8"),
            GsonUtil.GsonString(param)
        )
    }

    fun getBodyObj(param: Map<String, Any>?): RequestBody? {
        return RequestBody.create(
            MediaType.parse("application/json; charset=utf-8"),
            GsonUtil.GsonString(param)
        )
    }

    fun getBodyJsonObj(jsonObject: JSONObject): RequestBody? {
        return RequestBody.create(
            MediaType.parse("application/json; charset=utf-8"),
            jsonObject.toString()
        )
    }
}