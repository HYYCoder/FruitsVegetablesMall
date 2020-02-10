package com.huangyiyang.fruitsvegetablesmall.ui.cassification.model

import com.huangyiyang.fruitsvegetablesmall.api.ApiResult
import com.huangyiyang.fruitsvegetablesmall.api.FrameConst
import com.huangyiyang.fruitsvegetablesmall.api.HttpApi
import com.huangyiyang.fruitsvegetablesmall.bean.GoodsDetailBean
import com.huangyiyang.fruitsvegetablesmall.rxevent.RxSchedulers
import com.huangyiyang.fruitsvegetablesmall.ui.cassification.contract.ClassificationDetailFragmentContract
import okhttp3.RequestBody
import rx.Observable

class ClassificationDetailFragmentModel : ClassificationDetailFragmentContract.ClassificationDetailFragmentModel{

    override fun getCategoriesDetailList(
        header: Map<String, String>?,
        param: Map<String, String>?
    ): Observable<ApiResult<List<GoodsDetailBean>?>?>? {
        return FrameConst.apiService(HttpApi::class.java).getGoodsList(header,param)
            ?.compose(RxSchedulers.io_main())
    }

    override fun addShoppingCar(
        header: Map<String, String>?,
        param: RequestBody?
    ): Observable<ApiResult<Void>?>? {
        return FrameConst.apiService(HttpApi::class.java).addShoppingCar(header,param)
            ?.compose(RxSchedulers.io_main())
    }

    override fun getGoodsDetail(
        header: Map<String, String>?,
        id: String?
    ): Observable<ApiResult<GoodsDetailBean>?>? {
        return FrameConst.apiService(HttpApi::class.java).getGoodsDetail(header,id)
            ?.compose(RxSchedulers.io_main())
    }
}