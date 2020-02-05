package com.huangyiyang.fruitsvegetablesmall.ui.goods.activity

import android.content.Context
import android.content.Intent
import android.view.View
import com.huangyiyang.fruitsvegetablesmall.R
import com.huangyiyang.fruitsvegetablesmall.mvp.activity.BaseActivity
import com.huangyiyang.fruitsvegetablesmall.ui.goods.contract.GoodsDetailActivityContract
import com.huangyiyang.fruitsvegetablesmall.ui.goods.model.GoodsDetailActivityModel
import com.huangyiyang.fruitsvegetablesmall.ui.goods.presenter.GoodsDetailActivityPresenter

class GoodsDetailActivity : GoodsDetailActivityContract.GoodsDetailActivityView, View.OnClickListener, BaseActivity<GoodsDetailActivityModel,
        GoodsDetailActivityPresenter>(){

    private val ID = "goods_id"

    fun goTo(context: Context, goodsId: String?) {
        val intent = Intent(context, GoodsDetailActivity::class.java)
        intent.putExtra(ID, goodsId)
        context.startActivity(intent)
    }


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

    }

    override fun initToolBar() {

    }

    override fun initView() {

    }

}