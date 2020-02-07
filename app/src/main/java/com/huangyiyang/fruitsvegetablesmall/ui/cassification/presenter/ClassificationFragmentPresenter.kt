package com.huangyiyang.fruitsvegetablesmall.ui.cassification.presenter

import com.huangyiyang.fruitsvegetablesmall.MVPApplication
import com.huangyiyang.fruitsvegetablesmall.api.ApiCallBack
import com.huangyiyang.fruitsvegetablesmall.api.ServerException
import com.huangyiyang.fruitsvegetablesmall.bean.CategoryListBean
import com.huangyiyang.fruitsvegetablesmall.ui.cassification.contract.ClassificationFragmentContract
import com.huangyiyang.fruitsvegetablesmall.util.ToastUtil

class ClassificationFragmentPresenter :
    ClassificationFragmentContract.ClassificationFragmentPresenter() {
    override fun getCategoriesList(header: Map<String, String>?) {
        mManager!!.add(mModel?.getCategoriesList(header)?.subscribe(object :
            ApiCallBack<List<CategoryListBean>?>(mContext) {

            override fun _onNext(categoryListBean: List<CategoryListBean>?, message: String?) {
                mView!!.setCategoriesList(categoryListBean)
            }

            override fun _onError(e: ServerException?) {
                ToastUtil().showLong(
                    mContext,
                    MVPApplication.getToastContent(mContext, e?.mErrorCode,e?.mErrorMsg).toString()
                )
            }
        }))
    }

}