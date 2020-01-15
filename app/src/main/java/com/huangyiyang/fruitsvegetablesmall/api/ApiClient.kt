package com.huangyiyang.fruitsvegetablesmall.api

import android.content.Context
import com.huangyiyang.fruitsvegetablesmall.util.FrameConstUtil
import com.huangyiyang.fruitsvegetablesmall.util.NetWorkUtil
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient {
    //        public static final String BASE_URL = "http://192.168.1.129:8081";
//    public static final String BASE_URL = "http://39.108.80.101:8081";
//    public static final String BASE_URL = "http://192.168.1.115:8080";
//    public static final String BASE_URL = "http://192.168.1.190:8080";
//    public static final String BASE_URL = "http://192.168.1.141:8080";
//    public static final String BASE_URL = "http://192.168.1.118:8080";
//    public static final String BASE_URL = "http://192.168.1.164:8080";
//    public static final String BASE_URL = "http://192.168.1.168:8080";
//    public static final String BASE_URL = "http://api.yahao.ren:8080";
//    public static final String BASE_URL = "http://112.126.72.82:8080";


    //        public static final String BASE_URL = "http://192.168.1.129:8081";
//    public static final String BASE_URL = "http://39.108.80.101:8081";
//    public static final String BASE_URL = "http://192.168.1.115:8080";
//    public static final String BASE_URL = "http://192.168.1.190:8080";
//    public static final String BASE_URL = "http://192.168.1.141:8080";
//    public static final String BASE_URL = "http://192.168.1.118:8080";
//    public static final String BASE_URL = "http://192.168.1.164:8080";
//    public static final String BASE_URL = "http://192.168.1.168:8080";
//    public static final String BASE_URL = "http://api.yahao.ren:8080";
//    public static final String BASE_URL = "http://112.126.72.82:8080";
    private val READ_TIME_OUT = 45

    private val WRITE_TIME_OUT = 45

    private val CONNECT_TIME_OUT = 15

    private val CACHE_STALE_SEC = 60 * 60 * 24 * 2.toLong()

    val CACHE_CONTROL_CACHE = "only-if-cached, max-stale=$CACHE_STALE_SEC"

    val CACHE_CONTROL_AGE = "max-age=5"

    private val HTTP_LOG = "YGF"

    @Volatile
    private var sInstance: ApiClient? = null

    private var mRetrofit: Retrofit? = null

    fun getInstance(baseUrl: String): ApiClient? {
        if (sInstance == null) {
            synchronized(ApiClient::class.java) {
                if (sInstance == null) {
                    sInstance = ApiClient(baseUrl)
                }
            }
        }
        return sInstance
    }

    fun <T> createApi(clazz: Class<T>?): T {
        return mRetrofit!!.create(clazz)
    }

    private fun ApiClient(baseUrl: String) : ApiClient?{
//        val cookieJar = PersistentCookieJar(
//            SetCookieCache(),
//            SharedPrefsCookiePersistor(FrameConst.getContext())
//        )
        val client = OkHttpClient.Builder()
            .retryOnConnectionFailure(true)
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(45, TimeUnit.SECONDS)
            .writeTimeout(55, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            //.addInterceptor(LoggerInterceptor(HTTP_LOG, true))
            //.cookieJar(cookieJar)
            .build()
        mRetrofit = Retrofit.Builder()
            .client(client)
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .build()
        return sInstance
    }

    //根据网络设置离线、在线缓存Header
    fun getCacheControl(): String? {
        return if (NetWorkUtil().isNetConnected(FrameConstUtil().getContext() as Context)) CACHE_CONTROL_AGE else CACHE_CONTROL_CACHE
    }


}