package com.huangyiyang.fruitsvegetablesmall.ui.login.model

import com.huangyiyang.fruitsvegetablesmall.mvp.http.ApiResult
import com.huangyiyang.fruitsvegetablesmall.mvp.http.FrameConst
import com.huangyiyang.fruitsvegetablesmall.api.HttpApi
import com.huangyiyang.fruitsvegetablesmall.bean.LoginBean
import com.huangyiyang.fruitsvegetablesmall.mvp.rxevent.RxSchedulers
import com.huangyiyang.fruitsvegetablesmall.ui.login.contract.RegisterActivityContract
import okhttp3.RequestBody
import rx.Observable

class RegisterActivityModel : RegisterActivityContract.RegisterActivityModel{

    override fun getRegister(param: RequestBody?): Observable<ApiResult<LoginBean>?>? {
        return FrameConst.apiService(HttpApi::class.java).register(param)
            ?.compose(RxSchedulers.io_main())
    }

}