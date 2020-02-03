package com.huangyiyang.fruitsvegetablesmall.ui.main.contract

import com.huangyiyang.fruitsvegetablesmall.api.ApiResult
import com.huangyiyang.fruitsvegetablesmall.bean.CategoryListBean
import com.huangyiyang.fruitsvegetablesmall.bean.RecommendGoodsBean
import com.huangyiyang.fruitsvegetablesmall.mvp.load.LoadListPresenter
import com.huangyiyang.fruitsvegetablesmall.mvp.model.BaseModelInterface
import com.huangyiyang.fruitsvegetablesmall.mvp.view.BaseViewInterface
import rx.Observable

interface MainFragmentContract {
    interface MainFragmentModel : BaseModelInterface {
        fun getRecommendGoodsList(
            header: Map<String?, String?>?
        ): Observable<ApiResult<List<RecommendGoodsBean?>?>?>?
        fun getCategoriesList(header: Map<String?, String?>?): Observable<ApiResult<List<CategoryListBean?>?>?>?
    }

    interface MainFragmentView : BaseViewInterface {
        fun setRecommendGoodsListInfo(goodsBeanList: List<RecommendGoodsBean?>?)
        fun setCategoriesList(categoryListBean: List<CategoryListBean?>?)
    }

    abstract class MainFragmentPresenter :
        LoadListPresenter<RecommendGoodsBean, MainFragmentModel, MainFragmentView>() {
        abstract fun getRecommendGoodsList(header: Map<String?, String?>?)
        abstract fun getCategoriesList(header: Map<String?, String?>?)
    }

}