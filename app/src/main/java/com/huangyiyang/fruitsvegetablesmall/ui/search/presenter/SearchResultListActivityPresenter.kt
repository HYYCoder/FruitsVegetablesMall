package com.huangyiyang.fruitsvegetablesmall.ui.search.presenter

import com.huangyiyang.fruitsvegetablesmall.MVPApplication
import com.huangyiyang.fruitsvegetablesmall.api.ApiCallBack
import com.huangyiyang.fruitsvegetablesmall.api.ServerException
import com.huangyiyang.fruitsvegetablesmall.bean.ShoppingCarCountBean
import com.huangyiyang.fruitsvegetablesmall.ui.search.contract.SearchResultListActivityContract
import com.huangyiyang.fruitsvegetablesmall.util.ToastUtil
import okhttp3.RequestBody

class SearchResultListActivityPresenter : SearchResultListActivityContract.SearchResultListActivityPresenter(){
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

    override fun addShoppingCar(header: Map<String, String>?, param: RequestBody?) {
        mManager!!.add(mModel?.addShoppingCar(header,param)?.subscribe(object :
            ApiCallBack<Void>(mContext) {

            override fun _onNext(t: Void?, message: String?) {
                mView!!.addShoppingCar()
                ToastUtil.showShort(mContext, "加入购物车成功")
            }

            override fun _onError(e: ServerException?) {
                ToastUtil.showLong(
                    mContext,
                    MVPApplication.getToastContent(mContext, e?.mErrorCode,e?.mErrorMsg).toString()
                )
            }
        }))
    }

    override fun requestNextPage() {
        mManager!!.add(mModel?.getGoodsResultList(header, mParamsMap)?.subscribe(getCallBack()))
    }

}