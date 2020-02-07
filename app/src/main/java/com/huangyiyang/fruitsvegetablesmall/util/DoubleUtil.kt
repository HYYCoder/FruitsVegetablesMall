package com.huangyiyang.fruitsvegetablesmall.util

import java.math.BigDecimal

class DoubleUtil {

    companion object {
        /**
         * 1 d1<d2></d2>;
         * 0 d2=d1;
         * -1 d1>d2;
         */
        fun compare(d1: Double, d2: Double): Int {
            val bd1 = BigDecimal.valueOf(d1)
            val bd2 = BigDecimal.valueOf(d2)
            val result: Double = bd1.subtract(bd2).toDouble()
            return if (bd1.subtract(bd2).toDouble() == 0.0000) 0 else if (bd1.subtract(bd2).toDouble() > 0.0000) -1 else 1
        }

        /**
         * double 相加
         * @param d1
         * @param d2
         * @return
         */
        fun sum(d1: Double, d2: Double): Double {
            val bd1 = BigDecimal(java.lang.Double.toString(d1))
            val bd2 = BigDecimal(java.lang.Double.toString(d2))
            return bd1.add(bd2).toDouble()
        }


        /**
         * double 相减
         * @param d1
         * @param d2
         * @return
         */
        fun sub(d1: Double, d2: Double): Double {
            val bd1 = BigDecimal(java.lang.Double.toString(d1))
            val bd2 = BigDecimal(java.lang.Double.toString(d2))
            return bd1.subtract(bd2).toDouble()
        }

        /**
         * double 乘法
         * @param d1
         * @param d2
         * @return
         */
        fun mul(d1: Double, d2: Double): Double {
            val bd1 = BigDecimal(java.lang.Double.toString(d1))
            val bd2 = BigDecimal(java.lang.Double.toString(d2))
            return bd1.multiply(bd2).toDouble()
        }


        /**
         * double 除法
         * @param d1
         * @param d2
         * @param scale 四舍五入 小数点位数
         * @return
         */
        fun div(
            d1: Double,
            d2: Double,
            scale: Int
        ): Double { //  当然在此之前，你要判断分母是否为0，
//  为0你可以根据实际需求做相应的处理
            val bd1 = BigDecimal(java.lang.Double.toString(d1))
            val bd2 = BigDecimal(java.lang.Double.toString(d2))
            return bd1.divide(bd2, scale, BigDecimal.ROUND_HALF_UP).toDouble()
        }


        // 大数不显示科学计数法
        fun big(d: Double): String? {
            val d1 = BigDecimal(java.lang.Double.toString(d))
            val d2 = BigDecimal(Integer.toString(1))
            // 四舍五入,保留2位小数
            return d1.divide(d2, 2, BigDecimal.ROUND_HALF_UP).toString()
        }
    }
}