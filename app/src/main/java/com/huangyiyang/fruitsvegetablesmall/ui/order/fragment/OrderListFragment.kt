package com.huangyiyang.fruitsvegetablesmall.ui.order.fragment

import android.content.Context
import android.view.View
import com.huangyiyang.fruitsvegetablesmall.R
import com.huangyiyang.fruitsvegetablesmall.bean.OrderListBean
import com.huangyiyang.fruitsvegetablesmall.mvp.adapter.BaseQuickAdapter
import com.huangyiyang.fruitsvegetablesmall.mvp.fragment.BaseFragment
import com.huangyiyang.fruitsvegetablesmall.ui.order.contract.OrderListFragmentContract
import com.huangyiyang.fruitsvegetablesmall.ui.order.model.OrderListFragmentModel
import com.huangyiyang.fruitsvegetablesmall.ui.order.presenter.OrderListFragmentPresenter
import com.zhouyou.recyclerview.adapter.HelperRecyclerViewHolder

class OrderListFragment : OrderListFragmentContract.OrderListFragmentView
    , View.OnClickListener,BaseFragment<OrderListFragmentModel
            , OrderListFragmentPresenter>(){
    override fun onClick(v: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getLayoutResId(): Int {
        return R.layout.fragment_order_list
    }

    override fun initPresenter() {
        mPresenter?.setVM(this, mModel)
    }

    override fun initArgumentsData() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun initToolBar() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun initView() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun initData() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    class OrderListAdapter : BaseQuickAdapter<OrderListBean>{
        constructor(context : Context) : super(context,R.layout.item_orderlist)

        override fun HelperBindData(
            viewHolder: HelperRecyclerViewHolder?,
            position: Int,
            item: OrderListBean?
        ) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

    }
}