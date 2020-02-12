package com.huangyiyang.fruitsvegetablesmall.ui.order.presenter

import com.huangyiyang.fruitsvegetablesmall.MVPApplication
import com.huangyiyang.fruitsvegetablesmall.api.ServerException
import com.huangyiyang.fruitsvegetablesmall.ui.order.contract.OrderListFragmentContract
import com.huangyiyang.fruitsvegetablesmall.util.ToastUtil

class OrderListFragmentPresenter : OrderListFragmentContract.OrderListFragmentPresenter(){

    override fun requestNextPage() {
        mManager!!.add(mModel!!.getOrderList(header, mParamsMap)!!.subscribe(getCallBack()))
    }

    override fun errorBack(e: ServerException?) {
        ToastUtil.showLong(
            mContext,
            MVPApplication.getToastContent(mContext, e?.mErrorCode,e?.mErrorMsg).toString()
        )
    }
}