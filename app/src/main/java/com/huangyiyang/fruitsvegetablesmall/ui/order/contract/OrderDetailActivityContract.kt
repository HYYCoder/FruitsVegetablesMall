package com.huangyiyang.fruitsvegetablesmall.ui.order.contract

import com.huangyiyang.fruitsvegetablesmall.mvp.model.BaseModelInterface
import com.huangyiyang.fruitsvegetablesmall.mvp.presenter.BasePresenter
import com.huangyiyang.fruitsvegetablesmall.mvp.view.BaseViewInterface

interface OrderDetailActivityContract {

    interface OrderDetailActivityModel : BaseModelInterface{

    }

    interface OrderDetailActivityView : BaseViewInterface{

    }

    abstract class OrderDetailActivityPresenter : BasePresenter<OrderDetailActivityModel, OrderDetailActivityView>(){

    }
}