package com.huangyiyang.fruitsvegetablesmall.ui.order.model

import com.huangyiyang.fruitsvegetablesmall.api.ApiResult
import com.huangyiyang.fruitsvegetablesmall.api.FrameConst
import com.huangyiyang.fruitsvegetablesmall.api.HttpApi
import com.huangyiyang.fruitsvegetablesmall.bean.OrderListBean
import com.huangyiyang.fruitsvegetablesmall.rxevent.RxSchedulers
import com.huangyiyang.fruitsvegetablesmall.ui.order.contract.OrderListFragmentContract
import rx.Observable

class OrderListFragmentModel : OrderListFragmentContract.OrderListFragmentModel{

    override fun getOrderList(
        header: Map<String, String>?,
        param: Map<String, String>?
    ): Observable<ApiResult<List<OrderListBean>?>?>? {
        return FrameConst.apiService(HttpApi::class.java).getOrderList(header,param)
            ?.compose(RxSchedulers.io_main())
    }
}