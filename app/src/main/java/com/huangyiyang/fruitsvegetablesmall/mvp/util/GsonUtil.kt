package com.huangyiyang.fruitsvegetablesmall.mvp.util

import com.google.gson.Gson
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import java.util.*

class GsonUtil {

    companion object {
        private var gson: Gson? = Gson()

        /**
         * 转成json
         *
         * @param object
         * @return
         */
        fun GsonString(`object`: Any?): String? {
            var gsonString: String? = null
            if (gson != null) {
                gsonString = gson!!.toJson(`object`)
            }
            return gsonString
        }

        @JvmStatic
        fun main(strings: Array<String>) {
            val map: MutableMap<String, Any> =
                HashMap()
            map["大海"] = "biang"
            map["胖大海"] = "biao"
            println(GsonUtil.GsonString(map))
        }

        /**
         * 转成bean
         *
         * @param gsonString
         * @param cls
         * @return
         */
        fun <T> GsonToBean(gsonString: String?, cls: Class<T>?): T? {
            var t: T? = null
            if (gson != null) {
                t = gson!!.fromJson(gsonString, cls)
            }
            return t
        }

        /**
         * 转成list
         * 泛型在编译期类型被擦除导致报错
         *
         * @param gsonString
         * @param cls
         * @return
         */
        fun <T> GsonToList(
            gsonString: String?,
            cls: Class<T>?
        ): List<T>? {
            var list: List<T>? = null
            if (gson != null) {
                list = gson!!.fromJson(gsonString, object : TypeToken<List<String?>?>() {}.type)
            }
            return list
        }

        /**
         * 处理空值
         *
         * @param obj
         * @return
         */
        fun format(obj: Any?): String? {
            return obj?.toString() ?: ""
        }

        fun format0(obj: Any?): String? {
            return obj?.toString() ?: "0"
        }

        fun format1(obj: Any?): String? {
            return obj?.toString() ?: "1"
        }

        fun isEmpty(str: String?): Boolean {
            return str == null || str.length <= 0
        }

        /**
         * 转成list
         * 解决泛型问题
         *
         * @param json
         * @param cls
         * @param <T>
         * @return
        </T> */
        fun <T> jsonToList(
            json: String?,
            cls: Class<T>?
        ): List<T>? {
            val gson = Gson()
            val list: MutableList<T> = ArrayList()
            val array = JsonParser().parse(json).asJsonArray
            for (elem in array) {
                list.add(gson.fromJson(elem, cls))
            }
            return list
        }


        /**
         * 转成list中有map的
         *
         * @param gsonString
         * @return
         */
        fun <T> GsonToListMaps(gsonString: String?): List<Map<String?, T>?>? {
            var list: List<Map<String?, T>?>? = null
            if (gson != null) {
                list = gson!!.fromJson(
                    gsonString,
                    object : TypeToken<List<Map<String?, T>?>?>() {}.type
                )
            }
            return list
        }

        /**
         * 转成map的
         *
         * @param gsonString
         * @return
         */
        fun <T> GsonToMaps(gsonString: String?): Map<String?, T>? {
            var map: Map<String?, T>? = null
            if (gson != null) {
                map = gson!!.fromJson(gsonString, object : TypeToken<Map<String?, T>?>() {}.type)
            }
            return map
        }
    }
}