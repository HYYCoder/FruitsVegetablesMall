package com.huangyiyang.fruitsvegetablesmall.ui.order.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.TextView
import com.gyf.immersionbar.ktx.immersionBar
import com.huangyiyang.fruitsvegetablesmall.R
import com.huangyiyang.fruitsvegetablesmall.mvp.activity.BaseActivity
import com.huangyiyang.fruitsvegetablesmall.ui.order.contract.SuccessActivityContract
import com.huangyiyang.fruitsvegetablesmall.ui.order.model.SuccessActivityModel
import com.huangyiyang.fruitsvegetablesmall.ui.order.presenter.SuccessActivityPresenter
import com.huangyiyang.fruitsvegetablesmall.view.main.LoadingDialog
import com.huangyiyang.fruitsvegetablesmall.view.shoppingCar.ToolbarUtil
import java.text.DecimalFormat

class SuccessActivity : SuccessActivityContract.SuccessActivityView, View.OnClickListener,BaseActivity<SuccessActivityModel,SuccessActivityPresenter>(){
    private var mTvOrderNum : TextView? = null //订单号
    private var mTvOrderName : TextView? = null //收件人
    private var mTvOrderAddress : TextView? = null//收件地址
    private var mTvOrderAmount : TextView? = null //订单金额
    private var mTvOrderDetail : TextView? = null//订单详情
    private var toolbarUtil : ToolbarUtil? = null
    private var orderNum : String? = null
    private var name : String? = null
    private var address : String? = null
    private var amount : String? = null
    private var id = 0
    private val orderDetailStatus : String? = null
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

    override fun getLayoutResId(): Int {
        return R.layout.activity_order_success
    }

    override fun initPresenter() {
        mPresenter?.setVM(this, mModel)
    }

    override fun initIntentData() {
        orderNum = intent.getStringExtra(ORDER_NUM)
        name = intent.getStringExtra(NAME)
        address = intent.getStringExtra(ADDRESS)
        amount = intent.getStringExtra(AMOUNT)
        id = intent.getIntExtra(ORDER_ID, 0)
    }

    override fun initToolBar() {
        toolbarUtil = ToolbarUtil(this,mContext)
        toolbarUtil!!.setLightBackTheme("下单成功")
        toolbarUtil!!.setLineBackgroundColor(R.color.white_ffffff)
    }

    override fun initView() {
        immersionBar {
            statusBarColor(R.color.green_4CAF65)
            navigationBarColor(R.color.green_4CAF65)
            autoDarkModeEnable(true)
        }
        var doubleAmount = 0.0

        try {
            doubleAmount = amount!!.toDouble()
            val df = DecimalFormat("#.##")
            amount = df.format(doubleAmount)
        } catch (e: NumberFormatException) {
        }
        mTvOrderNum = findViewById(R.id.tv_order_num)
        mTvOrderNum?.text = orderNum
        mTvOrderName = findViewById(R.id.tv_order_name)
        mTvOrderName?.text = name
        mTvOrderAddress = findViewById(R.id.tv_order_address)
        mTvOrderAddress?.text = address
        mTvOrderAmount = findViewById(R.id.tv_order_amount)
        if (doubleAmount < 0) doubleAmount = 0.0
        mTvOrderAmount?.text = getString(R.string.common_amount, doubleAmount)
        mTvOrderDetail = findViewById(R.id.tv_order_detail)
        mTvOrderDetail?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.tv_order_detail -> {
                OrderDetailActivity.goTo(this, id)
                LoadingDialog.showDialogForLoading(
                    this,
                    getString(R.string.call_back_loading),
                    false
                )
            }
        }
    }

//    /**
//     * 订单详情
//     */
//    private fun showOrderDetail() {
//        if (orderDetailStatus != null) {
//            when (orderDetailStatus) {
//                "AWAITING_CONFIRMATION" -> OrderDetailActivity.goTo(this, id)
//                "AWAITING_PAYMENT" -> OrderDetailActivity.goTo(this, id)
//                "AWAITING_DELIVERY" -> OpenOrderActivity.goTo(this, id)
//                "COMPLETE" -> CompleteDetailActivity.goTo(this, id, orderNum)
//                "CANCEL" -> OrderDetailActivity.goTo(this, id)
//            }
//            finish()
//        } else {
//            ToastUtil.showShort(this, "订单状态出错")
//        }
//    }
}