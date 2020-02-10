package com.huangyiyang.fruitsvegetablesmall.ui.goods.model

import com.huangyiyang.fruitsvegetablesmall.api.ApiResult
import com.huangyiyang.fruitsvegetablesmall.api.FrameConst
import com.huangyiyang.fruitsvegetablesmall.api.HttpApi
import com.huangyiyang.fruitsvegetablesmall.bean.GoodsDetailBean
import com.huangyiyang.fruitsvegetablesmall.bean.ShoppingCarCountBean
import com.huangyiyang.fruitsvegetablesmall.rxevent.RxSchedulers
import com.huangyiyang.fruitsvegetablesmall.ui.goods.contract.GoodsDetailActivityContract
import okhttp3.RequestBody
import rx.Observable

class GoodsDetailActivityModel : GoodsDetailActivityContract.GoodsDetailActivityModel{

    override fun getGoodsDetail(
        header: Map<String, String>?,
        goodsId: String?
    ): Observable<ApiResult<GoodsDetailBean>?>? {
        return FrameConst.apiService(HttpApi::class.java).getGoodsDetail(header,goodsId)
            ?.compose(RxSchedulers.io_main())
    }

    override fun addShoppingCar(
        header: Map<String, String>?,
        param: RequestBody?
    ): Observable<ApiResult<Void>?>? {
        return FrameConst.apiService(HttpApi::class.java).addShoppingCar(header,param)
            ?.compose(RxSchedulers.io_main())
    }

    override fun getShoppingCarCount(header: Map<String, String>?): Observable<ApiResult<ShoppingCarCountBean>?>? {
        return FrameConst.apiService(HttpApi::class.java).getShoppingCarCount(header)
            ?.compose(RxSchedulers.io_main())
    }
}