package com.huangyiyang.fruitsvegetablesmall.mvp.util

import android.annotation.SuppressLint
import android.content.Context
import androidx.annotation.NonNull

class ContextUtils {

    constructor(){
       throw UnsupportedOperationException("u can't instantiate me...")
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var context: Context? = null


        /**
         * 初始化工具类
         *
         * @param context 上下文
         */
        fun init(@NonNull context: Context) {
            this.context = context.applicationContext
        }

        /**
         * 获取ApplicationContext
         *
         * @return ApplicationContext
         */
        fun getContext(): Context? {
            if (context != null) {
                return context
            }
            throw NullPointerException("should be initialized in application")
        }
    }
}