package com.huangyiyang.fruitsvegetablesmall.ui.order.activity

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.gyf.immersionbar.ktx.immersionBar
import com.huangyiyang.fruitsvegetablesmall.R
import com.huangyiyang.fruitsvegetablesmall.Const
import com.huangyiyang.fruitsvegetablesmall.bean.ConfirmOrderBean
import com.huangyiyang.fruitsvegetablesmall.bean.OrderBean
import com.huangyiyang.fruitsvegetablesmall.manage.UserManager
import com.huangyiyang.fruitsvegetablesmall.mvp.activity.BaseActivity
import com.huangyiyang.fruitsvegetablesmall.mvp.adapter.BaseQuickAdapter
import com.huangyiyang.fruitsvegetablesmall.ui.order.contract.ConfirmOrderActivityContract
import com.huangyiyang.fruitsvegetablesmall.ui.order.model.ConfirmOrderActivityModel
import com.huangyiyang.fruitsvegetablesmall.ui.order.presenter.ConfirmOrderActivityPresenter
import com.huangyiyang.fruitsvegetablesmall.util.ImageLoaderUtil
import com.huangyiyang.fruitsvegetablesmall.util.ParamsUtil
import com.huangyiyang.fruitsvegetablesmall.view.main.CommonLayout
import com.huangyiyang.fruitsvegetablesmall.view.main.LoadingDialog
import com.huangyiyang.fruitsvegetablesmall.view.shoppingCar.ToolbarUtil
import com.zhouyou.recyclerview.XRecyclerView
import com.zhouyou.recyclerview.adapter.HelperRecyclerViewHolder
import java.text.DecimalFormat
import java.util.*

class ConfirmOrderActivity : ConfirmOrderActivityContract.ConfirmOrderActivityView,BaseActivity<ConfirmOrderActivityModel
        ,ConfirmOrderActivityPresenter>(), View.OnClickListener {

    private var shoppingCarIds : MutableList<Int> = arrayListOf()
    private var mCommonLayout: CommonLayout? = null
    private var mOrderGoodsListAdapter: OrderGoodsListAdapter? = null
    private var mOrderGiftsListAdapter: OrderGiftsListAdapter? = null
    private var mOrderListRecyclerView: XRecyclerView? = null
    private var toolbarUtil: ToolbarUtil? = null
    private var rlChooseCoupon: RelativeLayout? = null
    private var user_name: TextView? = null
    private var user_phone: TextView? = null
    private var user_address: TextView? = null
    private var mCouponAmount: TextView? = null //优惠金额
    private var mCouponAmount2: TextView? = null //优惠金额
    private var mTotalAmount: TextView? = null //商品金额
    private var mConfirmTotalAmount: TextView? = null  //支付金额
    private var mMemo: TextView? = null  //备注
    private var mBtnConfirm: Button? = null

    companion object{

        private val ID = "order_ids"
        private val COUPON_ID = "coupons_ids"
        private var couponIds : MutableList<Int> = arrayListOf() //每次打开后初始化可用优惠券
        private var couponIds2 : MutableList<Int> = arrayListOf() //用来记录打开优惠券页面选择的优惠券id
        private var isGotoUse = false //用来标记是否加入过优惠券页面

        fun goTo(
            context: Context,
            shoppingCarIds: ArrayList<Int>?,
            couponsId: ArrayList<Int>
        ) {
            val intent = Intent(context, ConfirmOrderActivity::class.java)
            intent.putIntegerArrayListExtra(ID, shoppingCarIds)
            intent.putIntegerArrayListExtra(COUPON_ID, couponsId)
            couponIds2 = couponsId
            isGotoUse = true
            context.startActivity(intent)
        }

        fun goTo(
            context: Context,
            shoppingCarIds: ArrayList<Int?>?
        ) {
            val intent = Intent(context, ConfirmOrderActivity::class.java)
            intent.putIntegerArrayListExtra(ID, shoppingCarIds)
            context.startActivity(intent)
        }
    }

    fun couponIdsStutes(): List<Int> {
        return if (isGotoUse) {
            couponIds2
        } else {
            couponIds
        }
    }

    override fun getLayoutResId(): Int {
        return R.layout.activity_confirm_order_detail
    }

    override fun initPresenter() {
        mPresenter?.setVM(this, mModel)
    }

    override fun initIntentData() {
        shoppingCarIds = intent.getIntegerArrayListExtra(ID)
    }

    override fun initToolBar() {
        toolbarUtil = ToolbarUtil(this,mContext)
        toolbarUtil!!.setLightBackTheme("确认订单")
        toolbarUtil!!.setLineBackgroundColor(R.color.white_ffffff)
    }

    override fun initView() {
        immersionBar {
            statusBarColor(R.color.green_4CAF65)
            navigationBarColor(R.color.green_4CAF65)
            autoDarkModeEnable(true)
        }
        //===================地址信息===================
        user_name = findViewById(R.id.user_name)
        user_phone = findViewById(R.id.user_phone)
        user_address = findViewById(R.id.user_address)
        user_name?.setText(UserManager.getInstance()?.getUserName())
        user_phone?.setText(UserManager.getInstance()?.getUserPhone())
        user_address?.setText(UserManager.getInstance()?.getUserAddress())
        //===============商品列表========================
        mCommonLayout = findViewById(R.id.common_content)
        mOrderGoodsListAdapter = OrderGoodsListAdapter(this)
        mOrderListRecyclerView = findViewById(R.id.discount_recyclerView)
        mOrderListRecyclerView?.setLayoutManager(LinearLayoutManager(this))
        mOrderListRecyclerView?.setAdapter(mOrderGoodsListAdapter)
        mOrderListRecyclerView?.setLoadingMoreEnabled(false)
        mOrderListRecyclerView?.setPullRefreshEnabled(false)
        //==============满赠列表========================
        mCommonLayout = findViewById(R.id.common_contentGfit)
        mOrderGiftsListAdapter = OrderGiftsListAdapter(this)
        mOrderListRecyclerView = findViewById(R.id.gfit_recyclerView)
        mOrderListRecyclerView?.setLayoutManager(LinearLayoutManager(this))
        mOrderListRecyclerView?.setAdapter(mOrderGiftsListAdapter)
        mOrderListRecyclerView?.setLoadingMoreEnabled(false)
        mOrderListRecyclerView?.setPullRefreshEnabled(false)
        //====================价格计算=====================
        mCouponAmount = findViewById(R.id.coupon_amount)
        mCouponAmount2 = findViewById(R.id.coupon_amount2)
        mTotalAmount = findViewById(R.id.goods_amount)
        mConfirmTotalAmount = findViewById(R.id.confirm_order_total_amount)
        mMemo = findViewById(R.id.memo_text) //备注

        rlChooseCoupon = findViewById(R.id.rl_choose_coupon)
        rlChooseCoupon?.setOnClickListener(this)
        //====================bottom=====================
        mBtnConfirm = findViewById(R.id.btn_confirm) //下单按钮

        mBtnConfirm?.setOnClickListener(this)
    }

    override fun onStart() {
        super.onStart()
        getCouponList()
        val map: MutableMap<String, List<Int>> =
            HashMap()
        map["ids"] = shoppingCarIds
        if (isGotoUse) {
            map["couponIds"] = couponIdsStutes()
        }
        mPresenter?.getConfirmOrder(Const.header(), ParamsUtil.getInstance()?.getBodyIntegerList(map))
        LoadingDialog.showDialogForLoading(this, getString(R.string.call_back_loading), false)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        isGotoUse=false
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btn_confirm -> {
                val shoppingCarIdsList : MutableList<String> = arrayListOf()
                shoppingCarIds.map {
                    shoppingCarIdsList.add(it.toString())
                }
                val notes : MutableList<String> = arrayListOf()
                notes.add(mMemo!!.text.toString().trim { it <= ' ' })
                val map: MutableMap<String, Any> = HashMap()
                map["ids"] = shoppingCarIdsList
                map["note"] = notes
                //map["coupons"] = couponIdsStutes()
                mPresenter?.placeOrder(Const.header(), ParamsUtil.getInstance()!!.getBodyObj(map))
                LoadingDialog.showDialogForLoading(
                    this,
                    getString(R.string.call_back_loading),
                    false
                )
            }
//            R.id.rl_choose_coupon -> CouponActivity.goTo(
//                this@ConfirmOrderActivity,
//                goodsIds,
//                couponIdsStutes()
//            )
        }
    }

    private fun getCouponList() { //==========第一次打开结算页面时的自动选择可用的优惠券============
        val map: MutableMap<String, MutableList<Int>?> =
            HashMap()
        map["ids"] = shoppingCarIds
        //map["selectedCouponsIds"] = couponIds
        //mPresenter.getCouponList(Const.header(), ParamsUtil.getInstance()!!.getBodyIntegerList(map))
    }

    override fun setConfirmOrder(param: ConfirmOrderBean?) {
        couponIds = param?.coupons as MutableList<Int>
        mCouponAmount!!.text = getString(R.string.coupon_amount, param.discountAmount)
        if (param.discountAmount > 0) {
            mCouponAmount2!!.text = "-" + getString(
                R.string.common_amount,
                param.discountAmount
            )
        } else mCouponAmount2!!.text = "¥0.00"
        mTotalAmount!!.text = getString(R.string.common_amount, param.amount)
        var amount: Double = param.amount - param.discountAmount
        if (amount < 0) amount = 0.0
        mConfirmTotalAmount!!.text = getString(R.string.common_amount, amount)
        //添加商品信息
        mOrderGoodsListAdapter!!.setListAll(param.cartItems as List<ConfirmOrderBean.CartItemsBean?>?)
        val tvGift: TextView = findViewById(R.id.discount_giftTitle)
        if (param.gifts.size > 0) {
            tvGift.visibility = View.VISIBLE
        } else {
            tvGift.visibility = View.GONE
        }
        mOrderGiftsListAdapter!!.setListAll(param.gifts as List<ConfirmOrderBean.GiftsBean?>?)
        LoadingDialog.cancelDialogForLoading()
    }

    override fun setOrderConplete(bean: OrderBean?) {
        LoadingDialog.cancelDialogForLoading()
        isGotoUse = false
        //跳转成功页面
        SuccessActivity.goTo(
            this,
            bean?.id!!,
            bean.code,
            bean.receiver,
            bean.address,
            java.lang.String.valueOf(bean.amount)
        )
        finish()
    }

//    override fun setCouponList(param: List<CouponListBean?>?) {
//        val rlCoupons: RelativeLayout = findViewById(R.id.rl_choose_coupon)
//        val rlCouponAmount: RelativeLayout = findViewById(R.id.rl_coupon_amount)
//        //隐藏无效的优惠券
//        var i = 0
//        while (i < param!!.size) {
//            if (!param[i].isAvailable()) {
//                param.removeAt(i)
//                i = i - 1
//            }
//            i++
//        }
//        if (param!!.size <= 0) {
//            rlCoupons.visibility = View.GONE
//            rlCouponAmount.visibility = View.GONE
//        } else {
//            rlCoupons.visibility = View.VISIBLE
//            rlCouponAmount.visibility = View.VISIBLE
//        }
//    }

    private inner class OrderGoodsListAdapter internal constructor(context: Context?) :
        BaseQuickAdapter<ConfirmOrderBean.CartItemsBean?>(
            context,
            R.layout.item_confirm_order_detail_list
        ) {
        override fun HelperBindData(
            viewHolder: HelperRecyclerViewHolder,
            position: Int,
            data: ConfirmOrderBean.CartItemsBean?
        ) {
            viewHolder.setIsRecyclable(false)
            val decimalFormat = DecimalFormat("0.00")
            val mGoodsImg =
                viewHolder.getView<ImageView>(R.id.goods_img) //商品图
            ImageLoaderUtil.getInstance()
                ?.load(mGoodsImg, data?.imageUrls?.split("&&")?.get(1))
            val mGoodsName = viewHolder.getView<TextView>(R.id.tv_item_goods_name) //商品名称
            mGoodsName.setText(data?.name)
            val mGoodsPrice =
                viewHolder.getView<TextView>(R.id.tv_item_goods_price) //商品价格
            mGoodsPrice.setText(getString(R.string.common_amount, data?.price))
            val mGoodsUnit =
                viewHolder.getView<TextView>(R.id.tv_item_goods_price_unit) //商品单位
            mGoodsUnit.setText(
                getString(
                    R.string.shopping_car_item_goods_unit,
                    data?.specification
                )
            )
            val mGoodsCount =
                viewHolder.getView<TextView>(R.id.tv_item_goods_count) //商品数量
            mGoodsCount.setText(
                getString(
                    R.string.common_count,
                    decimalFormat.format(data?.quantity).toString()
                )
            )
        }
    }

    private inner class OrderGiftsListAdapter internal constructor(context: Context?) :
        BaseQuickAdapter<ConfirmOrderBean.GiftsBean?>(
            context,
            R.layout.item_confirm_order_gifts_list
        ) {
        override fun HelperBindData(
            viewHolder: HelperRecyclerViewHolder,
            position: Int,
            data: ConfirmOrderBean.GiftsBean?
        ) {
            viewHolder.setIsRecyclable(false)
            val decimalFormat = DecimalFormat("0.00")
            if (data != null) {
                val mGoodsImg =
                    viewHolder.getView<ImageView>(R.id.gift_img) //赠品图
                ImageLoaderUtil.getInstance()?.load(mGoodsImg, data.imageUrl)
                val mGoodsName =
                    viewHolder.getView<TextView>(R.id.tv_item_gift_name) //赠品名称
                mGoodsName.setText(data.name)
                val mGoodsPrice =
                    viewHolder.getView<TextView>(R.id.tv_item_gift_price) //赠品价格
                mGoodsPrice.setText(getString(R.string.common_amount, data.price))
                val mGoodsUnit =
                    viewHolder.getView<TextView>(R.id.tv_item_gift_unit) //赠品单位
                mGoodsUnit.setText(
                    getString(
                        R.string.shopping_car_item_goods_unit,
                        data.specification
                    )
                )
                val mGoodsCount =
                    viewHolder.getView<TextView>(R.id.tv_item_gift_count) //商品数量
                mGoodsCount.setText(
                    getString(
                        R.string.common_count,
                        decimalFormat.format(data.quantity / 100).toString()
                    )
                )
            }
        }
    }

}