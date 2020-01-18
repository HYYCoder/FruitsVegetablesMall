package com.huangyiyang.fruitsvegetablesmall.ui.good.activity

import android.view.View
import com.huangyiyang.fruitsvegetablesmall.R
import com.huangyiyang.fruitsvegetablesmall.mvp.activity.BaseActivity
import com.huangyiyang.fruitsvegetablesmall.ui.good.contract.GoodsDetailActivityContract
import com.huangyiyang.fruitsvegetablesmall.ui.good.model.GoodsDetailActivityModel
import com.huangyiyang.fruitsvegetablesmall.ui.good.presenter.GoodsDetailActivityPresenter

class GoodsDetailActivity : GoodsDetailActivityContract.GoodsDetailActivityView, View.OnClickListener, BaseActivity<GoodsDetailActivityModel,
        GoodsDetailActivityPresenter>(){
    override fun onClick(v: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getLayoutResId(): Int {
        return R.layout.activity_goods_detail
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