package com.huangyiyang.fruitsvegetablesmall.ui.login.activity

import android.view.View
import com.huangyiyang.fruitsvegetablesmall.R
import com.huangyiyang.fruitsvegetablesmall.mvp.activity.BaseActivity
import com.huangyiyang.fruitsvegetablesmall.ui.login.contract.LoginActivityContract
import com.huangyiyang.fruitsvegetablesmall.ui.login.model.LoginActivityModel
import com.huangyiyang.fruitsvegetablesmall.ui.login.presenter.LoginActivityPresenter

class LoginActivity : LoginActivityContract.LoginActivityView,View.OnClickListener,BaseActivity<LoginActivityModel,LoginActivityPresenter>(){
    override fun onClick(v: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getLayoutResId(): Int {
        return R.layout.activity_login
    }

    override fun initPresenter() {
        mPresenter?.setVM(this, mModel)
    }

    override fun initIntentData() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun initToolBar() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun initView() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}