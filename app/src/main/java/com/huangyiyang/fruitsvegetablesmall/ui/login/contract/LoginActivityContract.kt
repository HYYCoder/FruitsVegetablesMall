package com.huangyiyang.fruitsvegetablesmall.ui.login.contract

import com.huangyiyang.fruitsvegetablesmall.api.ApiResult
import com.huangyiyang.fruitsvegetablesmall.bean.LoginBean
import com.huangyiyang.fruitsvegetablesmall.mvp.model.BaseModelInterface
import com.huangyiyang.fruitsvegetablesmall.mvp.presenter.BasePresenter
import com.huangyiyang.fruitsvegetablesmall.mvp.view.BaseViewInterface
import okhttp3.RequestBody
import rx.Observable

interface LoginActivityContract {
    interface LoginActivityModel : BaseModelInterface{
        fun getLogin(param: RequestBody?): Observable<ApiResult<LoginBean>?>?
    }

    interface LoginActivityView : BaseViewInterface{
        fun setLogin(isBoundMobile: Boolean)
    }

    abstract class LoginActivityPresenter : BasePresenter<LoginActivityModel, LoginActivityView>(){
        abstract fun getLogin(param: RequestBody?)
    }

}