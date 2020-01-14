package com.huangyiyang.fruitsvegetablesmall.mvp.presenter

import android.content.Context
import com.huangyiyang.fruitsvegetablesmall.rxevent.RxManager

abstract class BasePresenter<M,V> {

    lateinit var mContext: Context

    var mModel:M? = null

    var mView:V? = null

    var mManager: RxManager? = null

    fun setVM(view:V?,model:M?){
        this.mView = view
        this.mModel = model
        this.onStart()
    }

    open fun onStart() {}
}