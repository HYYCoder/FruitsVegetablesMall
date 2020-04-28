package com.huangyiyang.fruitsvegetablesmall.ui.goods.contract

import com.huangyiyang.fruitsvegetablesmall.mvp.http.ApiResult
import com.huangyiyang.fruitsvegetablesmall.bean.GoodsDetailBean
import com.huangyiyang.fruitsvegetablesmall.bean.ShoppingCarCountBean
import com.huangyiyang.fruitsvegetablesmall.mvp.model.BaseModelInterface
import com.huangyiyang.fruitsvegetablesmall.mvp.presenter.BasePresenter
import com.huangyiyang.fruitsvegetablesmall.mvp.view.BaseViewInterface
import okhttp3.RequestBody
import rx.Observable

interface GoodsDetailActivityContract {

    interface GoodsDetailActivityModel : BaseModelInterface{

        fun getGoodsDetail(
            header: Map<String, String>?,
            goodsId: String?
        ): Observable<ApiResult<GoodsDetailBean>?>?

        fun addShoppingCar(
            header: Map<String, String>?,
            param: RequestBody?
        ): Observable<ApiResult<Void>?>?

        fun getShoppingCarCount(header: Map<String, String>?): Observable<ApiResult<ShoppingCarCountBean>?>?
    }

    interface GoodsDetailActivityView : BaseViewInterface{

        fun setGoodsDetail(bean: GoodsDetailBean?)

        fun addShoppingCar()

        fun setShoppingCarCount(bean: ShoppingCarCountBean?)
    }

    abstract class GoodsDetailActivityPresenter : BasePresenter<GoodsDetailActivityModel, GoodsDetailActivityView>(){

        abstract fun getGoodsDetail(
            header: Map<String, String>?,
            goodsId: String?
        )

        abstract fun addShoppingCar(
            header: Map<String, String>?,
            param: RequestBody?
        )

        abstract fun getShoppingCarCount(header: Map<String, String>?)
    }
}