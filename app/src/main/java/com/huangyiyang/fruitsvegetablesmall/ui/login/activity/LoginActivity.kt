package com.huangyiyang.fruitsvegetablesmall.ui.login.activity

import android.Manifest
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.gyf.immersionbar.ktx.immersionBar
import com.huangyiyang.fruitsvegetablesmall.BuildConfig
import com.huangyiyang.fruitsvegetablesmall.Const
import com.huangyiyang.fruitsvegetablesmall.MVPApplication
import com.huangyiyang.fruitsvegetablesmall.R
import com.huangyiyang.fruitsvegetablesmall.mvp.manage.UserManager
import com.huangyiyang.fruitsvegetablesmall.mvp.activity.BaseActivity
import com.huangyiyang.fruitsvegetablesmall.ui.login.contract.LoginActivityContract
import com.huangyiyang.fruitsvegetablesmall.ui.login.model.LoginActivityModel
import com.huangyiyang.fruitsvegetablesmall.ui.login.presenter.LoginActivityPresenter
import com.huangyiyang.fruitsvegetablesmall.ui.main.activity.MainActivity
import com.huangyiyang.fruitsvegetablesmall.mvp.util.*
import java.util.*

class LoginActivity : LoginActivityContract.LoginActivityView,View.OnClickListener,BaseActivity<LoginActivityModel,LoginActivityPresenter>(){

    private var mEtLoginActivityCashierAccount //用户ID
            : EditText? = null
    private var mEtLoginActivityPassword //密码
            : EditText? = null
    private var mBtnLoginActivitySign //确认登录
            : Button? = null
    private var mBtnLoginActivityForgotPsw //忘记密码
            : TextView? = null

    companion object {
        var isR = false
        var isL = false
        fun goTo(context: Context) {
            val intent = Intent(context, LoginActivity::class.java)
            if (context !is Activity) {
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            }
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 动态权限申请
        PermissionUtil.ApplyWithOutCallBack(
            this, Manifest.permission.READ_EXTERNAL_STORAGE
            , Manifest.permission.WRITE_EXTERNAL_STORAGE
            , Manifest.permission.CAMERA
            , Manifest.permission.READ_PHONE_STATE
        )

        if (MVPApplication.checkLogin(this)) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        immersionBar {
            statusBarColor(R.color.green_4CAF65)
            navigationBarColor(R.color.green_4CAF65)
            autoDarkModeEnable(true)
        }
    }

    override fun onStart() {
        super.onStart()
        if (UserManager.getInstance()?.getToken_EXPIRED() != null && UserManager.getInstance()?.getToken_EXPIRED().equals(
                "t"
            )
        ) {
            ToastUtil.showLong(this, "登录已过期，请重新登录")
            isL = false
        }
        if (!isR) {
            //checkAppVersionCode()
        }
    }

    override fun getLayoutResId(): Int {
        return R.layout.activity_login
    }

    override fun initPresenter() {
        mPresenter?.setVM(this, mModel)
    }

    override fun initIntentData() {

    }

    override fun initToolBar() {

    }

    override fun initView() {
        //  isR = false;
        mEtLoginActivityCashierAccount =
            findViewById(R.id.et_login_activity_cashier_account) as EditText
        mEtLoginActivityCashierAccount?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) {
                if (!StringUtil.isTrimEmpty(s.toString())) {
                    mBtnLoginActivitySign!!.isEnabled = true
                } else {
                    mBtnLoginActivitySign!!.isEnabled = false
                }
            }

            override fun afterTextChanged(s: Editable) {}
        })
        mEtLoginActivityPassword = findViewById(R.id.et_login_activity_password) as EditText
        mEtLoginActivityPassword?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) {
                if (!StringUtil.isTrimEmpty(s.toString())) {
                    mBtnLoginActivitySign!!.isEnabled = true
                } else {
                    mBtnLoginActivitySign!!.isEnabled = false
                }
            }

            override fun afterTextChanged(s: Editable) {}
        })
        mBtnLoginActivityForgotPsw =
            findViewById(R.id.tw_login_activity_register) as TextView
        mBtnLoginActivityForgotPsw?.setOnClickListener(this)
        mBtnLoginActivitySign = findViewById(R.id.btn_login) as Button
        mBtnLoginActivitySign?.setOnClickListener(this)
        if (isL) {
            ToastUtil.showLong(this, "token过期，请重新登录")
            isL = false
        }
        if (UserManager.getInstance()!!.getToken_EXPIRED() != null && UserManager.getInstance()!!.getToken_EXPIRED().equals(
                "t"
            )
        ) {
            ToastUtil.showLong(this, "登录已过期，请重新登录")
            UserManager.getInstance()!!.saveToken_EXPIRED("f")
        }
        val name = UserManager.getInstance()?.getName()
        if (name != null && name.length > 1) {
            mEtLoginActivityCashierAccount?.setText(name)
            val pwd = UserManager.getInstance()!!.getUserPassword()
            if (pwd != null && pwd.length > 1) {
                mEtLoginActivityPassword?.setText(pwd)
            }
        } else {
            val code = UserManager.getInstance()!!.getUserCode()
            if (code != null && code.length > 1) {
                mEtLoginActivityCashierAccount?.setText(code)
                val pwd = UserManager.getInstance()!!.getUserPassword()
                if (pwd != null && pwd.length > 1) {
                    mEtLoginActivityPassword?.setText(pwd)
                }
            }
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btn_login -> submit()
            R.id.tw_login_activity_register -> RegisterActivity.goTo(this)
        }
    }

    /**
     * 提交登录用参数
     */
    private fun submit() { // 账号非空判断
        val account =
            mEtLoginActivityCashierAccount!!.text.toString().trim { it <= ' ' }
        if (GsonUtil.isEmpty(account)) {
            ToastUtil.showShort(this, R.string.toast_for_LoginActivity_content_0)
            return
        }
        // 密码非空判断
        val password =
            mEtLoginActivityPassword!!.text.toString().trim { it <= ' ' }
        if (TextUtils.isEmpty(password)) {
            ToastUtil.showShort(this, R.string.toast_for_LoginActivity_content_1)
            return
        }
        val map: MutableMap<String, String> =
            HashMap()
        map["userName"] = account
        map["password"] = password
        map["type"] = "android"
        map["brand"] = Build.BRAND
        map["model"] = Build.MODEL
        map["version"] = Build.VERSION.RELEASE
        map["coreVersion"] = Build.VERSION.SDK_INT.toString() + ""
        map["softwareVersion"] = BuildConfig.VERSION_NAME
        map["androidDeviceId"] = MVPApplication.ANDROID_ID!!
        mPresenter?.getLogin(ParamsUtil.getInstance()?.getBody(map))
    }

    fun openWebPage(url: String?) {
        try {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            e.printStackTrace()
        }
    }

    fun checkAppVersionCode() {
        val pm = packageManager
        val versionname: String
        val map: MutableMap<String, String> =
            HashMap()
        var packageInfo: PackageInfo? = null
        try {
            packageInfo = pm.getPackageInfo(packageName, 0)
            versionname = if (Const.BASE_URL.endsWith("5011")) {
                packageInfo.versionName + ".MILESTONE"
            } else {
                packageInfo.versionName + ".RELEASE"
            }
            map["versionCode"] = versionname
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        map["version"] = "android"
        //mPresenter.getVersionCode(map)
    }

    override fun setLogin(isBoundMobile: Boolean) {

        if (!isBoundMobile) { //判断是否需要绑定手机号
            //PhoneBindingActivity.goTo(this)
        } else {
            UserManager.getInstance()
                ?.saveUserPassword(mEtLoginActivityPassword!!.text.toString().trim { it <= ' ' })
            UserManager.getInstance()
                ?.saveName(mEtLoginActivityCashierAccount!!.text.toString().trim { it <= ' ' })
            //mPresenter.getConfig()
            startActivity(MainActivity::class.java)
            finish()
        }
    }
}