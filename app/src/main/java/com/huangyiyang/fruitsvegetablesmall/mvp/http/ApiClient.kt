package com.huangyiyang.fruitsvegetablesmall.mvp.http

import android.content.Context
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.huangyiyang.fruitsvegetablesmall.mvp.util.NetWorkUtil
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

open class ApiClient {
    companion object {
        //        public static final String BASE_URL = "http://192.168.1.129:8081";

        private val READ_TIME_OUT = 45

        private val WRITE_TIME_OUT = 45

        private val CONNECT_TIME_OUT = 15

        private val CACHE_STALE_SEC = 60 * 60 * 24 * 2.toLong()

        val CACHE_CONTROL_CACHE = "only-if-cached, max-stale=$CACHE_STALE_SEC"

        val CACHE_CONTROL_AGE = "max-age=5"

        private val HTTP_LOG = "FVM"

        @Volatile
        private var sInstance: ApiClient? = null

        fun getInstance(baseUrl: String): ApiClient? {
            if (sInstance == null) {
                synchronized(ApiClient::class.java) {
                    if (sInstance == null) {
                        sInstance =
                            ApiClient(
                                baseUrl
                            )
                    }
                }
            }
            return sInstance
        }

        //根据网络设置离线、在线缓存Header
        fun getCacheControl(): String? {
            return if (NetWorkUtil.isNetConnected(FrameConst.getContext() as Context)) CACHE_CONTROL_AGE else CACHE_CONTROL_CACHE
        }

    }
    private var mRetrofit: Retrofit? = null

    fun <T> createApi(clazz: Class<T>): T {
        return mRetrofit!!.create(clazz)
    }

    constructor(baseUrl: String):super(){
        val cookieJar = PersistentCookieJar(
            SetCookieCache(),
            SharedPrefsCookiePersistor(FrameConst.getContext())
        )

        val client = OkHttpClient.Builder()
            .retryOnConnectionFailure(true)
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(45, TimeUnit.SECONDS)
            .writeTimeout(55, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .addInterceptor(
                LoggerInterceptor(
                    HTTP_LOG,
                    true
                )
            )
            .cookieJar(cookieJar)
            .build()

        mRetrofit = Retrofit.Builder()
            .client(client)
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .build()

    }

}