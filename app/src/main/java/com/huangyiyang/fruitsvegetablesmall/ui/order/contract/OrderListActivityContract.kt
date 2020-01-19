package com.huangyiyang.fruitsvegetablesmall.ui.order.contract

import com.huangyiyang.fruitsvegetablesmall.mvp.model.BaseModelInterface
import com.huangyiyang.fruitsvegetablesmall.mvp.presenter.BasePresenter
import com.huangyiyang.fruitsvegetablesmall.mvp.view.BaseViewInterface

interface OrderListActivityContract {
    interface OrderListActivityyModel : BaseModelInterface{

    }

    interface OrderListActivityView : BaseViewInterface{

    }

    abstract class OrderListActivityyPresenter : BasePresenter<OrderListActivityyModel, OrderListActivityView>(){

    }
}