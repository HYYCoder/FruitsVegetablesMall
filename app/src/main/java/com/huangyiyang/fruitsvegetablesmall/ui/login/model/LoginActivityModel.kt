package com.huangyiyang.fruitsvegetablesmall.ui.login.model

import com.huangyiyang.fruitsvegetablesmall.api.ApiResult
import com.huangyiyang.fruitsvegetablesmall.api.FrameConst
import com.huangyiyang.fruitsvegetablesmall.api.HttpApi
import com.huangyiyang.fruitsvegetablesmall.bean.LoginBean
import com.huangyiyang.fruitsvegetablesmall.rxevent.RxSchedulers
import com.huangyiyang.fruitsvegetablesmall.ui.login.contract.LoginActivityContract
import okhttp3.RequestBody
import rx.Observable

class LoginActivityModel : LoginActivityContract.LoginActivityModel{

    override fun getLogin(param: RequestBody?): Observable<ApiResult<LoginBean>?>? {
        return FrameConst.apiService(HttpApi::class.java).login(param)
            ?.compose(RxSchedulers.io_main())
    }

}