package com.huangyiyang.fruitsvegetablesmall.rxevent

import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * RxJava调度管理
 */
class RxSchedulers {
    companion object {
        fun <T> io_main(): Observable.Transformer<T, T>? {
            return Observable.Transformer { observable ->
                observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
            }
        }
    }
}