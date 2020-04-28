package com.huangyiyang.fruitsvegetablesmall.ui.search.contract

import com.huangyiyang.fruitsvegetablesmall.mvp.http.ApiResult
import com.huangyiyang.fruitsvegetablesmall.bean.GoodsDetailBean
import com.huangyiyang.fruitsvegetablesmall.bean.ShoppingCarCountBean
import com.huangyiyang.fruitsvegetablesmall.mvp.load.LoadListPresenter
import com.huangyiyang.fruitsvegetablesmall.mvp.model.BaseModelInterface
import com.huangyiyang.fruitsvegetablesmall.mvp.view.BaseViewInterface
import okhttp3.RequestBody
import rx.Observable

interface SearchResultListActivityContract {

    interface SearchResultListActivityModel : BaseModelInterface{
        fun getGoodsResultList(
            header: Map<String, String>?,
            param: Map<String, String>?
        ): Observable<ApiResult<List<GoodsDetailBean>?>?>?

        fun getShoppingCarCount(header: Map<String, String>?): Observable<ApiResult<ShoppingCarCountBean>?>?
        fun addShoppingCar(
            header: Map<String, String>?,
            param: RequestBody?
        ): Observable<ApiResult<Void>?>?
    }

    interface SearchResultListActivityView : BaseViewInterface {
        fun setRecommendGoodsListInfo(goodsBeanList: List<GoodsDetailBean?>?)
        fun addShoppingCar()
        fun setShoppingCarCount(bean: ShoppingCarCountBean?)
    }

    abstract class SearchResultListActivityPresenter :
        LoadListPresenter<GoodsDetailBean, SearchResultListActivityModel, SearchResultListActivityView>(){
        abstract fun getShoppingCarCount(header: Map<String, String>?)
        abstract fun addShoppingCar(
            header: Map<String, String>?,
            param: RequestBody?
        )
    }
}