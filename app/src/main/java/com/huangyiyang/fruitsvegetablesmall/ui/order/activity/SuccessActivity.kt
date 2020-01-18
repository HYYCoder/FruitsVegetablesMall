package com.huangyiyang.fruitsvegetablesmall.ui.order.activity

import android.view.View
import com.huangyiyang.fruitsvegetablesmall.R
import com.huangyiyang.fruitsvegetablesmall.mvp.activity.BaseActivity
import com.huangyiyang.fruitsvegetablesmall.mvp.presenter.BasePresenter
import com.huangyiyang.fruitsvegetablesmall.ui.order.contract.SuccessActivityContract
import com.huangyiyang.fruitsvegetablesmall.ui.order.model.SuccessActivityModel
import com.huangyiyang.fruitsvegetablesmall.ui.order.presenter.SuccessActivityPresenter

class SuccessActivity : SuccessActivityContract.SuccessActivityView, View.OnClickListener,BaseActivity<SuccessActivityModel,SuccessActivityPresenter>(){
    override fun onClick(v: View?) {

    }

    override fun getLayoutResId(): Int {
        return R.layout.activity_order_success
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