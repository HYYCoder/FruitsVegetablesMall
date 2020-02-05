package com.huangyiyang.fruitsvegetablesmall.ui.order.activity

import android.content.Context
import android.content.Intent
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import com.huangyiyang.fruitsvegetablesmall.R
import com.huangyiyang.fruitsvegetablesmall.bean.OrderBean
import com.huangyiyang.fruitsvegetablesmall.mvp.activity.BaseActivity
import com.huangyiyang.fruitsvegetablesmall.ui.order.contract.ConfirmOrderActivityContract
import com.huangyiyang.fruitsvegetablesmall.ui.order.model.ConfirmOrderActivityModel
import com.huangyiyang.fruitsvegetablesmall.ui.order.presenter.ConfirmOrderActivityPresenter
import com.huangyiyang.fruitsvegetablesmall.view.main.LoadingDialog
import java.lang.String
import java.util.*

class ConfirmOrderActivity : ConfirmOrderActivityContract.ConfirmOrderActivityView,BaseActivity<ConfirmOrderActivityModel
        ,ConfirmOrderActivityPresenter>() {

    private var btnConfirm:Button? = null

    companion object{

        private var ID = "goods_ids"

        private var COUPON_ID = "coupons_ids"

        fun goTo(
            context: Context,
            goodsIds: ArrayList<Int?>?,
            couponsId: ArrayList<Int?>
        ) {
            val intent = Intent(context, ConfirmOrderActivity::class.java)
            intent.putIntegerArrayListExtra(ID, goodsIds)
            intent.putIntegerArrayListExtra(COUPON_ID, couponsId)
//            ConfirmOrderActivity.couponIds2 = couponsId
//            ConfirmOrderActivity.isGotoUse = true
            context.startActivity(intent)
        }

        fun goTo(
            context: Context,
            goodsIds: ArrayList<Int?>?
        ) {
            val intent = Intent(context, ConfirmOrderActivity::class.java)
            intent.putIntegerArrayListExtra(ID, goodsIds)
            context.startActivity(intent)
        }
    }

    override fun getLayoutResId(): Int {
        return R.layout.activity_confirm_order_detail
    }

    override fun initPresenter() {
        mPresenter?.setVM(this, mModel)
    }

    override fun initIntentData() {

    }

    override fun initToolBar() {

    }

    override fun initView() {
        btnConfirm = findViewById(R.id.btn_confirm)
        btnConfirm?.setOnClickListener {
//                val map: MutableMap<String, Any> = HashMap()
//                map["cartItemsIds"] = goodsIds
//                map["note"] = mMemo.getText().toString().trim({ it <= ' ' })
//                map["coupons"] = couponIdsStutes()
//                mPresenter.placeOrder(Const.header(), ParamsUtil.getInstance().getBodyObj(map))
            //跳转成功页面
            var bean:OrderBean? = null
            SuccessActivity.goTo(
                this,
                bean?.id!!,
                bean?.code,
                bean?.receiver,
                bean?.address,
                String.valueOf(bean?.amount)
            )
            finish()
            LoadingDialog.showDialogForLoading(
                this,
                getString(R.string.call_back_loading),
                false
            )
        }
    }
}