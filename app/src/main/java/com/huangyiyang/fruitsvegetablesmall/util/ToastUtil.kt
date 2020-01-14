package com.huangyiyang.fruitsvegetablesmall.util

import android.annotation.SuppressLint
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.huangyiyang.fruitsvegetablesmall.R

class ToastUtil {
    private var sToast: Toast? = null
    private var sMessageTv: TextView? = null

    private fun initToast(context: Context) {
        if (sToast == null) {
            sToast = Toast(context)
            @SuppressLint("InflateParams")
            val view: View = LayoutInflater.from(context).inflate(R.layout.toast_layout, null)
            sMessageTv = view.findViewById<View>(android.R.id.message) as TextView
            sToast!!.view = view
            sToast!!.setGravity(Gravity.CENTER, 0, 0)
        }
    }

    fun showShort(
        context: Context,
        message: CharSequence
    ) {
        show(context, message, Toast.LENGTH_SHORT)
    }

    fun showShort(context: Context, messageResId: Int) {
        show(context, context.getString(messageResId), Toast.LENGTH_SHORT)
    }

    fun showLong(context: Context, message: CharSequence) {
        show(context, message, Toast.LENGTH_LONG)
    }

    fun showLong(context: Context, messageResId: Int) {
        show(context, context.getString(messageResId), Toast.LENGTH_LONG)
    }

    private fun show(
        context: Context,
        message: CharSequence,
        duration: Int
    ) {
        if (sToast != null) {
            sToast!!.cancel()
            sToast = null
        }
        initToast(context)
        sMessageTv!!.text = message
        sToast!!.duration = duration
        sToast!!.show()
    }

//    public static void showStyle(Context context, int resid) {
//        if (sToast != null) {
//            sToast.cancel();
//            sToast = null;
//        }
//        sToast = new Toast(context);
//
//        @SuppressLint("InflateParams")
//        View view = LayoutInflater.from(context).inflate(R.layout.toast, null);
//        view.findViewById(R.id.ll_toast).setBackgroundColor(Color.WHITE);
//        sMessageTv = (TextView) view.findViewById(android.R.id.message);
//        sMessageTv.setBackgroundResource(resid);
//        sToast.setView(view);
//        sToast.setGravity(Gravity.CENTER, 0, 0);
//        sToast.setDuration(Toast.LENGTH_LONG);
//        sToast.show();
//    }
//
//    public static void showImgStyle(Context context, String message) {
//        if (sToast != null) {
//            sToast.cancel();
//            sToast = null;
//        }
//        sToast = new Toast(context);
//
//        @SuppressLint("InflateParams")
//        View view = LayoutInflater.from(context).inflate(R.layout.toast_layout, null);
//        sMessageTv = (TextView) view.findViewById(R.id.tv_toast_msg);
//        sMessageTv.setText(message);
//        sToast.setView(view);
//        sToast.setGravity(Gravity.CENTER, 0, 0);
//        sToast.setDuration(Toast.LENGTH_LONG);
//        sToast.show();
//    }
//
//    public static void showPostSelectLabel(Context context) {
//        if (sToast != null) {
//            sToast.cancel();
//            sToast = null;
//        }
//        sToast = new Toast(context);
//
//        @SuppressLint("InflateParams")
//        View view = LayoutInflater.from(context).inflate(R.layout.toast_post_select_label, null);
//        sToast.setView(view);
//        sToast.setGravity(Gravity.CENTER, 0, 0);
//        sToast.setDuration(Toast.LENGTH_SHORT);
//        sToast.show();
//    }
//
//    public static void showNoShopPrivilege(Context context) {
//        if (sToast != null) {
//            sToast.cancel();
//            sToast = null;
//        }
//        sToast = new Toast(context);
//
//        @SuppressLint("InflateParams")
//        View view = LayoutInflater.from(context).inflate(R.layout.toast_post_select_label, null);
//        TextView content1 = (TextView) view.findViewById(R.id.tv_content1);
//        content1.setText(R.string.mine_shop_privilege_toast1);
////        TextView content2 = V.f(view, R.id.tv_content2);
//        TextView content2 = (TextView) view.findViewById(R.id.tv_content2);
//
//        content2.setText(R.string.mine_shop_privilege_toast2);
//        sToast.setView(view);
//        sToast.setGravity(Gravity.CENTER, 0, 0);
//        sToast.setDuration(Toast.LENGTH_SHORT);
//        sToast.show();
//    }
//
//    public static void showNotEnoughMoney(Context context) {
//        if (sToast != null) {
//            sToast.cancel();
//            sToast = null;
//        }
//        sToast = new Toast(context);
//
//        @SuppressLint("InflateParams")
//        View view = LayoutInflater.from(context).inflate(R.layout.toast_post_select_label, null);
//        TextView content1 = (TextView) view.findViewById(R.id.tv_content1);
//        content1.setText(R.string.mine_shop_privilege_toast1);
////        TextView content2 = V.f(view, R.id.tv_content2);
//        TextView content2 = (TextView) view.findViewById(R.id.tv_content2);
//        content2.setText(R.string.ecoer_detail_shop_not_enough_money);
//        sToast.setView(view);
//        sToast.setGravity(Gravity.CENTER, 0, 0);
//        sToast.setDuration(Toast.LENGTH_SHORT);
//        sToast.show();
//    }
//
//    public static void showIntegralStyle(Context context, String integral) {
//        if (sToast != null) {
//            sToast.cancel();
//            sToast = null;
//        }
//        sToast = new Toast(context);
//
//        @SuppressLint("InflateParams")
//        View view = LayoutInflater.from(context).inflate(R.layout.toast_integral, null);
//        view.findViewById(R.id.ll_toast).setBackgroundColor(ContextCompat.getColor(context, R.color.white_00ffffff));
//        sMessageTv = (TextView) view.findViewById(R.id.textView);
//        sMessageTv.setText(integral);
//        sToast.setView(view);
//        sToast.setGravity(Gravity.CENTER, 0, 0);
//        sToast.setDuration(Toast.LENGTH_SHORT);
//        sToast.show();
//    }
}