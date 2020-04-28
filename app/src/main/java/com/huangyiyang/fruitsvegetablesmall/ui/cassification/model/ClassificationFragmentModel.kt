package com.huangyiyang.fruitsvegetablesmall.ui.cassification.model

import com.huangyiyang.fruitsvegetablesmall.mvp.http.ApiResult
import com.huangyiyang.fruitsvegetablesmall.mvp.http.FrameConst
import com.huangyiyang.fruitsvegetablesmall.api.HttpApi
import com.huangyiyang.fruitsvegetablesmall.bean.CategoryListBean
import com.huangyiyang.fruitsvegetablesmall.mvp.rxevent.RxSchedulers
import com.huangyiyang.fruitsvegetablesmall.ui.cassification.contract.ClassificationFragmentContract
import rx.Observable

class ClassificationFragmentModel : ClassificationFragmentContract.ClassificationFragmentModel {

    override fun getCategoriesList(header: Map<String, String>?): Observable<ApiResult<List<CategoryListBean>?>?>? {
        return FrameConst.apiService(HttpApi::class.java).getCategoriesList(header)
            ?.compose(RxSchedulers.io_main())
    }
}