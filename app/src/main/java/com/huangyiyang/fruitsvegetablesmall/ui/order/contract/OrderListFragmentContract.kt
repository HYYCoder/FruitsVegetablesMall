package com.huangyiyang.fruitsvegetablesmall.ui.order.contract

import com.huangyiyang.fruitsvegetablesmall.api.ApiResult
import com.huangyiyang.fruitsvegetablesmall.bean.OrderListBean
import com.huangyiyang.fruitsvegetablesmall.mvp.load.LoadListPresenter
import com.huangyiyang.fruitsvegetablesmall.mvp.model.BaseModelInterface
import com.huangyiyang.fruitsvegetablesmall.mvp.view.BaseViewInterface
import rx.Observable

interface OrderListFragmentContract {

    interface OrderListFragmentModel : BaseModelInterface{

        fun getOrderList(
            header: Map<String, String>?,
            param: Map<String, String>?
        ): Observable<ApiResult<List<OrderListBean>?>?>?
    }

    interface OrderListFragmentView : BaseViewInterface{

    }

    abstract class OrderListFragmentPresenter : LoadListPresenter<OrderListBean, OrderListFragmentModel, OrderListFragmentView>(){

    }
}