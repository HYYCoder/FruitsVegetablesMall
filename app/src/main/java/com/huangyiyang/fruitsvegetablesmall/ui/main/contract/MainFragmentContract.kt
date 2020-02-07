package com.huangyiyang.fruitsvegetablesmall.ui.main.contract

import com.huangyiyang.fruitsvegetablesmall.api.ApiResult
import com.huangyiyang.fruitsvegetablesmall.bean.CategoryListBean
import com.huangyiyang.fruitsvegetablesmall.bean.GoodsDetailBean
import com.huangyiyang.fruitsvegetablesmall.mvp.load.LoadListPresenter
import com.huangyiyang.fruitsvegetablesmall.mvp.model.BaseModelInterface
import com.huangyiyang.fruitsvegetablesmall.mvp.view.BaseViewInterface
import com.huangyiyang.fruitsvegetablesmall.util.BannerUtil
import okhttp3.RequestBody
import rx.Observable

interface MainFragmentContract {

    interface MainFragmentModel : BaseModelInterface {

        fun getBannerList(header: Map<String, String>?,
                          parame: Map<String, String>?
        ): Observable<ApiResult<List<BannerUtil.Companion.DataBean>?>?>?

        fun getRecommendGoodsList(
            header: Map<String, String>?,
            param: Map<String, String>?
        ): Observable<ApiResult<List<GoodsDetailBean>?>?>?

        fun getCategoriesList(header: Map<String, String>?): Observable<ApiResult<List<CategoryListBean>?>?>?

        fun addShoppingCar(
            header: Map<String, String>?,
            param: RequestBody?
        ): Observable<ApiResult<Void>?>?
    }

    interface MainFragmentView : BaseViewInterface {

        fun setBannerList(bannerList: List<BannerUtil.Companion.DataBean>?)

        fun setRecommendGoodsList(goodsDetailBeanList: List<GoodsDetailBean>?)

        fun setCategoriesList(categoryListBean: List<CategoryListBean>?)

        fun addShoppingCar()
    }

    abstract class MainFragmentPresenter : LoadListPresenter<GoodsDetailBean, MainFragmentModel, MainFragmentView>() {

        abstract fun getBannerList(
            header: Map<String, String>?,
            param: Map<String, String>?
        )

        //abstract fun getRecommendGoodsList(header: Map<String?, String?>?,parame: Map<String?, String?>?)

        abstract fun getCategoriesList(header: Map<String, String>?)

        abstract fun addShoppingCar(
            header: Map<String, String>?,
            param: RequestBody?
        )
    }

}