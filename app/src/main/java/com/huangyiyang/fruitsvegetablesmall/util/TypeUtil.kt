package com.huangyiyang.fruitsvegetablesmall.util

import java.lang.reflect.ParameterizedType

class TypeUtil {
    companion object{
        fun <T> getTypeObject(o: Any, i: Int): T? {
            try {
                return ((o.javaClass
                    .genericSuperclass as ParameterizedType).actualTypeArguments[i] as Class<T>)
                    .newInstance()
            } catch (e: InstantiationException) {

            } catch (e: IllegalAccessException) {

            } catch (e: ClassCastException) {

            }
            return null
        }
    }
}