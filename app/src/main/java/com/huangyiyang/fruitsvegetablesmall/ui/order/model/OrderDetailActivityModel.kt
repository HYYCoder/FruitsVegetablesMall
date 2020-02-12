package com.huangyiyang.fruitsvegetablesmall.ui.order.model

import com.huangyiyang.fruitsvegetablesmall.api.ApiResult
import com.huangyiyang.fruitsvegetablesmall.api.FrameConst
import com.huangyiyang.fruitsvegetablesmall.api.HttpApi
import com.huangyiyang.fruitsvegetablesmall.bean.OrderDetailBean
import com.huangyiyang.fruitsvegetablesmall.rxevent.RxSchedulers
import com.huangyiyang.fruitsvegetablesmall.ui.order.contract.OrderDetailActivityContract
import rx.Observable

class OrderDetailActivityModel : OrderDetailActivityContract.OrderDetailActivityModel{

    override fun getOrderDetail(
        header: Map<String, String>?,
        id: String?
    ): Observable<ApiResult<OrderDetailBean>?>? {
        return FrameConst.apiService(HttpApi::class.java).getOrderDetail(header, id)
            ?.compose(RxSchedulers.io_main())
    }
}