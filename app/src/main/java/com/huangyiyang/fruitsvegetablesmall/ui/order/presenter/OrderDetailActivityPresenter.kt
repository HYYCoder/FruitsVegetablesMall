package com.huangyiyang.fruitsvegetablesmall.ui.order.presenter

import com.huangyiyang.fruitsvegetablesmall.MVPApplication
import com.huangyiyang.fruitsvegetablesmall.api.ApiCallBack
import com.huangyiyang.fruitsvegetablesmall.api.ServerException
import com.huangyiyang.fruitsvegetablesmall.bean.OrderDetailBean
import com.huangyiyang.fruitsvegetablesmall.ui.order.contract.OrderDetailActivityContract
import com.huangyiyang.fruitsvegetablesmall.util.ToastUtil
import okhttp3.RequestBody

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

    override fun updateOrderDetail(header: Map<String, String>?, id: Int, body: RequestBody?) {
        mManager!!.add(mModel?.updateOrderDetail(header, id, body)?.subscribe(
            object :
                ApiCallBack<Void>(mContext) {
                override fun _onNext(
                    t: Void?,
                    message: String?
                ) {
                    mView!!.updateSuccess()
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