package com.huangyiyang.fruitsvegetablesmall.ui.login.contract

import com.huangyiyang.fruitsvegetablesmall.mvp.http.ApiResult
import com.huangyiyang.fruitsvegetablesmall.bean.LoginBean
import com.huangyiyang.fruitsvegetablesmall.mvp.model.BaseModelInterface
import com.huangyiyang.fruitsvegetablesmall.mvp.presenter.BasePresenter
import com.huangyiyang.fruitsvegetablesmall.mvp.view.BaseViewInterface
import okhttp3.RequestBody
import rx.Observable

interface RegisterActivityContract {

    interface RegisterActivityModel : BaseModelInterface{
        fun getRegister(param: RequestBody?): Observable<ApiResult<LoginBean>?>?
    }

    interface RegisterActivityView : BaseViewInterface{
        fun setRegister(isBoundMobile: Boolean)
    }

    abstract class RegisterActivityPresenter : BasePresenter<RegisterActivityModel, RegisterActivityView>(){
        abstract fun getRegister(param: RequestBody?)
    }

}