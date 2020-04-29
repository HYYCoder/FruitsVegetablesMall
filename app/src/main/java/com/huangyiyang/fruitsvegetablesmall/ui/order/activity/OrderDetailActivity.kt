package com.huangyiyang.fruitsvegetablesmall.ui.order.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.gyf.immersionbar.ktx.immersionBar
import com.huangyiyang.fruitsvegetablesmall.R
import com.huangyiyang.fruitsvegetablesmall.Const
import com.huangyiyang.fruitsvegetablesmall.bean.OrderDetailBean
import com.huangyiyang.fruitsvegetablesmall.mvp.activity.BaseActivity
import com.huangyiyang.fruitsvegetablesmall.mvp.adapter.BaseQuickAdapter
import com.huangyiyang.fruitsvegetablesmall.ui.order.contract.OrderDetailActivityContract
import com.huangyiyang.fruitsvegetablesmall.ui.order.model.OrderDetailActivityModel
import com.huangyiyang.fruitsvegetablesmall.ui.order.presenter.OrderDetailActivityPresenter
import com.huangyiyang.fruitsvegetablesmall.mvp.util.ImageLoaderUtil
import com.huangyiyang.fruitsvegetablesmall.mvp.util.ParamsUtil
import com.huangyiyang.fruitsvegetablesmall.mvp.util.ToastUtil
import com.huangyiyang.fruitsvegetablesmall.view.main.CommonLayout
import com.huangyiyang.fruitsvegetablesmall.view.shoppingCar.ToolbarUtil
import com.zhouyou.recyclerview.XRecyclerView
import com.zhouyou.recyclerview.adapter.HelperRecyclerViewHolder
import java.util.*

class OrderDetailActivity : OrderDetailActivityContract.OrderDetailActivityView,BaseActivity<OrderDetailActivityModel
        , OrderDetailActivityPresenter>(){

    var tvOrderDetailId: TextView? = null
    var tvOrderDetailTime: TextView? = null
    var orderRecyclerView: XRecyclerView? = null
    var commonContent: CommonLayout? = null
    var tvOrderDetailPrice: TextView? = null
    var tvOrderDetailCoupon: TextView? = null
    var tvOrderDetailTotal: TextView? = null
    var tvOrderDetailPeople: TextView? = null
    var tvOrderDetailPhone: TextView? = null
    var tvOrderDetailAddress: TextView? = null
    var btnOrderDetail: Button? = null
    var tvOrderDetailNote: TextView? = null
    var flOrderDetailCoupon : FrameLayout ? = null
    private var toolbarUtil: ToolbarUtil? = null
    private var mOrderId = 0
    private var detailListAdapter: DetailListAdapter? = null
    private var orderDetail: OrderDetailBean? = null
    private var mCommonLayout: CommonLayout? = null

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
        mOrderId = intent.getIntExtra(KEY_ORDER_ID, 0)
    }

    override fun initToolBar() {
        toolbarUtil = ToolbarUtil(this,mContext)
        toolbarUtil!!.setLightBackTheme("订单详情")
        toolbarUtil!!.setLineBackgroundColor(R.color.white_ffffff)
    }

    override fun initView() {
        immersionBar {
            statusBarColor(R.color.green_4CAF65)
            navigationBarColor(R.color.green_4CAF65)
            autoDarkModeEnable(true)
        }
        tvOrderDetailId = findViewById(R.id.tv_order_detail_id)
        tvOrderDetailTime = findViewById(R.id.tv_order_detail_time)
        orderRecyclerView = findViewById(R.id.order_recyclerView)
        commonContent = findViewById(R.id.common_content)
        tvOrderDetailPrice = findViewById(R.id.tv_order_detail_price)
        tvOrderDetailCoupon = findViewById(R.id.tv_order_detail_coupon)
        tvOrderDetailTotal = findViewById(R.id.tv_order_detail_total)
        tvOrderDetailPeople = findViewById(R.id.tv_order_detail_people)
        tvOrderDetailPhone = findViewById(R.id.tv_order_detail_phone)
        tvOrderDetailAddress = findViewById(R.id.tv_order_detail_address)
        btnOrderDetail = findViewById(R.id.btn_order_detail)
        tvOrderDetailNote = findViewById(R.id.tv_order_detail_note)
        flOrderDetailCoupon = findViewById(R.id.fl_order_detail_coupon)
        orderRecyclerView?.layoutManager = LinearLayoutManager(this)
        orderRecyclerView?.isPullRefreshEnabled = false
        orderRecyclerView?.isLoadingMoreEnabled = false
        detailListAdapter = DetailListAdapter(this)
        orderRecyclerView?.adapter = detailListAdapter
        orderRecyclerView?.isPullRefreshEnabled = false

        mPresenter?.getOrderDetail(Const.header(), mOrderId.toString())
    }

    override fun onBackPressed() {
        super.onBackPressed()
        OrderListActivity.goTo(this, 0)
    }

    private fun setStyle(
        btnString: String,
        textColor: Int,
        background: Int
    ) {
        btnOrderDetail!!.text = btnString
        btnOrderDetail!!.setTextColor(getColor(textColor))
        btnOrderDetail!!.setBackgroundResource(background)
    }

    override fun setOrderDetail(param: OrderDetailBean?) {
        orderDetail = param
        val giftDatas: ArrayList<OrderDetailBean.Companion.DetailsBean> = ArrayList<OrderDetailBean.Companion.DetailsBean>()
        val goodsDatas: ArrayList<OrderDetailBean.Companion.DetailsBean> = ArrayList<OrderDetailBean.Companion.DetailsBean>()
        val tvGift: TextView = findViewById(R.id.discount_giftTitle)
        for (i in 0 until param?.details?.size!!) {
            if (param.details[i].type == "GIFT") {
                tvGift.visibility = View.VISIBLE
                giftDatas.add(param.details[i])
            } else if (param.details[i].type == "GOODS") {
                goodsDatas.add(param.details[i])
            } else if (i >= param.details.size) {
                tvGift.visibility = View.GONE
            }
        }
        btnOrderDetail!!.visibility = View.VISIBLE
        detailListAdapter?.setListAll(goodsDatas)
        tvOrderDetailId!!.text = "订单编号：" + param.code
        tvOrderDetailTime?.text = param.date
        tvOrderDetailPrice!!.text = getString(R.string.common_amount, param.amount)
        if (param.discountAmount !== 0.0) {
            tvOrderDetailCoupon!!.text = "-" + getString(
                R.string.common_amount,
                param.discountAmount
            )
            flOrderDetailCoupon?.visibility = View.VISIBLE
        } else {
            flOrderDetailCoupon?.visibility = View.GONE
        }
        if (param.paidAmount < 0) {
            tvOrderDetailTotal!!.text = "¥0.00"
        } else {
            tvOrderDetailTotal!!.text = getString(
            R.string.common_amount,
            param.paidAmount
        )
        }
        tvOrderDetailPeople?.text = param.receiver
        tvOrderDetailPhone?.text = param.mobile
        tvOrderDetailAddress?.text = param.address
        tvOrderDetailNote?.text = param.note
        if (param.status == getString(R.string.order_cancel) || param.status == getString(R.string.order_complete)
        ) {
            setStyle("查看订单", R.color.grey_666666, R.drawable.bg_button_read)
        }
        if (param.status == getString(R.string.order_payment)) {
            setStyle("立即支付", R.color.white_ffffff, R.drawable.bg_button_payment)
            btnOrderDetail?.setOnClickListener {
                val map: MutableMap<String, String> = HashMap()
                map["status"] = "AWAITING_DELIVERY"
                mPresenter!!.updateOrderDetail(
                    Const.header(),param.id,
                    ParamsUtil.getInstance()?.getBody(map)
                )
                ToastUtil.showShort(mContext, "支付成功！")
                super.onBackPressed()
            }
        }
        if (param.status == getString(R.string.order_delivery)) {
            setStyle("确认收货", R.color.white_ffffff, R.drawable.btn_common_100_radius_button)
            btnOrderDetail?.setOnClickListener {
                val map: MutableMap<String, String> = HashMap()
                map["status"] = "COMPLETE"
                mPresenter!!.updateOrderDetail(
                    Const.header(),param.id,
                    ParamsUtil.getInstance()?.getBody(map)
                )
                ToastUtil.showShort(mContext, "收货成功！")
                super.onBackPressed()
            }
        }
        if (param.status == getString(R.string.order_payment_overdue)) {
            setStyle("查看订单", R.color.grey_666666, R.drawable.bg_button_read)
            tvOrderDetailTime!!.text = "支付超时，订单已取消"
            tvOrderDetailTime!!.setTextColor(Color.GRAY)
        }
    }

    override fun updateSuccess() {

    }

    inner class DetailListAdapter : BaseQuickAdapter<OrderDetailBean.Companion.DetailsBean> {

        constructor(context: Context?) : super(
            context,
            R.layout.item_order_gifts_goods_list
        )

        override fun HelperBindData(
            viewHolder: HelperRecyclerViewHolder,
            position: Int,
            data: OrderDetailBean.Companion.DetailsBean
        ) {
            if (data.type == "GOODS") {
                val mGoodsImg =
                    viewHolder.getView<ImageView>(R.id.gift_img) //商品图
                ImageLoaderUtil.getInstance()?.load(
                    mGoodsImg,
                    data.imageUrl.split("&&")[1]
                )
                val mGoodsName =
                    viewHolder.getView<TextView>(R.id.tv_item_gift_name) //商品名称
                mGoodsName.text = data.name
                val mGoodsPrice =
                    viewHolder.getView<TextView>(R.id.tv_item_gift_price) //商品价格
                mGoodsPrice.text = getString(R.string.common_amount, data.price)
                val mGoodsCount =
                    viewHolder.getView<TextView>(R.id.tv_item_gift_count) //商品数量
                mGoodsCount.text = "x" + getString(R.string.common_amount2, data.quantity)
            }
        }
    }

}