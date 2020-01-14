package com.huangyiyang.fruitsvegetablesmall.util

import android.app.Activity
import android.content.Context
import android.os.IBinder
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

class KeyboardUtil {
    companion object {
        //隐藏键盘
        fun hiddenKeyboard(event: MotionEvent, context: Context) {
            val activity = context as Activity
            val v = activity.currentFocus
            if (isShouldHideKeyboard(v, event)) {
                hideKeyboard(v!!.windowToken, context)
            }
        }


        /**
         * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
         *
         * @param v
         * @param event
         * @return
         */
        private fun isShouldHideKeyboard(
            v: View?,
            event: MotionEvent
        ): Boolean {
            if (v != null && v is EditText) { //            EditText editText=(EditText)v;
//            editText.setSelection(0);
//            v.setFocusable(false);
                val l = intArrayOf(0, 0)
                v.getLocationInWindow(l)
                val left = l[0]
                val top = l[1]
                val bottom = top + v.getHeight()
                val right = left + v.getWidth()
                return if (event.x > left && event.x < right && event.y > top && event.y < bottom
                ) { // 点击EditText的事件，忽略它。
                    false
                } else {
                    v.clearFocus()
                    true
                }
            }
            // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
            return false
        }

        /**
         * 获取InputMethodManager，隐藏软键盘
         * @param token
         */
        private fun hideKeyboard(token: IBinder?, context: Context) {
            if (token != null) {
                val im =
                    context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                im.hideSoftInputFromWindow(
                    token,
                    InputMethodManager.HIDE_NOT_ALWAYS
                )
            }
        }
    }
}