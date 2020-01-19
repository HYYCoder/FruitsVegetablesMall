package com.huangyiyang.fruitsvegetablesmall.ui.order.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import com.huangyiyang.fruitsvegetablesmall.R
import com.huangyiyang.fruitsvegetablesmall.mvp.activity.BaseActivity
import com.huangyiyang.fruitsvegetablesmall.ui.order.contract.SuccessActivityContract
import com.huangyiyang.fruitsvegetablesmall.ui.order.model.SuccessActivityModel
import com.huangyiyang.fruitsvegetablesmall.ui.order.presenter.SuccessActivityPresenter

class SuccessActivity : SuccessActivityContract.SuccessActivityView, View.OnClickListener,BaseActivity<SuccessActivityModel,SuccessActivityPresenter>(){

    companion object{

        var ORDER_NUM = "order_num"
        var ORDER_ID = "order_id"
        var NAME = "name"
        var ADDRESS = "address"
        var AMOUNT = "amount"

        fun goTo(
            context: Context,
            id: Int,
            orderNum: String?,
            name: String?,
            address: String?,
            amount: String?
        ) {
            val intent = Intent(context, SuccessActivity::class.java)
            intent.putExtra(ORDER_ID, id)
            intent.putExtra(ORDER_NUM, orderNum)
            intent.putExtra(NAME, name)
            intent.putExtra(ADDRESS, address)
            intent.putExtra(AMOUNT, amount)
            if (context !is Activity) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
        }
    }

    override fun onClick(v: View?) {

    }

    override fun getLayoutResId(): Int {
        return R.layout.activity_order_success
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