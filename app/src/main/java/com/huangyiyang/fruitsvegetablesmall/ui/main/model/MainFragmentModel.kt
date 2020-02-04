package com.huangyiyang.fruitsvegetablesmall.ui.main.model

import com.huangyiyang.fruitsvegetablesmall.api.ApiResult
import com.huangyiyang.fruitsvegetablesmall.api.FrameConst
import com.huangyiyang.fruitsvegetablesmall.api.HttpApi
import com.huangyiyang.fruitsvegetablesmall.bean.CategoryListBean
import com.huangyiyang.fruitsvegetablesmall.bean.RecommendGoodsBean
import com.huangyiyang.fruitsvegetablesmall.rxevent.RxSchedulers
import com.huangyiyang.fruitsvegetablesmall.ui.main.contract.MainFragmentContract
import rx.Observable

class MainFragmentModel : MainFragmentContract.MainFragmentModel{
    override fun getRecommendGoodsList(
        header: Map<String?, String?>?,
        parame: Map<String?, String?>?
    ): Observable<ApiResult<List<RecommendGoodsBean?>?>?>? {
        return FrameConst.apiService(HttpApi::class.java).getRecommendList(header,parame)
            ?.compose(RxSchedulers.io_main())
    }

    override fun getCategoriesList(header: Map<String?, String?>?): Observable<ApiResult<List<CategoryListBean?>?>?>? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}