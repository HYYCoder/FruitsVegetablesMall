package com.huangyiyang.fruitsvegetablesmall.ui.order.presenter

import com.huangyiyang.fruitsvegetablesmall.MVPApplication
import com.huangyiyang.fruitsvegetablesmall.api.ApiCallBack
import com.huangyiyang.fruitsvegetablesmall.api.ServerException
import com.huangyiyang.fruitsvegetablesmall.bean.OrderDetailBean
import com.huangyiyang.fruitsvegetablesmall.ui.order.contract.OrderDetailActivityContract
import com.huangyiyang.fruitsvegetablesmall.util.ToastUtil

class OrderDetailActivityPresenter : OrderDetailActivityContract.OrderDetailActivityPresenter(){

    override fun getOrderDetail(header: Map<String, String>?, orderId: String?) {
        mManager!!.add(mModel?.getOrderDetail(header, orderId)?.subscribe(
            object :
                ApiCallBack<OrderDetailBean>(mContext) {
                override fun _onNext(
                    t: OrderDetailBean?,
                    message: String?
                ) {
                    mView!!.setOrderDetail(t)
                }

                override fun _onError(e: ServerException?) {
                    ToastUtil.showLong(
                        mContext,
                        MVPApplication.getToastContent(mContext, e?.mErrorCode, e?.mErrorMsg).toString()
                    )
                }
            }))
    }
}