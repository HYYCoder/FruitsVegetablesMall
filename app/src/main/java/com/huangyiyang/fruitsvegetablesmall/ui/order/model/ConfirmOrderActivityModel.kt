package com.huangyiyang.fruitsvegetablesmall.ui.order.model

import com.huangyiyang.fruitsvegetablesmall.mvp.http.ApiResult
import com.huangyiyang.fruitsvegetablesmall.mvp.http.FrameConst
import com.huangyiyang.fruitsvegetablesmall.api.HttpApi
import com.huangyiyang.fruitsvegetablesmall.bean.ConfirmOrderBean
import com.huangyiyang.fruitsvegetablesmall.bean.OrderBean
import com.huangyiyang.fruitsvegetablesmall.mvp.rxevent.RxSchedulers
import com.huangyiyang.fruitsvegetablesmall.ui.order.contract.ConfirmOrderActivityContract
import okhttp3.RequestBody
import rx.Observable

class ConfirmOrderActivityModel : ConfirmOrderActivityContract.ConfirmOrderActivityModel{

    override fun getConfirmOrder(
        header: Map<String, String>?,
        param: RequestBody?
    ): Observable<ApiResult<ConfirmOrderBean>?>? {
        return FrameConst.apiService(HttpApi::class.java).getConfirmOrder(header, param)
            ?.compose(RxSchedulers.io_main())
    }

    override fun placeOrder(
        header: Map<String, String>?,
        param: RequestBody?
    ): Observable<ApiResult<OrderBean>?>? {
        return FrameConst.apiService(HttpApi::class.java).placeOrder(header, param)
            ?.compose(RxSchedulers.io_main())
    }

//    override fun getCouponList(
//        header: Map<String, String>?,
//        body: RequestBody?
//    ): Observable<ApiResult<List<CouponListBean>?>?>? {
//        return FrameConst.apiService(HttpApi::class.java).couponList(header, body)
//            ?.compose(RxSchedulers.io_main())
//    }
}