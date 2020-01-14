package com.huangyiyang.fruitsvegetablesmall.rxevent

import java.util.*

import rx.Observable
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.functions.Action1
import rx.subscriptions.CompositeSubscription

class RxManager {
    private var mRxBus: RxBus? = RxBus.getInstance()
    //管理rxbus订阅
    private val mObservables: MutableMap<String,Observable<Void>?> =
        HashMap()
    /*管理Observables 和 Subscribers订阅*/
    private val mCompositeSubscription: CompositeSubscription = CompositeSubscription()

    /**
     * RxBus注入监听
     * @param eventName
     * @param action1
     */
    fun on(eventName: String, action1: Action1<Void>?) {
        val mObservable: Observable<Void>? = mRxBus?.register(eventName)
        mObservables[eventName] = mObservable
        /*订阅管理*/mCompositeSubscription.add(
            mObservable?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(action1, Action1<Throwable?> {
                    fun call(throwable: Throwable) {
                        throwable.printStackTrace()
                    }
                })
        )
    }

    /**
     * 单纯的Observables 和 Subscribers管理
     * @param m
     */
    fun add(m: Subscription?) { /*订阅管理*/
        mCompositeSubscription.add(m)
    }

    /**
     * 单个presenter生命周期结束，取消订阅和所有rxbus观察
     */
    fun clear() {
        mCompositeSubscription.unsubscribe() // 取消所有订阅
        for ((key, value) in mObservables) {
            mRxBus?.unregister(key, value) // 移除rxbus观察
        }
    }

    //发送rxbus
    fun post(tag: Any?, content: Any?) {
        mRxBus?.post(tag, content)
    }
}