package com.huangyiyang.fruitsvegetablesmall.ui.order.activity

import com.huangyiyang.fruitsvegetablesmall.R
import com.huangyiyang.fruitsvegetablesmall.mvp.activity.BaseActivity
import com.huangyiyang.fruitsvegetablesmall.ui.order.contract.OrderDetailActivityContract
import com.huangyiyang.fruitsvegetablesmall.ui.order.model.OrderDetailActivityModel
import com.huangyiyang.fruitsvegetablesmall.ui.order.presenter.OrderDetailActivityPresenter

class OrderDetailActivity : OrderDetailActivityContract.OrderDetailActivityView,BaseActivity<OrderDetailActivityModel
        , OrderDetailActivityPresenter>(){
    override fun getLayoutResId(): Int {
        return R.layout.activity_order_detail
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