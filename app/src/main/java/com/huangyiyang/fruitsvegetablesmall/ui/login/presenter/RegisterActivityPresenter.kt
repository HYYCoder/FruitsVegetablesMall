package com.huangyiyang.fruitsvegetablesmall.ui.login.presenter

import com.huangyiyang.fruitsvegetablesmall.MVPApplication
import com.huangyiyang.fruitsvegetablesmall.mvp.http.ApiCallBack
import com.huangyiyang.fruitsvegetablesmall.mvp.http.ServerException
import com.huangyiyang.fruitsvegetablesmall.bean.LoginBean
import com.huangyiyang.fruitsvegetablesmall.mvp.manage.UserManager
import com.huangyiyang.fruitsvegetablesmall.ui.login.contract.RegisterActivityContract
import com.huangyiyang.fruitsvegetablesmall.mvp.util.ToastUtil
import okhttp3.RequestBody

class RegisterActivityPresenter : RegisterActivityContract.RegisterActivityPresenter(){

    override fun getRegister(param: RequestBody?) {
        mManager!!.add(mModel?.getRegister(param)?.subscribe(object :
            ApiCallBack<LoginBean>(mContext) {
            override fun _onNext(t: LoginBean?, message: String?) {
                UserManager.getInstance()?.saveUserId(t?.id.toString())
                UserManager.getInstance()?.saveUserLevel(t?.clientType)
                UserManager.getInstance()?.saveUserToken(t?.token)
                UserManager.getInstance()?.saveUserName(t?.name)
                UserManager.getInstance()?.saveUserPhone(t?.mobile)
                UserManager.getInstance()?.saveUserAddress(t?.address)
                if (t?.clientCode != null) {
                    UserManager.getInstance()?.saveUserCode(t?.clientCode)
                }
                if (t?.userName != null) {
                    UserManager.getInstance()?.saveUserCode2(t?.userName)
                }
                if (t?.clientId != null) {
                    UserManager.getInstance()?.saveClientId(t?.clientId)
                }
                if (t?.receivingPhone != null) {
                    UserManager.getInstance()?.saveReceivingPhone(t?.receivingPhone)
                }
                UserManager.getInstance()
                    ?.saveOrderOverdueTime("24")
                mView!!.setRegister(t?.isBoundMobile!!)
            }

            override fun _onError(e: ServerException?) {
                ToastUtil.showLong(
                    mContext,
                    MVPApplication.getToastContent(mContext, e?.mErrorCode,e?.mErrorMsg).toString()
                )
            }
        }))
    }
}