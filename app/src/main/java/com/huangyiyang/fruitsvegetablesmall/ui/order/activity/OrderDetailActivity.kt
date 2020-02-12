package com.huangyiyang.fruitsvegetablesmall.ui.order.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.huangyiyang.fruitsvegetablesmall.R
import com.huangyiyang.fruitsvegetablesmall.mvp.activity.BaseActivity
import com.huangyiyang.fruitsvegetablesmall.ui.order.contract.OrderDetailActivityContract
import com.huangyiyang.fruitsvegetablesmall.ui.order.model.OrderDetailActivityModel
import com.huangyiyang.fruitsvegetablesmall.ui.order.presenter.OrderDetailActivityPresenter

class OrderDetailActivity : OrderDetailActivityContract.OrderDetailActivityView,BaseActivity<OrderDetailActivityModel
        , OrderDetailActivityPresenter>(){

    companion object{
        private const val KEY_ORDER_ID = "order_id"
        private const val KEY_QR_CODE = "qr_code"
        fun goTo(context: Context, orderId: Int) {
            goTo(context, orderId, null)
        }

        fun goTo(
            context: Context,
            orderId: Int,
            qrcode: String?
        ) {
            val intent = Intent(context, OrderDetailActivity::class.java)
            intent.putExtra(KEY_ORDER_ID, orderId)
            intent.putExtra(KEY_QR_CODE, qrcode)
            if (context !is Activity) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
        }
    }

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