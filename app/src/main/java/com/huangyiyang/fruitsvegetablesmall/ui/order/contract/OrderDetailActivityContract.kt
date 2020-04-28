package com.huangyiyang.fruitsvegetablesmall.ui.order.contract

import com.huangyiyang.fruitsvegetablesmall.mvp.http.ApiResult
import com.huangyiyang.fruitsvegetablesmall.bean.OrderDetailBean
import com.huangyiyang.fruitsvegetablesmall.mvp.model.BaseModelInterface
import com.huangyiyang.fruitsvegetablesmall.mvp.presenter.BasePresenter
import com.huangyiyang.fruitsvegetablesmall.mvp.view.BaseViewInterface
import okhttp3.RequestBody
import retrofit2.http.HeaderMap
import rx.Observable

interface OrderDetailActivityContract {

    interface OrderDetailActivityModel : BaseModelInterface{

        fun getOrderDetail(
            @HeaderMap header: Map<String, String>?,
            orderId: String?
        ): Observable<ApiResult<OrderDetailBean>?>?

        fun updateOrderDetail(
            header: Map<String, String>?,
            id: Int,
            body: RequestBody?
        ): Observable<ApiResult<Void>?>?
    }

    interface OrderDetailActivityView : BaseViewInterface{

        fun setOrderDetail(param: OrderDetailBean?)

        fun updateSuccess()
    }

    abstract class OrderDetailActivityPresenter : BasePresenter<OrderDetailActivityModel, OrderDetailActivityView>(){

        abstract fun getOrderDetail(
            @HeaderMap header: Map<String, String>?,
            orderId: String?
        )

        abstract fun updateOrderDetail(
            header: Map<String, String>?,
            id: Int,
            body: RequestBody?
        )
    }
}