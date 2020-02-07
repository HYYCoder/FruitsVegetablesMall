package com.huangyiyang.fruitsvegetablesmall.ui.cassification.presenter

import com.huangyiyang.fruitsvegetablesmall.MVPApplication
import com.huangyiyang.fruitsvegetablesmall.api.ApiCallBack
import com.huangyiyang.fruitsvegetablesmall.api.ServerException
import com.huangyiyang.fruitsvegetablesmall.bean.GoodsDetailBean
import com.huangyiyang.fruitsvegetablesmall.ui.cassification.contract.ClassificationDetailFragmentContract
import com.huangyiyang.fruitsvegetablesmall.util.ToastUtil
import okhttp3.RequestBody

class ClassificationDetailFragmentPresenter : ClassificationDetailFragmentContract.MainClassificationFragmentPresenter(){
    override fun getCategoriesDetailList(
        header: Map<String, String>?,
        parame: Map<String, String>?
    ) {
        mManager!!.add(mModel!!.getCategoriesDetailList(header, parame)!!.subscribe(
            object :
                ApiCallBack<List<GoodsDetailBean>?>(mContext) {
                override fun _onNext(
                    categoryListBean: List<GoodsDetailBean>?,
                    message: String?
                ) {
                    mView!!.setCategoriesDetailList(categoryListBean)
                }

                override fun _onError(e: ServerException?) {
                    ToastUtil().showLong(
                        mContext,
                        MVPApplication.getToastContent(mContext, e?.mErrorCode, e?.mErrorMsg).toString()
                    )
                }
            }))
    }

    override fun addShoppingCar(header: Map<String, String>?, parame: RequestBody?) {
        mManager!!.add(mModel?.addShoppingCar(header,parame)?.subscribe(object :
            ApiCallBack<Void>(mContext) {

            override fun _onNext(t: Void?, message: String?) {
                mView!!.addShoppingCar()
            }

            override fun _onError(e: ServerException?) {
                ToastUtil().showLong(
                    mContext,
                    MVPApplication.getToastContent(mContext, e?.mErrorCode,e?.mErrorMsg).toString()
                )
            }
        }))
    }

    override fun getGoodsDetail(header: Map<String, String>?, id: String?) {
        mManager!!.add(mModel?.getGoodsDetail(header,id)?.subscribe(object :
            ApiCallBack<GoodsDetailBean>(mContext) {

            override fun _onNext(t: GoodsDetailBean?, message: String?) {
                mView!!.setGoodsDetail(t)
            }

            override fun _onError(e: ServerException?) {
                ToastUtil().showLong(
                    mContext,
                    MVPApplication.getToastContent(mContext, e?.mErrorCode,e?.mErrorMsg).toString()
                )
            }
        }))
    }

    override fun requestNextPage() {
        mManager!!.add(
            mModel!!.getCategoriesDetailList(
                header,
                mParamsMap
            )!!.subscribe(getCallBack())
        )
    }

}