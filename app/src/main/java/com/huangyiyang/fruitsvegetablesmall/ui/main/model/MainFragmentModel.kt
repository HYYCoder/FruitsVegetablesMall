package com.huangyiyang.fruitsvegetablesmall.ui.main.model

import com.huangyiyang.fruitsvegetablesmall.api.ApiResult
import com.huangyiyang.fruitsvegetablesmall.api.FrameConst
import com.huangyiyang.fruitsvegetablesmall.api.HttpApi
import com.huangyiyang.fruitsvegetablesmall.bean.CategoryListBean
import com.huangyiyang.fruitsvegetablesmall.bean.GoodsDetailBean
import com.huangyiyang.fruitsvegetablesmall.rxevent.RxSchedulers
import com.huangyiyang.fruitsvegetablesmall.ui.main.contract.MainFragmentContract
import com.huangyiyang.fruitsvegetablesmall.util.BannerUtil
import okhttp3.RequestBody
import rx.Observable

class MainFragmentModel : MainFragmentContract.MainFragmentModel{
    override fun getBannerList(header: Map<String, String>?, parame: Map<String, String>?): Observable<ApiResult<List<BannerUtil.DataBean>?>?>? {
        return FrameConst.apiService(HttpApi::class.java).getBannerList(header,parame)
            ?.compose(RxSchedulers.io_main())
    }

    override fun getRecommendGoodsList(
        header: Map<String, String>?,
        parame: Map<String, String>?
    ): Observable<ApiResult<List<GoodsDetailBean>?>?>? {
        return FrameConst.apiService(HttpApi::class.java).getGoodsList(header,parame)
            ?.compose(RxSchedulers.io_main())
    }

    override fun getCategoriesList(header: Map<String, String>?): Observable<ApiResult<List<CategoryListBean>?>?>? {
        return FrameConst.apiService(HttpApi::class.java).getCategoriesList(header)
            ?.compose(RxSchedulers.io_main())
    }

    override fun addShoppingCar(
        header: Map<String, String>?,
        parame: RequestBody?
    ): Observable<ApiResult<Void>?>? {
        return FrameConst.apiService(HttpApi::class.java).addShoppingCar(header,parame)
            ?.compose(RxSchedulers.io_main())
    }
}