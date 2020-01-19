package com.huangyiyang.fruitsvegetablesmall.ui.order.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import com.huangyiyang.fruitsvegetablesmall.R
import com.huangyiyang.fruitsvegetablesmall.mvp.activity.BaseActivity
import com.huangyiyang.fruitsvegetablesmall.ui.order.contract.OrderListActivityContract
import com.huangyiyang.fruitsvegetablesmall.ui.order.model.OrderListActivityModel
import com.huangyiyang.fruitsvegetablesmall.ui.order.presenter.OrderListActivityPresenter

class OrderListActivity : OrderListActivityContract.OrderListActivityView, View.OnClickListener,BaseActivity<OrderListActivityModel
        , OrderListActivityPresenter>(){

    companion object{

        private var KEY_INDEX = "index"

        fun goTo(context: Context, index: Int) {
            val intent = Intent(context, OrderListActivity::class.java)
            intent.putExtra(KEY_INDEX, index)
            if (context !is Activity) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
        }
    }

    override fun onClick(v: View?) {

    }

    override fun getLayoutResId(): Int {
        return R.layout.activity_order_list
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