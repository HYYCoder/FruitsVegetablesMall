package com.huangyiyang.fruitsvegetablesmall.ui.goods.presenter

import com.huangyiyang.fruitsvegetablesmall.MVPApplication
import com.huangyiyang.fruitsvegetablesmall.mvp.http.ApiCallBack
import com.huangyiyang.fruitsvegetablesmall.mvp.http.ServerException
import com.huangyiyang.fruitsvegetablesmall.bean.GoodsDetailBean
import com.huangyiyang.fruitsvegetablesmall.bean.ShoppingCarCountBean
import com.huangyiyang.fruitsvegetablesmall.ui.goods.contract.GoodsDetailActivityContract
import com.huangyiyang.fruitsvegetablesmall.mvp.util.ToastUtil
import okhttp3.RequestBody

class GoodsDetailActivityPresenter : GoodsDetailActivityContract.GoodsDetailActivityPresenter(){

    override fun getGoodsDetail(header: Map<String, String>?, goodsId: String?) {
        mManager!!.add(mModel?.getGoodsDetail(header,goodsId)?.subscribe(object :
            ApiCallBack<GoodsDetailBean>(mContext) {

            override fun _onNext(t: GoodsDetailBean?, message: String?) {
                mView!!.setGoodsDetail(t)
            }

            override fun _onError(e: ServerException?) {
                ToastUtil.showLong(
                    mContext,
                    MVPApplication.getToastContent(mContext, e?.mErrorCode,e?.mErrorMsg).toString()
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

    override fun getShoppingCarCount(header: Map<String, String>?) {
        mManager!!.add(mModel?.getShoppingCarCount(header)?.subscribe(object :
            ApiCallBack<ShoppingCarCountBean>(mContext) {

            override fun _onNext(t: ShoppingCarCountBean?, message: String?) {
                mView!!.setShoppingCarCount(t)
            }

            override fun _onError(e: ServerException?) {
                ToastUtil.showLong(
                    mContext,
                    MVPApplication.getToastContent(mContext, e?.mErrorCode,e?.mErrorMsg).toString()
                )
            }
        }))
    }
}