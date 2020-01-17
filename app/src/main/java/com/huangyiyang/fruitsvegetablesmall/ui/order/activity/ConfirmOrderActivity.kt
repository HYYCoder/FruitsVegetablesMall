package com.huangyiyang.fruitsvegetablesmall.ui.order.activity

import android.view.View
import com.huangyiyang.fruitsvegetablesmall.R
import com.huangyiyang.fruitsvegetablesmall.mvp.activity.BaseActivity
import com.huangyiyang.fruitsvegetablesmall.mvp.presenter.BasePresenter
import com.huangyiyang.fruitsvegetablesmall.ui.order.contract.ConfirmOrderActivityContract
import com.huangyiyang.fruitsvegetablesmall.ui.order.model.ConfirmOrderActivityModel

class ConfirmOrderActivity : ConfirmOrderActivityContract.ConfirmOrderActivityView, View.OnClickListener,BaseActivity<ConfirmOrderActivityModel
        ,ConfirmOrderActivityContract.ConfirmOrderActivityView, BasePresenter<ConfirmOrderActivityModel
        ,ConfirmOrderActivityContract.ConfirmOrderActivityView>>() {
    override fun onClick(v: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getLayoutResId(): Int {
        return R.layout.activity_confirm_order_detail
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