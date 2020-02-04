package com.huangyiyang.fruitsvegetablesmall.api

import android.app.Activity
import android.content.Context
import com.huangyiyang.fruitsvegetablesmall.R
import com.huangyiyang.fruitsvegetablesmall.api.FrameConst
import com.huangyiyang.fruitsvegetablesmall.util.NetWorkUtil
import com.huangyiyang.fruitsvegetablesmall.view.main.LoadingDialog
import org.json.JSONException
import retrofit2.HttpException
import rx.Subscriber
import java.net.ConnectException


abstract class ApiCallBack<T> : Subscriber<ApiResult<T>> {

    private var mContext: Context? = null
    private var msg: String? = null
    private var mShowDialog = false

    private val UNAUTHORIZED = 401
    private val FORBIDDEN = 403
    private val NOT_FOUND = 404
    private val REQUEST_TIMEOUT = 408
    private val INTERNAL_SERVER_ERROR = 500
    private val BAD_GATEWAY = 502
    private val SERVICE_UNAVAILABLE = 503
    private val GATEWAY_TIMEOUT = 504

    constructor(context: Context):this(context,context.getString(R.string.call_back_loading),false)

    constructor(context: Context,showDialog:Boolean):this(context,context.getString(R.string.call_back_loading),showDialog)

    constructor(context: Context,msg:String,showDialog:Boolean){
        this.mContext = context
        this.msg = msg
        this.mShowDialog = showDialog
    }


    override fun onCompleted() {
        LoadingDialog().cancelDialogForLoading()
    }

    override fun onStart() {
        super.onStart()
        if (!NetWorkUtil.isNetConnected(FrameConst.getContext() as Context)) { //            ToastUtil.showShort(mContext, mContext.getString(com.wangxing.code.R.string.call_back_no_network));
            _onError(
                ServerException(
                    ServerException().ERROR_EXCEPTION,
                    mContext!!.getString(R.string.call_back_no_network)
                )
            )
            onCompleted()
            return
        }
        if (mShowDialog) {
            try {
                LoadingDialog()
                    .showDialogForLoading(mContext as Activity, msg, true)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    override fun onNext(t: ApiResult<T>) {
        if (t.isOk()) {
            _onNext(t.data, t.message)
        } else {
            _onError(ServerException(t.code, t.message))
        }
    }

    override fun onError(e: Throwable) {
        if (mShowDialog) LoadingDialog().cancelDialogForLoading()
        e.printStackTrace()
        //网络
        if (!NetWorkUtil.isNetConnected(FrameConst.getContext() as Context)) {
            _onError(ServerException(ServerException().ERROR_EXCEPTION, mContext!!.getString(R.string.call_back_no_network)))
        } else if (e is ServerException) { //            ToastUtil.showShort(mContext, ((ServerException) e).mErrorMsg);
            _onError(e)
        } else if (e is HttpException) {
            val code: Int = (e).code()
            //            ToastUtil.showShort(mContext, mContext.getString(com.wangxing.code.R.string.call_back_network_error, code));
            _onError(
                ServerException(
                    ServerException().ERROR_EXCEPTION,
                    mContext!!.getString(R.string.call_back_network_error, code)
                )
            )
        } else if (e is ConnectException) { //            ToastUtil.showShort(mContext, com.wangxing.code.R.string.common_connection_error);
            _onError(
                ServerException(
                    ServerException().ERROR_EXCEPTION,
                    mContext!!.getString(R.string.common_connection_error)
                )
            )
        } else if (e is JSONException) { //            ToastUtil.showShort(mContext,com.wangxing.code.R.string.call_back_requext_json_error);
            _onError(
                ServerException(
                    ServerException().ERROR_EXCEPTION,
                    mContext!!.getString(R.string.call_back_requext_json_error)
                )
            )
        }
        //        else
//            if (e instanceof IOException) {
////            ToastUtil.showShort(mContext, com.wangxing.code.R.string.call_back_connection_failed);
//            _onError(new ServerException(ServerException.ERROR_EXCEPTION, mContext.getString(com.wangxing.code.R.string.call_back_connection_failed)));
//        }
    }

    protected abstract fun _onNext(t: T?, message: String?)

    protected abstract fun _onError(exception: ServerException?)

}