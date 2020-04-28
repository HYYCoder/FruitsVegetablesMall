package com.huangyiyang.fruitsvegetablesmall.ui.cassification.contract

import com.huangyiyang.fruitsvegetablesmall.mvp.http.ApiResult
import com.huangyiyang.fruitsvegetablesmall.bean.CategoryListBean
import com.huangyiyang.fruitsvegetablesmall.mvp.model.BaseModelInterface
import com.huangyiyang.fruitsvegetablesmall.mvp.presenter.BasePresenter
import com.huangyiyang.fruitsvegetablesmall.mvp.view.BaseViewInterface
import rx.Observable

interface ClassificationFragmentContract {

    interface ClassificationFragmentModel : BaseModelInterface {
        fun getCategoriesList(header: Map<String, String>?): Observable<ApiResult<List<CategoryListBean>?>?>?
    }

    interface ClassificationFragmentView : BaseViewInterface {
        fun setCategoriesList(categoryListBean: List<CategoryListBean>?)
    }

    abstract class ClassificationFragmentPresenter :
        BasePresenter<ClassificationFragmentModel, ClassificationFragmentView>() {
        abstract fun getCategoriesList(header: Map<String, String>?)
    }

}