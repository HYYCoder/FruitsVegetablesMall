package com.huangyiyang.fruitsvegetablesmall.ui.main.model

import com.huangyiyang.fruitsvegetablesmall.mvp.http.ApiResult
import com.huangyiyang.fruitsvegetablesmall.mvp.http.FrameConst
import com.huangyiyang.fruitsvegetablesmall.api.HttpApi
import com.huangyiyang.fruitsvegetablesmall.bean.CategoryListBean
import com.huangyiyang.fruitsvegetablesmall.bean.GoodsDetailBean
import com.huangyiyang.fruitsvegetablesmall.mvp.rxevent.RxSchedulers
import com.huangyiyang.fruitsvegetablesmall.ui.main.contract.MainFragmentContract
import com.huangyiyang.fruitsvegetablesmall.mvp.util.BannerUtil
import okhttp3.RequestBody
import rx.Observable

class MainFragmentModel : MainFragmentContract.MainFragmentModel{

    override fun getBannerList(header: Map<String, String>?, param: Map<String, String>?): Observable<ApiResult<List<BannerUtil.Companion.DataBean>?>?>? {
        return FrameConst.apiService(HttpApi::class.java).getBannerList(header,param)
            ?.compose(RxSchedulers.io_main())
    }

    override fun getRecommendGoodsList(
        header: Map<String, String>?,
        param: Map<String, String>?
    ): Observable<ApiResult<List<GoodsDetailBean>?>?>? {
        return FrameConst.apiService(HttpApi::class.java).getGoodsList(header,param)
            ?.compose(RxSchedulers.io_main())
    }

    override fun getCategoriesList(header: Map<String, String>?): Observable<ApiResult<List<CategoryListBean>?>?>? {
        return FrameConst.apiService(HttpApi::class.java).getCategoriesList(header)
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