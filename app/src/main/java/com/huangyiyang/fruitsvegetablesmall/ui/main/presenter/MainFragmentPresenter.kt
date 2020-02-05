package com.huangyiyang.fruitsvegetablesmall.ui.main.presenter

import com.huangyiyang.fruitsvegetablesmall.MVPApplication
import com.huangyiyang.fruitsvegetablesmall.api.ApiCallBack
import com.huangyiyang.fruitsvegetablesmall.api.ServerException
import com.huangyiyang.fruitsvegetablesmall.bean.CategoryListBean
import com.huangyiyang.fruitsvegetablesmall.bean.RecommendGoodsBean
import com.huangyiyang.fruitsvegetablesmall.ui.main.contract.MainFragmentContract
import com.huangyiyang.fruitsvegetablesmall.util.BannerUtil
import com.huangyiyang.fruitsvegetablesmall.util.ToastUtil
import okhttp3.RequestBody

class MainFragmentPresenter : MainFragmentContract.MainFragmentPresenter() {

    override fun getBannerList(header: Map<String, String>?, parame: Map<String, String>?) {
        mManager!!.add(mModel?.getBannerList(header,parame)?.subscribe(object :
            ApiCallBack<List<BannerUtil.DataBean>?>(mContext) {
            override fun _onNext(bannerList: List<BannerUtil.DataBean>?, message: String?) {
                mView!!.setBannerList(bannerList)
            }

            override fun _onError(e: ServerException?) {
                ToastUtil().showLong(
                    mContext,
                    MVPApplication.getToastContent(mContext, e?.mErrorCode,e?.mErrorMsg).toString()
                )
            }
        }))
    }

    override fun getCategoriesList(header: Map<String, String>?) {
        mManager!!.add(mModel?.getCategoriesList(header)?.subscribe(object :
            ApiCallBack<List<CategoryListBean>?>(mContext) {

            override fun _onNext(categoryListBean: List<CategoryListBean>?, message: String?) {
                mView!!.setCategoriesList(categoryListBean)
            }

            override fun _onError(e: ServerException?) {
                ToastUtil().showLong(
                    mContext,
                    MVPApplication.getToastContent(mContext, e?.mErrorCode,e?.mErrorMsg).toString()
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

    override fun requestNextPage() {
        mManager!!.add(mModel?.getRecommendGoodsList(header, mParamsMap)?.subscribe(getCallBack()))

    }

    override fun errorBack(e: ServerException?) {
        ToastUtil().showLong(
            mContext,
            MVPApplication.getToastContent(mContext, e?.mErrorCode, e?.mErrorMsg).toString()
        )
        mView!!.setRecommendGoodsList(null)
    }

    override fun response(bean: List<RecommendGoodsBean>?) {
        mView!!.setRecommendGoodsList(bean)
    }
}