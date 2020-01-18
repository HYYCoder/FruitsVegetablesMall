package com.huangyiyang.fruitsvegetablesmall.ui.order.activity

import android.view.View
import com.huangyiyang.fruitsvegetablesmall.R
import com.huangyiyang.fruitsvegetablesmall.mvp.activity.BaseActivity
import com.huangyiyang.fruitsvegetablesmall.mvp.presenter.BasePresenter
import com.huangyiyang.fruitsvegetablesmall.ui.order.contract.ConfirmOrderActivityContract
import com.huangyiyang.fruitsvegetablesmall.ui.order.model.ConfirmOrderActivityModel
import com.huangyiyang.fruitsvegetablesmall.ui.order.presenter.ConfirmOrderActivityPresenter

class ConfirmOrderActivity : ConfirmOrderActivityContract.ConfirmOrderActivityView, View.OnClickListener,BaseActivity<ConfirmOrderActivityModel
        ,ConfirmOrderActivityPresenter>() {
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