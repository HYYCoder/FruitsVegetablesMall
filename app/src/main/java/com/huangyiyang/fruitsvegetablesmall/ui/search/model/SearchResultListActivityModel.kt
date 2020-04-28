package com.huangyiyang.fruitsvegetablesmall.ui.search.model

import com.huangyiyang.fruitsvegetablesmall.mvp.http.ApiResult
import com.huangyiyang.fruitsvegetablesmall.mvp.http.FrameConst
import com.huangyiyang.fruitsvegetablesmall.api.HttpApi
import com.huangyiyang.fruitsvegetablesmall.bean.GoodsDetailBean
import com.huangyiyang.fruitsvegetablesmall.bean.ShoppingCarCountBean
import com.huangyiyang.fruitsvegetablesmall.mvp.rxevent.RxSchedulers
import com.huangyiyang.fruitsvegetablesmall.ui.search.contract.SearchResultListActivityContract
import okhttp3.RequestBody
import rx.Observable

class SearchResultListActivityModel : SearchResultListActivityContract.SearchResultListActivityModel {
    override fun getGoodsResultList(
        header: Map<String, String>?,
        param: Map<String, String>?
    ): Observable<ApiResult<List<GoodsDetailBean>?>?>? {
        return FrameConst.apiService(HttpApi::class.java).getGoodsList(header,param)
            ?.compose(RxSchedulers.io_main())
    }

    override fun getShoppingCarCount(header: Map<String, String>?): Observable<ApiResult<ShoppingCarCountBean>?>? {
        return FrameConst.apiService(HttpApi::class.java).getShoppingCarCount(header)
            ?.compose(RxSchedulers.io_main())
    }

    override fun addShoppingCar(
        header: Map<String, String>?,
        param: RequestBody?
    ): Observable<ApiResult<Void>?>? {
        return FrameConst.apiService(HttpApi::class.java).addShoppingCar(header,param)
            ?.compose(RxSchedulers.io_main())
    }
}