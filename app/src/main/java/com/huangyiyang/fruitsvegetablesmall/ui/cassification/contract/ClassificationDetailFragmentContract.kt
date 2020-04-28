package com.huangyiyang.fruitsvegetablesmall.ui.cassification.contract

import com.huangyiyang.fruitsvegetablesmall.mvp.http.ApiResult
import com.huangyiyang.fruitsvegetablesmall.bean.GoodsDetailBean
import com.huangyiyang.fruitsvegetablesmall.mvp.load.LoadListPresenter
import com.huangyiyang.fruitsvegetablesmall.mvp.model.BaseModelInterface
import com.huangyiyang.fruitsvegetablesmall.mvp.view.BaseViewInterface
import okhttp3.RequestBody
import rx.Observable

interface ClassificationDetailFragmentContract {

    interface ClassificationDetailFragmentModel : BaseModelInterface{
        fun getCategoriesDetailList(
            header: Map<String, String>?,
            parame: Map<String, String>?
        ): Observable<ApiResult<List<GoodsDetailBean>?>?>?

        fun addShoppingCar(
            header: Map<String, String>?,
            parame: RequestBody?
        ): Observable<ApiResult<Void>?>?

        fun getGoodsDetail(
            header: Map<String, String>?,
            goodsId: String?
        ): Observable<ApiResult<GoodsDetailBean>?>?
    }

    interface ClassificationDetailFragmentView : BaseViewInterface{
        fun setCategoriesDetailList(categoryListBean: List<GoodsDetailBean>?)
        fun addShoppingCar()
        fun setGoodsDetail(bean: GoodsDetailBean?)
    }

    abstract class MainClassificationFragmentPresenter : LoadListPresenter<GoodsDetailBean, ClassificationDetailFragmentModel, ClassificationDetailFragmentView>() {
        abstract fun getCategoriesDetailList(
            header: Map<String, String>?,
            parame: Map<String, String>?
        )

        abstract fun addShoppingCar(
            header: Map<String, String>?,
            parame: RequestBody?
        )

        abstract fun getGoodsDetail(
            header: Map<String, String>?,
            id: String?
        )
    }

}