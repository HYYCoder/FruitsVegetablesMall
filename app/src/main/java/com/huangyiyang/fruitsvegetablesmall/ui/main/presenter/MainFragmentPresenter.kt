package com.huangyiyang.fruitsvegetablesmall.ui.main.presenter

import com.huangyiyang.fruitsvegetablesmall.MVPApplication
import com.huangyiyang.fruitsvegetablesmall.mvp.http.ApiCallBack
import com.huangyiyang.fruitsvegetablesmall.mvp.http.ServerException
import com.huangyiyang.fruitsvegetablesmall.bean.CategoryListBean
import com.huangyiyang.fruitsvegetablesmall.bean.GoodsDetailBean
import com.huangyiyang.fruitsvegetablesmall.ui.main.contract.MainFragmentContract
import com.huangyiyang.fruitsvegetablesmall.mvp.util.BannerUtil
import com.huangyiyang.fruitsvegetablesmall.mvp.util.ToastUtil
import okhttp3.RequestBody

class MainFragmentPresenter : MainFragmentContract.MainFragmentPresenter() {

    override fun getBannerList(header: Map<String, String>?, param: Map<String, String>?) {
        mManager!!.add(mModel?.getBannerList(header,param)?.subscribe(object :
            ApiCallBack<List<BannerUtil.Companion.DataBean>?>(mContext) {
            override fun _onNext(t: List<BannerUtil.Companion.DataBean>?, message: String?) {
                mView!!.setBannerList(t)
            }

            override fun _onError(e: ServerException?) {
                ToastUtil.showLong(
                    mContext,
                    MVPApplication.getToastContent(mContext, e?.mErrorCode,e?.mErrorMsg).toString()
                )
            }
        }))
    }

    override fun getCategoriesList(header: Map<String, String>?) {
        mManager!!.add(mModel?.getCategoriesList(header)?.subscribe(object :
            ApiCallBack<List<CategoryListBean>?>(mContext) {

            override fun _onNext(t: List<CategoryListBean>?, message: String?) {
                mView!!.setCategoriesList(t)
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

    override fun requestNextPage() {
        mManager!!.add(mModel?.getRecommendGoodsList(header, mParamsMap)?.subscribe(getCallBack()))

    }

    override fun errorBack(e: ServerException?) {
        ToastUtil.showLong(
            mContext,
            MVPApplication.getToastContent(mContext, e?.mErrorCode, e?.mErrorMsg).toString()
        )
        mView!!.setRecommendGoodsList(null)
    }

    override fun response(detailBean: List<GoodsDetailBean>?) {
        mView!!.setRecommendGoodsList(detailBean)
    }
}