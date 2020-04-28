package com.huangyiyang.fruitsvegetablesmall.ui.order.presenter

import com.huangyiyang.fruitsvegetablesmall.MVPApplication
import com.huangyiyang.fruitsvegetablesmall.mvp.http.ApiCallBack
import com.huangyiyang.fruitsvegetablesmall.mvp.http.ServerException
import com.huangyiyang.fruitsvegetablesmall.bean.ConfirmOrderBean
import com.huangyiyang.fruitsvegetablesmall.bean.OrderBean
import com.huangyiyang.fruitsvegetablesmall.ui.order.contract.ConfirmOrderActivityContract
import com.huangyiyang.fruitsvegetablesmall.mvp.util.ToastUtil
import okhttp3.RequestBody

class ConfirmOrderActivityPresenter : ConfirmOrderActivityContract.ConfirmOrderActivityPresenter(){

    override fun placeOrder(header: Map<String, String>?, param: RequestBody?) {
        mManager!!.add(mModel?.placeOrder(header, param)?.subscribe(
            object :
                ApiCallBack<OrderBean>(mContext) {
                override fun _onNext(
                    t: OrderBean?,
                    message: String?
                ) {
                    ToastUtil.showShort(mContext, "下单成功")
                    mView!!.setOrderConplete(t)
                }

                override fun _onError(e: ServerException?) {
                    ToastUtil.showLong(
                        mContext,
                        MVPApplication.getToastContent(mContext, e?.mErrorCode, e?.mErrorMsg).toString()
                    )
                }
            }))
    }

    override fun getConfirmOrder(header: Map<String, String>?, param: RequestBody?) {
        mManager!!.add(mModel?.getConfirmOrder(header, param)?.subscribe(
            object :
                ApiCallBack<ConfirmOrderBean>(mContext) {
                override fun _onNext(
                    t: ConfirmOrderBean?,
                    message: String?
                ) {
                    mView!!.setConfirmOrder(t)
                }

                override fun _onError(e: ServerException?) {
                    ToastUtil.showLong(
                        mContext,
                        MVPApplication.getToastContent(mContext, e?.mErrorCode, e?.mErrorMsg).toString()
                    )
                }
            }))
    }

    //    override fun getCouponList(header: Map<String, String>?, param: RequestBody?) {
//        mManager!!.add(mModel?.getCouponList(header, param)?.subscribe(
//            object :
//                ApiCallBack<List<CouponListBean>?>(mContext) {
//                override fun _onNext(
//                    t: List<CouponListBean>?,
//                    message: String?
//                ) {
//                    ToastUtil.showShort(mContext, "下单成功")
//                    mView!!.setCouponList(t)
//                }
//
//                override fun _onError(e: ServerException?) {
//                    ToastUtil.showLong(
//                        mContext,
//                        MVPApplication.getToastContent(mContext, e?.mErrorCode, e?.mErrorMsg).toString()
//                    )
//                }
//            }))
//    }
}