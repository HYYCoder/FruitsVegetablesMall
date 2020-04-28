package com.huangyiyang.fruitsvegetablesmall

import android.annotation.SuppressLint
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
import com.huangyiyang.fruitsvegetablesmall.mvp.http.FrameConst
import com.huangyiyang.fruitsvegetablesmall.mvp.manage.AppForegroundStateManager
import com.huangyiyang.fruitsvegetablesmall.mvp.manage.UserManager
import com.huangyiyang.fruitsvegetablesmall.ui.login.activity.LoginActivity
import com.huangyiyang.fruitsvegetablesmall.mvp.util.ContextUtils
import com.huangyiyang.fruitsvegetablesmall.mvp.util.ImageLoaderUtil
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
        var ANDROID_ID: String? = null
        private var myApplication: MVPApplication? = null

        fun checkLogin(context: Context): Boolean {
            var isLogin: Boolean = UserManager.checkUser(context)
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
            }
            return if (errorCode!!.containsKey(code)) {
                if (code == "500") { // UserManager.getInstance().clearCache();
                    UserManager.getInstance()?.saveToken_EXPIRED("t")
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
    }

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
        //监听前后台
        AppForegroundStateManager.getInstance()?.addListener(this)
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
                }
                else -> Log.i("TAG", "Unhandled msg - " + msg.what)
            }
        }
    }

    override fun onAppForegroundStateChange(appForegroundState: AppForegroundStateManager.AppForegroundState?) {
        hasReception = AppForegroundStateManager.AppForegroundState.IN_FOREGROUND === appForegroundState
    }

}