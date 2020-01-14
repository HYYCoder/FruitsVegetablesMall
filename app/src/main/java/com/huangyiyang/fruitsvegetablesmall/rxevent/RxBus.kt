package com.huangyiyang.fruitsvegetablesmall.rxevent

import androidx.annotation.NonNull
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import rx.subjects.Subject
import rx.subjects.PublishSubject
import rx.Observable


class RxBus {
    companion object {
        private var sRxBus: RxBus? = null

        fun getInstance(): RxBus? {
            if (sRxBus == null) {
                synchronized(RxBus::class.java) {
                    if (sRxBus == null) {
                        sRxBus =
                            RxBus()
                    }
                }
            }
            return sRxBus
        }
    }
    constructor()

    private val subjectMapper: ConcurrentHashMap<Any, MutableList<Subject<Any,Any>>> =
        ConcurrentHashMap()

    /**
     * 注册事件源
     */
    fun <T> register(@NonNull tag: Any): Observable<T>? {
        var subjects: MutableList<Subject<Any,Any>>? = subjectMapper[tag]
        if (subjects == null) {
            subjects = ArrayList()
            subjectMapper[tag]  = subjects
        }
        val subject: Subject<Any,Any> = PublishSubject.create()
        subjects.add(subject)

        return subject as Observable<T>
    }

    fun unRegister(@NonNull tag: Any?) {
        val subjects: List<Subject<Any,Any>>? = subjectMapper[tag]
        if (subjects != null) {
            subjectMapper.remove(tag)
        }
    }

    /**
     * 取消监听
     */
    fun unregister(
        @NonNull tag: Any?,
        @NonNull observable: Observable<*>?
    ): RxBus? {
        if (null == observable) return getInstance()
        val subjects: MutableList<Subject<Any,Any>>? = subjectMapper[tag]
        if (null != subjects) {
            subjects.remove(observable as Subject<*, *>)
            if (isEmpty(subjects)) {
                subjectMapper.remove(tag)
            }
        }
        return getInstance()
    }

    fun isEmpty(collection: Collection<Subject<Any,Any>>?): Boolean {
        return null == collection || collection.isEmpty()
    }

    /**
     * 触发事件
     */
    fun post(@NonNull tag: Any?, @NonNull content: Any?) {
        val subjectList: List<Subject<Any,Any>>? = subjectMapper[tag]
        if (!isEmpty(subjectList)) {
            for (subject in subjectList!!) {
                subject.onNext(content)

            }
        }
    }
}