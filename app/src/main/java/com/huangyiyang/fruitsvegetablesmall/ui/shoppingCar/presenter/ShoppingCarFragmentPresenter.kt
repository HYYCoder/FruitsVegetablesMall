package com.huangyiyang.fruitsvegetablesmall.ui.shoppingCar.presenter

import com.huangyiyang.fruitsvegetablesmall.MVPApplication
import com.huangyiyang.fruitsvegetablesmall.api.ApiCallBack
import com.huangyiyang.fruitsvegetablesmall.api.ServerException
import com.huangyiyang.fruitsvegetablesmall.bean.ShoppingCarCountBean
import com.huangyiyang.fruitsvegetablesmall.bean.ShoppingCarListBean
import com.huangyiyang.fruitsvegetablesmall.ui.shoppingCar.contract.ShoppingCarFragmentContract
import com.huangyiyang.fruitsvegetablesmall.util.ToastUtil
import okhttp3.RequestBody

class ShoppingCarFragmentPresenter : ShoppingCarFragmentContract.ShoppingCarFragmentPresenter(){

    override fun getShoppingCarList(header: Map<String, String>?) {
        mManager!!.add(mModel?.getShoppingCarList(header)?.subscribe(
            object :
                ApiCallBack<ShoppingCarListBean>(mContext) {
                override fun _onNext(
                    t: ShoppingCarListBean?,
                    message: String?
                ) {
                    mView!!.setShoppingCarList(t)
                }

                override fun _onError(e: ServerException?) {
                    ToastUtil.showLong(
                        mContext,
                        MVPApplication.getToastContent(mContext, e?.mErrorCode, e?.mErrorMsg).toString()
                    )
                }
            }))
    }

    override fun deleteShoppingCarGoods(
        header: Map<String, String>?,
        param: RequestBody?,
        toastStr: String?
    ) {
        mManager!!.add(mModel?.deleteShoppingCarGoods(header, param)?.subscribe(
            object :
                ApiCallBack<Void>(mContext) {
                override fun _onNext(
                    t: Void?,
                    message: String?
                ) {
                    mView!!.deleteSuccess()
                    if (toastStr == null) {
                        ToastUtil.showShort(mContext, "清空无效商品成功")
                    } else {
                        ToastUtil.showLong(mContext, toastStr)
                    }
                }

                override fun _onError(e: ServerException?) {
                    ToastUtil.showLong(
                        mContext,
                        MVPApplication.getToastContent(mContext, e?.mErrorCode, e?.mErrorMsg).toString()
                    )
                }
            }))
    }

    override fun deleteShoppingCarGood(header: Map<String, String>?, param: String?) {
        mManager!!.add(mModel?.deleteShoppingCarGood(header, param)?.subscribe(
            object :
                ApiCallBack<Void>(mContext) {
                override fun _onNext(
                    t: Void?,
                    message: String?
                ) {
                    mView!!.deleteSuccess()
                }

                override fun _onError(e: ServerException?) {
                    ToastUtil.showLong(
                        mContext,
                        MVPApplication.getToastContent(mContext, e?.mErrorCode, e?.mErrorMsg).toString()
                    )
                }
            }))
    }

    override fun updateShoppingCarCount(
        header: Map<String, String>?,
        id: Int,
        body: RequestBody?
    ) {
        mManager!!.add(mModel?.updateShoppingCarCount(header, id, body)?.subscribe(
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

    override fun getShoppingCarCount(header: Map<String, String>?) {
        mManager!!.add(mModel?.getShoppingCarCount(header)?.subscribe(
            object :
                ApiCallBack<ShoppingCarCountBean>(mContext) {
                override fun _onNext(
                    t: ShoppingCarCountBean?,
                    message: String?
                ) {
                    mView!!.setShoppingCarCount(t)
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