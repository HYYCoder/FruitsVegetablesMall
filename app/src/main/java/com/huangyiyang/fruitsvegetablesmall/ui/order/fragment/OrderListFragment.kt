package com.huangyiyang.fruitsvegetablesmall.ui.order.fragment

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.huangyiyang.fruitsvegetablesmall.R
import com.huangyiyang.fruitsvegetablesmall.Const
import com.huangyiyang.fruitsvegetablesmall.bean.OrderListBean
import com.huangyiyang.fruitsvegetablesmall.mvp.adapter.BaseQuickAdapter
import com.huangyiyang.fruitsvegetablesmall.mvp.fragment.BaseFragment
import com.huangyiyang.fruitsvegetablesmall.ui.order.activity.OrderDetailActivity
import com.huangyiyang.fruitsvegetablesmall.ui.order.contract.OrderListFragmentContract
import com.huangyiyang.fruitsvegetablesmall.ui.order.model.OrderListFragmentModel
import com.huangyiyang.fruitsvegetablesmall.ui.order.presenter.OrderListFragmentPresenter
import com.huangyiyang.fruitsvegetablesmall.util.ImageLoaderUtil
import com.huangyiyang.fruitsvegetablesmall.view.main.CommonLayout
import com.zhouyou.recyclerview.XRecyclerView
import com.zhouyou.recyclerview.XRecyclerView.LoadingListener
import com.zhouyou.recyclerview.adapter.HelperRecyclerViewHolder
import java.util.*

class OrderListFragment : OrderListFragmentContract.OrderListFragmentView
    , View.OnClickListener,BaseFragment<OrderListFragmentModel
            , OrderListFragmentPresenter>(){

    private var mXRecyclerView: XRecyclerView? = null
    private var commonLayout: CommonLayout? = null
    private val categoryId = 0
    private var mOrderListListAdapter: OrderListAdapter? = null

    companion object{
        private const val ID = "id"
        fun newInstance(id: Int): OrderListFragment? {
            val args = Bundle()
            args.putInt(ID, id)
            val fragment = OrderListFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun getLayoutResId(): Int {
        return R.layout.fragment_order_list
    }

    override fun initPresenter() {
        mPresenter?.setVM(this, mModel)
    }

    override fun initArgumentsData() {

    }

    override fun initToolBar() {

    }

    override fun initView() {
        commonLayout = layout!!.findViewById(R.id.common_layout)
        mXRecyclerView = layout!!.findViewById(R.id.order_list)
        mXRecyclerView?.setLayoutManager(LinearLayoutManager(activity))
        mXRecyclerView?.setLoadingMoreEnabled(true)
        mOrderListListAdapter = OrderListAdapter(activity!!)
        mXRecyclerView?.setAdapter(mOrderListListAdapter)
        mXRecyclerView?.setPullRefreshEnabled(true)
        mXRecyclerView?.setLoadingListener(object : LoadingListener {
            override fun onRefresh() {
                mPresenter!!.onRefresh()
            }

            override fun onLoadMore() {
                mPresenter!!.onLoadMore()
            }
        })

        var status = ""
        when (categoryId) {
            1 -> status = ""
            2 -> status = resources.getString(R.string.order_payment)
            3 -> status = resources.getString(R.string.order_delivery)
            4 -> status = resources.getString(R.string.order_complete)
            else -> {
            }
        }
        val params: MutableMap<String, String> =
            HashMap()
        params["status"] = status
        params["pageSize"] = java.lang.String.valueOf(mPresenter?.mPageSize)
        mPresenter!!.initLoadParams(Const.header(), params)
        mPresenter!!.initLoadView(commonLayout, mXRecyclerView, mOrderListListAdapter)
    }

    override fun initData() {

    }

    override fun onClick(v: View?) {

    }

    inner class OrderListAdapter : BaseQuickAdapter<OrderListBean>{

        constructor(context : Context) : super(context,R.layout.item_orderlist)

        private var btnOrder: Button? = null

        override fun HelperBindData(
            viewHolder: HelperRecyclerViewHolder?,
            position: Int,
            item: OrderListBean?
        ) {
            viewHolder!!.setIsRecyclable(false) // RecyclerView不重复加载

            val tvOrderID = viewHolder.getView<TextView>(R.id.tv_orderlist_id)
            val tvOrderTime = viewHolder.getView<TextView>(R.id.tv_orderlist_time)
            val tvOrderNum = viewHolder.getView<TextView>(R.id.tv_orderlist_num)
            val tvOrderPrice = viewHolder.getView<TextView>(R.id.tv_orderlist_price)
            val tvOrderStatus = viewHolder.getView<TextView>(R.id.tv_orderlist_status)
            btnOrder = viewHolder.getView<Button>(R.id.btn_orderlist)

            tvOrderTime.setText(item?.data!!)
            tvOrderID.text = getString(R.string.order_num, item?.code)
            tvOrderNum.text = getString(R.string.order_total, item?.count)
            if (item?.payAmount!! < 0.0) {
                tvOrderPrice.text = "¥0.00"
            } else tvOrderPrice.text = getString(R.string.order_price, item.payAmount)
            if (!item.status.equals("COMPLETE")) {
                tvOrderStatus.visibility = View.GONE
            } else {
                tvOrderStatus.visibility = View.VISIBLE
            }
            tvOrderStatus.setText(getStringByStatus(item.status))
            tvOrderStatus.setTextColor(Color.GRAY)
            //===========商品列表===========
            val ivImg1 =
                viewHolder.getView<ImageView>(R.id.iv_imgs1)
            val ivImg2 =
                viewHolder.getView<ImageView>(R.id.iv_imgs2)
            val ivImg3 =
                viewHolder.getView<ImageView>(R.id.iv_imgs3)
            val ivImg4 =
                viewHolder.getView<ImageView>(R.id.iv_imgs4)
            val ivImg5 =
                viewHolder.getView<ImageView>(R.id.iv_imgs5)
            val ivImg6 =
                viewHolder.getView<ImageView>(R.id.iv_imgs6)
            val imageViewList: MutableList<ImageView> = arrayListOf()
            imageViewList.add(ivImg1)
            imageViewList.add(ivImg2)
            imageViewList.add(ivImg3)
            imageViewList.add(ivImg4)
            imageViewList.add(ivImg5)
            imageViewList.add(ivImg6)
            val imageUrls: List<String> = item.imageUrls
            if (imageUrls.size < 6) {
                for (i in imageUrls.indices) {
                    ImageLoaderUtil.getInstance()?.load(
                        imageViewList[i],
                        imageUrls[i]
                    )
                }
            } else {
                for (i in 0..4) {
                    ImageLoaderUtil.getInstance()?.load(
                        imageViewList[i],
                        imageUrls[i]
                    )
                }
                imageViewList[5]!!.setImageResource(R.drawable.ic_more_50dp)
            }
            //===========按钮============
            when (item.status) {
                "AWAITING_CONFIRMATION" -> {
                    setStyle("查看订单", R.color.grey_666666, R.drawable.bg_button_read)
                }
                "AWAITING_PAYMENT" -> {
                    setStyle("立即支付", R.color.white_ffffff, R.drawable.bg_button_payment)
                }
                "AWAITING_DELIVERY" -> {
                    setStyle("等待收货", R.color.white_ffffff, R.drawable.btn_common_100_radius_button)
                }
                "COMPLETE" -> {
                    setStyle("查看订单", R.color.grey_666666, R.drawable.bg_button_read)
                }
                "CANCEL" -> {
                    setStyle("查看详情", R.color.grey_666666, R.drawable.bg_button_read)
                    tvOrderStatus.text = "已取消"
                }
            }
            viewHolder.itemView.setOnClickListener {
                OrderDetailActivity.goTo(
                    context!!,
                    item.id
                )
            }
            btnOrder?.setOnClickListener(View.OnClickListener {
                OrderDetailActivity.goTo(
                    context!!,
                    item.id
                )
            })
        }

        private fun setStyle(
            btnString: String,
            textColor: Int,
            background: Int
        ) {
            btnOrder!!.text = btnString
            btnOrder!!.setTextColor(mContext.getColor(textColor))
            btnOrder!!.setBackgroundResource(background)
        }

        private fun getStringByStatus(status: String?): String? {
            return when (status) {
                getString(R.string.order_payment) -> getString(R.string.AWAITING_PAYMENT)
                getString(R.string.order_delivery) -> getString(R.string.AWAITING_PAYMENT)
                getString(R.string.order_complete) -> getString(R.string.COMPLETE)
                getString(R.string.order_cancel) -> getString(R.string.CANCEL)
                else -> getString(R.string.order_complete)
            }
        }
    }
}