package com.huangyiyang.fruitsvegetablesmall.ui.order.contract

import com.huangyiyang.fruitsvegetablesmall.bean.OrderListBean
import com.huangyiyang.fruitsvegetablesmall.mvp.load.LoadListPresenter
import com.huangyiyang.fruitsvegetablesmall.mvp.model.BaseModelInterface
import com.huangyiyang.fruitsvegetablesmall.mvp.view.BaseViewInterface

interface OrderListFragmentContract {
    interface OrderListFragmentModel : BaseModelInterface{

    }

    interface OrderListFragmentView : BaseViewInterface{

    }

    abstract class OrderListFragmentPresenter : LoadListPresenter<OrderListBean, OrderListFragmentModel, OrderListFragmentView>(){

    }
}