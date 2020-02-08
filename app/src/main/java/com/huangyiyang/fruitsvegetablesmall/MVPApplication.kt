package com.huangyiyang.fruitsvegetablesmall

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Message
import android.os.Process
import android.provider.Settings
import android.text.TextUtils
import android.util.Log
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.huangyiyang.fruitsvegetablesmall.api.Const
import com.huangyiyang.fruitsvegetablesmall.api.FrameConst
import com.huangyiyang.fruitsvegetablesmall.manage.AppForegroundStateManager
import com.huangyiyang.fruitsvegetablesmall.ui.login.activity.LoginActivity
import com.huangyiyang.fruitsvegetablesmall.util.ContextUtils
import com.huangyiyang.fruitsvegetablesmall.util.ImageLoaderUtil
import com.youth.banner.loader.ImageLoader
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.*
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

class MVPApplication : AppForegroundStateManager.OnAppForegroundStateChangeListener,
    MultiDexApplication(){
    companion object {
        var hasReception = false
        var errorCode: MutableMap<String, String?>? = null
        var isDebug = true
        var ANDROID_ID: String? = null
        private var myApplication: MVPApplication? = null

        fun getHasReception(): Boolean? {
            return hasReception
        }

        fun checkLogin(context: Context): Boolean {
            val isLogin: Boolean = true
                //UserManager.checkUser(context)
            if (!isLogin) {
                val intent = Intent(context, LoginActivity::class.java)
                if (context !is Activity) {
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                context.startActivity(intent)
            }
            return isLogin
        }

        fun getToastContent(
            context: Context,
            code: String?,
            message: String?
        ): String? {
            if (errorCode == null) {
                errorCode = HashMap()
//                errorCode["100000"] = "用户不存在"
//                errorCode["100001"] = "密码错误"
//                errorCode["100002"] = "开屏广告不存在"
//                errorCode["100003"] = "项目点不存在"
//                errorCode["100004"] = "商品不存在"
//                errorCode["100005"] = "验证码错误"
//                errorCode["100006"] = "购物车商品不存在"
//                errorCode["100007"] = "订单不存在"
//                errorCode["100008"] = "手机号错误"
//                errorCode["100009"] = "品类不存在"
//                errorCode["100010"] = "手机号不存在"
//                errorCode["100011"] = "规格不存在"
//                errorCode["100012"] = "当前状态订单无法取消"
//                errorCode["100013"] = "商品库存不足无法下单"
//                errorCode["100014"] = "供货商不存在"
//                errorCode["100015"] = "拆单不存在"
//                errorCode["100016"] = "门店账号已停用"
//                errorCode["100017"] = "门店账号已被封"
//                errorCode["100018"] = "门店账号还未开启，无法订购常温物料"
//                errorCode["100019"] = "该状态无法确认收货 "
//                errorCode["100020"] = "商品下单数量超过最大下单量"
//                errorCode["100021"] = "商品下单量小于商品最小下单量"
//                errorCode["100022"] = "NC错误：$message"
//                errorCode["100023"] = "当前无法盘库，请稍后再试"
//                errorCode["100024"] = "营销活动不存在"
//                errorCode["100025"] = "存在商品没有价格"
//                errorCode["100026"] = "该手机号已被其他门店绑定"
//                errorCode["100027"] = "优惠活动已达最多使用次数"
//                errorCode["100028"] = "优惠活动已结束"
//                errorCode["100029"] = "兑换失败"
//                errorCode["200001"] = "有商品库存不足，下单失败"
//                errorCode["401000"] = "未经授权的请求"
//                errorCode["401001"] = "token过期"
//                errorCode["401002"] = "未认证用户"
//                errorCode["401003"] = "用户账号已禁用"
//                errorCode["403000"] = "未经身份验证的请求"
//                errorCode["403001"] = "未授权用户"
//                errorCode["405001"] = "不允许的支付方式"
//                errorCode["406000"] = "参数类型错误"
//                errorCode["406001"] = "请求参数缺失"
//                errorCode["406002"] = "未满足的参数条件"
//                errorCode["406003"] = "验证码过期"
//                errorCode["406004"] = "用户已绑定手机"
//                errorCode["406005"] = "文件过大"
//                errorCode["406007"] = "不存在接收公司"
//                errorCode["406008"] = "错误的活动类型"
//                errorCode["500000"] = "不一致的数据"
//                errorCode["530000"] = "OSS上传失败"
//                errorCode["999999"] = "严重错误"
            }
            return if (errorCode!!.containsKey(code)) {
                if (code == "500") { // UserManager.getInstance().clearCache();
                    //UserManager.getInstance().saveToken_EXPIRED("t")
                    val intent = Intent(context, LoginActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(intent)
                    Process.killProcess(Process.myPid())
                    LoginActivity.isL = true
                }
                errorCode!![code]
            } else {
                message
            }
        }

        fun handleSSLHandshake() {
            try {
                val trustAllCerts =
                    arrayOf<TrustManager>(object : X509TrustManager {
                        override fun getAcceptedIssuers(): Array<X509Certificate?> {
                            return arrayOfNulls(0)
                        }

                        override fun checkClientTrusted(
                            certs: Array<X509Certificate>,
                            authType: String
                        ) {
                        }

                        override fun checkServerTrusted(
                            certs: Array<X509Certificate>,
                            authType: String
                        ) {
                        }
                    })
                val sc = SSLContext.getInstance("TLS")
                // trustAllCerts信任所有的证书
                sc.init(null, trustAllCerts, SecureRandom())
                HttpsURLConnection.setDefaultSSLSocketFactory(sc.socketFactory)
                HttpsURLConnection.setDefaultHostnameVerifier { hostname, session -> true }
            } catch (ignored: Exception) {
            }
        }
    }
    var seq = 0
    private val BUHLY_KEY = "75fa58b574"
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        myApplication = this
        ANDROID_ID = Settings.System.getString(
            contentResolver,
            Settings.System.ANDROID_ID
        )
        ImageLoaderUtil.init(getDrawable(R.drawable.place_holder))
        //框架初始化
        FrameConst.init(this, Const.BASE_URL)
         //初始化工具类
        ContextUtils.init(this)
        //初始化ImageLoader
        //ImageLoader.init(ContextCompat.getDrawable(this, 0))
        //初始化CommonLayout  空图片  loading
//        CommonLayout.setResources(R.drawable.icon_empty_view, 0);
        //监听前后台
        AppForegroundStateManager.getInstance()?.addListener(this)
        //handleSSLHandshake();
//极光
//        JPushInterface.setDebugMode(false) // 设置开启日志,发布时请关闭日志
//        JPushInterface.init(this)
        //用ANDROID_ID作为别名设置
        setAlias()
    }


    private fun setAlias() {
        ANDROID_ID = Settings.System.getString(
            contentResolver,
            Settings.System.ANDROID_ID
        )
        // 调用 Handler 来异步设置别名
        mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, ANDROID_ID))
    }

    private val MSG_SET_ALIAS = 1001
    @SuppressLint("HandlerLeak")
    private val mHandler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {
                MSG_SET_ALIAS -> {
                    Log.d("TAG", "Set alias in handler.")
                    // 调用 JPush 接口来设置别名。
//                    JPushInterface.setAlias(
//                        applicationContext
//                        , seq++
//                        , msg.obj as String
//                    )
                }
                else -> Log.i("TAG", "Unhandled msg - " + msg.what)
            }
        }
    }

    fun getTokenState(context: Context) {
//        if (LoggerInterceptor.errorCodeCallback().equals("401001")) { //  UserManager.getInstance().clearCache();
//            UserManager.getInstance().saveToken_EXPIRED("t")
//            val intent = Intent(context, LoginActivity::class.java)
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//            context.startActivity(intent)
//            Process.killProcess(Process.myPid())
//        }
    }

    /**
     * 初始化全局异常崩溃
     */
    private fun initCrash() {
        val context = applicationContext
        // 获取当前包名
        val packageName = context.packageName
        // 获取当前进程名
        val processName = getProcessName(Process.myPid())
        // 设置是否为上报进程
//        val strategy: CrashReport.UserStrategy = UserStrategy(context)
//        strategy.setUploadProcess(processName == null || processName == packageName)
//        // 初始化Bugly
//        CrashReport.initCrashReport(context, BUHLY_KEY, true, strategy)
    }

    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    private fun getProcessName(pid: Int): String? {
        var reader: BufferedReader? = null
        try {
            reader = BufferedReader(FileReader("/proc/$pid/cmdline"))
            var processName = reader.readLine()
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim { it <= ' ' }
            }
            return processName
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
        } finally {
            try {
                reader?.close()
            } catch (exception: IOException) {
                exception.printStackTrace()
            }
        }
        return null
    }

    override fun onAppForegroundStateChange(appForegroundState: AppForegroundStateManager.AppForegroundState?) {
        hasReception = AppForegroundStateManager.AppForegroundState.IN_FOREGROUND === appForegroundState
    }

}