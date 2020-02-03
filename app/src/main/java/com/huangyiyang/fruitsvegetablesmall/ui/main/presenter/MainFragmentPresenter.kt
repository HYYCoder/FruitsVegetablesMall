package com.huangyiyang.fruitsvegetablesmall.ui.main.presenter

import com.huangyiyang.fruitsvegetablesmall.api.ApiCallBack
import com.huangyiyang.fruitsvegetablesmall.api.ServerException
import com.huangyiyang.fruitsvegetablesmall.bean.RecommendGoodsBean
import com.huangyiyang.fruitsvegetablesmall.ui.main.contract.MainFragmentContract
import com.huangyiyang.fruitsvegetablesmall.util.ToastUtil

class MainFragmentPresenter : MainFragmentContract.MainFragmentPresenter() {
    override fun getRecommendGoodsList(header: Map<String?, String?>?) {
        mManager!!.add(mModel?.getRecommendGoodsList(header)?.subscribe(object :
            ApiCallBack<List<RecommendGoodsBean?>?>(mContext) {
            override fun _onNext(
                goodsList: List<RecommendGoodsBean?>?,
                message: String?
            ) {
                mView!!.setRecommendGoodsListInfo(goodsList)
            }

            override fun _onError(exception: ServerException?) {
                ToastUtil().showLong(
                    mContext,
//                    YGFShopMVPApplication.getToastContent(mContext, e.mErrorCode)
                    ""
                )
            }
        }))
    }

    override fun getCategoriesList(header: Map<String?, String?>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun requestNextPage() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}