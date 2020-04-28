package com.huangyiyang.fruitsvegetablesmall.mvp.util

import android.annotation.SuppressLint
import android.content.Context
import android.text.format.DateUtils
import android.util.TypedValue
import com.huangyiyang.fruitsvegetablesmall.R
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

class ValueUtil {

    companion object {
        @SuppressLint("SimpleDateFormat")
        private val DATE_TIME_FORMAT =
            SimpleDateFormat("yyyy-MM-dd HH:mm")
        @SuppressLint("SimpleDateFormat")
        private val LONG_TIME_FORMAT =
            SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        @SuppressLint("SimpleDateFormat")
        private val SHORT_TIME_FORMAT =
            SimpleDateFormat("MM月dd日 HH:mm")
        @SuppressLint("SimpleDateFormat")
        private val TIME_STAMP =
            SimpleDateFormat("yyyyMMddHHmmssSSSS")
        @SuppressLint("SimpleDateFormat")
        private val SPECIFIC_DATE = SimpleDateFormat("yyyyMMdd")

        fun dpToPx(context: Context, dp: Int): Int {
            return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp.toFloat(),
                context.resources.displayMetrics
            ).toInt()
        }

        fun parseInt(str: String): Int {
            try {
                return str.toInt()
            } catch (e: Exception) { // nothing
            }
            return 0
        }

        fun parseLong(str: String): Long {
            try {
                return str.toInt().toLong()
            } catch (e: Exception) { // nothing
            }
            return 0
        }

        fun parseFloat(str: String): Float {
            try {
                return str.toFloat()
            } catch (e: Exception) { // nothing
            }
            return 0F
        }

        fun checkMoney(money: String): Boolean {
            return money.matches("\\d*\\.?\\d?\\d?" as Regex)
        }

        fun formatDateTime(timestamp: Long): String? {
            return DATE_TIME_FORMAT.format(Date(timestamp))
        }


        fun formatLongTime(timestamp: Long): String? {
            return LONG_TIME_FORMAT.format(Date(timestamp))
        }

        fun formatShortTime(timestamp: Long): String? {
            return SHORT_TIME_FORMAT.format(Date(timestamp))
        }

        fun formatStamp(date: Date): String? {
            return TIME_STAMP.format(date.time)
        }

        fun formatSpecificDate(date: Date): String? {
            return SPECIFIC_DATE.format(date.time)
        }

        fun formatDuration(duration: Int): String? {
            return DateUtils.formatElapsedTime(duration.toLong())
        }


        fun formatMoney(context: Context, money: Float): String? {
            return context.getString(
                R.string.common_money,
                String.format(Locale.ENGLISH, "%.2f", money)
            )
        }

        fun parseDouble(str: String): Double {
            try {
                return str.toDouble()
            } catch (e: Exception) {
            }
            return 0.00
        }

        /**
         * 将每三个数字加上逗号处理（通常使用金额方面的编辑）
         *
         * @param str 需要处理的字符串
         * @return 处理完之后的字符串
         */
        fun addComma(str: String): String? {
            val decimalFormat = DecimalFormat(",###")
            return decimalFormat.format(str.toDouble())
        }
    }
}