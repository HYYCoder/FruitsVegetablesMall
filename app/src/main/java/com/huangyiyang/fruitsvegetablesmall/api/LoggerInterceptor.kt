package com.huangyiyang.fruitsvegetablesmall.api

import android.text.TextUtils
import android.util.Log
import okhttp3.*
import okio.Buffer
import java.io.IOException

class LoggerInterceptor : Interceptor{

    companion object{
        val TAG = "HttpLog"
        private var errorCode: String? = null

        fun errorCodeCallback(): String? {
            return if (errorCode == null) "" else errorCode!!.split(",").toTypedArray()[0].split(":")
                .toTypedArray()[1]
        }
    }
    private var tag: String? = null
    private var showResponse = false

    constructor(tag: String?, showResponse: Boolean){
        var tag = tag
        if (TextUtils.isEmpty(tag)) {
            tag = TAG
        }
        this.showResponse = showResponse
        this.tag = tag
    }

    constructor(tag: String?):this(tag, false)

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response? {
        val request = chain.request()
        logForRequest(request)
        val response = chain.proceed(request)
        return logForResponse(response)
    }

    private fun logForResponse(response: Response): Response? {
        try { //===>response log
            Log.e(tag, "========response'log=======")
            val builder = response.newBuilder()
            val clone = builder.build()
            Log.e(tag, "url : " + clone.request().url())
            Log.e(tag, "code : " + clone.code())
            Log.e(tag, "protocol : " + clone.protocol())
            if (!TextUtils.isEmpty(clone.message())) Log.e(
                tag,
                "message : " + clone.message()
            )
            if (showResponse) {
                var body = clone.body()
                if (body != null) {
                    val mediaType = body.contentType()
                    if (mediaType != null) {
                        Log.e(
                            tag,
                            "responseBody's contentType : $mediaType"
                        )
                        if (isText(mediaType)) {
                            val resp = body.string()
                            Log.e(tag, "responseBody's content : $resp")
                            body = ResponseBody.create(mediaType, resp)
                            errorCode = resp
                            return response.newBuilder().body(body).build()
                        } else {
                            Log.e(
                                tag,
                                "responseBody's content : " + " maybe [file part] , too large too print , ignored!"
                            )
                        }
                    }
                }
            }
            Log.e(tag, "========response'log=======end")
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return response
    }

    private fun logForRequest(request: Request) {
        try {
            val url = request.url().toString()
            val headers = request.headers()
            Log.e(tag, "========request'log=======")
            Log.e(tag, "method : " + request.method())
            Log.e(tag, "url : $url")
            if (headers != null && headers.size() > 0) {
                Log.e(tag, "headers : $headers")
            }
            val requestBody = request.body()
            if (requestBody != null) {
                val mediaType = requestBody.contentType()
                if (mediaType != null) {
                    Log.e(tag, "requestBody's contentType : $mediaType")
                    //                    if (isText(mediaType))
//                    {
                    Log.e(tag, "requestBody's content : " + bodyToString(request))
                    //                    } else
//                    {
//                        Log.e(tag, "requestBody's content : " + " maybe [file part] , too large too print , ignored!");
//                    }
                }
            }
            Log.e(tag, "========request'log=======end")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun isText(mediaType: MediaType): Boolean {
        if (mediaType.type() != null && mediaType.type() == "text") {
            return true
        }
        if (mediaType.subtype() != null) {
            if (mediaType.subtype() == "json" || mediaType.subtype() == "xml" || mediaType.subtype() == "html" || mediaType.subtype() == "webviewhtml") return true
        }
        return false
    }

    private fun bodyToString(request: Request): String {
        return try {
            val copy = request.newBuilder().build()
            val buffer = Buffer()
            copy.body()!!.writeTo(buffer)
            buffer.readUtf8()
        } catch (e: IOException) {
            "something error when show requestBody."
        }
    }

}