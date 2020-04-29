package com.huangyiyang.fruitsvegetablesmall.ui.login.activity

import android.Manifest
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.gyf.immersionbar.ktx.immersionBar
import com.huangyiyang.fruitsvegetablesmall.Const
import com.huangyiyang.fruitsvegetablesmall.MVPApplication
import com.huangyiyang.fruitsvegetablesmall.R
import com.huangyiyang.fruitsvegetablesmall.mvp.manage.UserManager
import com.huangyiyang.fruitsvegetablesmall.mvp.activity.BaseActivity
import com.huangyiyang.fruitsvegetablesmall.ui.login.contract.RegisterActivityContract
import com.huangyiyang.fruitsvegetablesmall.ui.login.model.RegisterActivityModel
import com.huangyiyang.fruitsvegetablesmall.ui.login.presenter.RegisterActivityPresenter
import com.huangyiyang.fruitsvegetablesmall.ui.main.activity.MainActivity
import com.huangyiyang.fruitsvegetablesmall.mvp.util.*
import java.util.*

class RegisterActivity : RegisterActivityContract.RegisterActivityView,View.OnClickListener,BaseActivity<RegisterActivityModel,RegisterActivityPresenter>(){

    private var etRegisterUsername //用户ID
            : EditText? = null
    private var etRegisterPassword //密码
            : EditText? = null
    private var etRegisterName //姓名
            : EditText? = null
    private var etRegisterMobile //手机号
            : EditText? = null
    private var etRegisterAddress //地址
            : EditText? = null
    private var etRegisterReceivingPhone //收货手机
            : EditText? = null
    private var btnRegister //注册
            : Button? = null
    private var btnRegisterBack //已有账号，返回
            : TextView? = null

    companion object {
        var isL = false
        fun goTo(context: Context) {
            val intent = Intent(context, RegisterActivity::class.java)
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
    }

    override fun getLayoutResId(): Int {
        return R.layout.activity_register
    }

    override fun initPresenter() {
        mPresenter?.setVM(this, mModel)
    }

    override fun initIntentData() {

    }

    override fun initToolBar() {

    }

    override fun initView() {
        etRegisterUsername =
            findViewById(R.id.et_register_username) as EditText
        etRegisterUsername?.addTextChangedListener(object : TextWatcher {
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
                    btnRegister!!.isEnabled = true
                } else {
                    btnRegister!!.isEnabled = false
                }
            }

            override fun afterTextChanged(s: Editable) {}
        })
        etRegisterPassword = findViewById(R.id.et_register_password) as EditText
        etRegisterPassword?.addTextChangedListener(object : TextWatcher {
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
                    btnRegister!!.isEnabled = true
                } else {
                    btnRegister!!.isEnabled = false
                }
            }

            override fun afterTextChanged(s: Editable) {}
        })
        etRegisterName =
            findViewById(R.id.et_register_name) as EditText
        etRegisterMobile =
            findViewById(R.id.et_register_mobile) as EditText
        etRegisterAddress =
            findViewById(R.id.et_register_address) as EditText
        etRegisterReceivingPhone =
            findViewById(R.id.et_register_receivingPhone) as EditText
        btnRegisterBack =
            findViewById(R.id.tw_register_back) as TextView
        btnRegisterBack?.setOnClickListener(this)
        btnRegister = findViewById(R.id.btn_register) as Button
        btnRegister?.setOnClickListener(this)
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
            etRegisterUsername?.setText(name)
            val pwd = UserManager.getInstance()!!.getUserPassword()
            if (pwd != null && pwd.length > 1) {
                etRegisterPassword?.setText(pwd)
            }
        } else {
            val code = UserManager.getInstance()!!.getUserCode()
            if (code != null && code.length > 1) {
                etRegisterUsername?.setText(code)
                val pwd = UserManager.getInstance()!!.getUserPassword()
                if (pwd != null && pwd.length > 1) {
                    etRegisterPassword?.setText(pwd)
                }
            }
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btn_register -> submit()
            R.id.tw_register_back -> super.onBackPressed()
        }
    }

    /**
     * 提交注册用参数
     */
    private fun submit() {
        val account =
            etRegisterUsername!!.text.toString().trim { it <= ' ' }
        if (GsonUtil.isEmpty(account)) {
            ToastUtil.showShort(this, R.string.toast_for_LoginActivity_content_0)
            return
        }
        // 密码非空判断
        val password =
            etRegisterPassword!!.text.toString().trim { it <= ' ' }
        if (TextUtils.isEmpty(password)) {
            ToastUtil.showShort(this, R.string.toast_for_LoginActivity_content_1)
            return
        }
        val name =
            etRegisterName!!.text.toString().trim { it <= ' ' }
        if (TextUtils.isEmpty(password)) {
            ToastUtil.showShort(this, R.string.toast_for_LoginActivity_content_2)
            return
        }
        val mobile =
            etRegisterMobile!!.text.toString().trim { it <= ' ' }
        if (TextUtils.isEmpty(password)) {
            ToastUtil.showShort(this, R.string.toast_for_LoginActivity_content_3)
            return
        }
        val address =
            etRegisterAddress!!.text.toString().trim { it <= ' ' }
        if (TextUtils.isEmpty(password)) {
            ToastUtil.showShort(this, R.string.toast_for_LoginActivity_content_4)
            return
        }
        val receivingPhone =
            etRegisterReceivingPhone!!.text.toString().trim { it <= ' ' }
        if (TextUtils.isEmpty(password)) {
            ToastUtil.showShort(this, R.string.toast_for_LoginActivity_content_5)
            return
        }
        val map: MutableMap<String, String> =
            HashMap()
        map["userName"] = account
        map["password"] = password
        map["name"] = name
        map["mobile"] = mobile
        map["address"] = address
        map["receivingPhone"] = receivingPhone
        mPresenter?.getRegister(ParamsUtil.getInstance()?.getBody(map))
    }

    override fun setRegister(isBoundMobile: Boolean) {
        UserManager.getInstance()
            ?.saveUserPassword(etRegisterPassword!!.text.toString().trim { it <= ' ' })
        UserManager.getInstance()
            ?.saveName(etRegisterUsername!!.text.toString().trim { it <= ' ' })
        startActivity(MainActivity::class.java)
        finish()
    }
}