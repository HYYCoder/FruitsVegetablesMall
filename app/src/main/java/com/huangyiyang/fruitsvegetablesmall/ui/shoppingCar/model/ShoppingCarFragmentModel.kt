package com.huangyiyang.fruitsvegetablesmall.ui.shoppingCar.model

import com.huangyiyang.fruitsvegetablesmall.api.ApiResult
import com.huangyiyang.fruitsvegetablesmall.api.FrameConst
import com.huangyiyang.fruitsvegetablesmall.api.HttpApi
import com.huangyiyang.fruitsvegetablesmall.bean.ShoppingCarCountBean
import com.huangyiyang.fruitsvegetablesmall.bean.ShoppingCarListBean
import com.huangyiyang.fruitsvegetablesmall.rxevent.RxSchedulers
import com.huangyiyang.fruitsvegetablesmall.ui.shoppingCar.contract.ShoppingCarFragmentContract
import okhttp3.RequestBody
import rx.Observable

class ShoppingCarFragmentModel : ShoppingCarFragmentContract.ShoppingCarFragmentModel{

    override fun getShoppingCarList(header: Map<String, String>?): Observable<ApiResult<ShoppingCarListBean>?>? {
        return FrameConst.apiService(HttpApi::class.java).getShoppingCarList(header)
            ?.compose(RxSchedulers.io_main())
    }

    override fun deleteShoppingCarGoods(
        header: Map<String, String>?,
        param: RequestBody?
    ): Observable<ApiResult<Void>?>? {
        return FrameConst.apiService(HttpApi::class.java).deleteShoppingCarGoods(header,param)
            ?.compose(RxSchedulers.io_main())
    }

    override fun deleteShoppingCarGood(
        header: Map<String, String>?,
        param: String?
    ): Observable<ApiResult<Void>?>? {
        return FrameConst.apiService(HttpApi::class.java).deleteShoppingCarGood(header,param)
            ?.compose(RxSchedulers.io_main())
    }

    override fun updateShoppingCarCount(
        header: Map<String, String>?,
        id: Int,
        body: RequestBody?
    ): Observable<ApiResult<Void>?>? {
        return FrameConst.apiService(HttpApi::class.java).updateShoppingCarCount(header, id.toString(), body)
            ?.compose(RxSchedulers.io_main())
    }

    override fun getShoppingCarCount(header: Map<String, String>?): Observable<ApiResult<ShoppingCarCountBean>?>? {
        return FrameConst.apiService(HttpApi::class.java).getShoppingCarCount(header)
            ?.compose(RxSchedulers.io_main())
    }
}