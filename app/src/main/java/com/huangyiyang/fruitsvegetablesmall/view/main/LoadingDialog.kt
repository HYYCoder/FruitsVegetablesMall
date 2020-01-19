package com.huangyiyang.fruitsvegetablesmall.view.main

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.huangyiyang.fruitsvegetablesmall.R
import com.huangyiyang.fruitsvegetablesmall.view.main.CommonLayout

class LoadingDialog {
    /**
     * 加载数据对话框
     */
    private var mLoadingDialog: Dialog? = null

    /**
     * 显示加载对话框
     *
     * @param context    上下文
     * @param msg        对话框显示内容
     * @param cancelable 对话框是否可以取消
     */
    fun showDialogForLoading(
        context: Context,
        msg: String?,
        cancelable: Boolean
    ): Dialog? {
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.dialog_loading, null)
        val loadingText =
            view.findViewById<View>(R.id.id_tv_loading_dialog_text) as TextView
        val progressBar =
            view.findViewById<View>(R.id.callback_progress) as ProgressBar
        progressBar.indeterminateDrawable = ContextCompat.getDrawable(
            context,
            CommonLayout(context).getLoadingId()
        )
        loadingText.text = msg
        mLoadingDialog = Dialog(context, R.style.CustomProgressDialog)
        mLoadingDialog!!.setCancelable(cancelable)
        mLoadingDialog!!.setCanceledOnTouchOutside(false)
        mLoadingDialog!!.setContentView(
            view,
            LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
        )
        mLoadingDialog?.show()
        return mLoadingDialog
    }


    /**
     * 关闭加载对话框
     */
    fun cancelDialogForLoading() {
        if (mLoadingDialog != null) { //            mLoadingDialog.cancel();
            mLoadingDialog!!.dismiss()
        }
    }
}