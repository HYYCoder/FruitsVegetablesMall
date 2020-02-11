package com.huangyiyang.fruitsvegetablesmall.ui.order.contract

import com.huangyiyang.fruitsvegetablesmall.api.ApiResult
import com.huangyiyang.fruitsvegetablesmall.bean.ConfirmOrderBean
import com.huangyiyang.fruitsvegetablesmall.bean.OrderBean
import com.huangyiyang.fruitsvegetablesmall.mvp.model.BaseModelInterface
import com.huangyiyang.fruitsvegetablesmall.mvp.presenter.BasePresenter
import com.huangyiyang.fruitsvegetablesmall.mvp.view.BaseViewInterface
import okhttp3.RequestBody
import rx.Observable

interface ConfirmOrderActivityContract {

    interface ConfirmOrderActivityModel : BaseModelInterface{

        fun getConfirmOrder(
            header: Map<String, String>?,
            param: RequestBody?
        ): Observable<ApiResult<ConfirmOrderBean>?>?

        fun placeOrder(
            header: Map<String, String>?,
            param: RequestBody?
        ): Observable<ApiResult<OrderBean>?>?

//        fun getCouponList(
//            header: Map<String, String>?,
//            body: RequestBody?
//        ): Observable<ApiResult<List<CouponListBean>?>?>?

    }

    interface ConfirmOrderActivityView : BaseViewInterface{
        fun setConfirmOrder(param: ConfirmOrderBean?)

        fun setOrderConplete(param: OrderBean?)

//        fun setCouponList(param: List<CouponListBean?>?)
    }

    abstract class ConfirmOrderActivityPresenter : BasePresenter<ConfirmOrderActivityModel, ConfirmOrderActivityView>(){

        abstract fun placeOrder(
            header: Map<String, String>?,
            param: RequestBody?
        )

        abstract fun getConfirmOrder(
            header: Map<String, String>?,
            param: RequestBody?
        )

//        abstract fun getCouponList(
//            header: Map<String, String>?,
//            param: RequestBody?
//        )
    }
}