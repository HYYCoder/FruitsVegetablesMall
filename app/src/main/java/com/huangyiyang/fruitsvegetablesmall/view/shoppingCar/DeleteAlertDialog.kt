package com.huangyiyang.fruitsvegetablesmall.view.shoppingCar

import android.app.Activity
import android.app.Dialog
import android.view.*
import android.widget.Button
import android.widget.TextView
import com.huangyiyang.fruitsvegetablesmall.R
import com.huangyiyang.fruitsvegetablesmall.util.StringUtil

class DeleteAlertDialog {

    private var mContext: Activity? = null

    private var mDialog: Dialog? = null
    private var viewById: TextView? = null
    private var btn: Button? = null
    private var btnCancel: Button? = null
    private var id: String? = null
    private var showTitle: String? = null

    interface onConfirmListener {
        fun onConfirm()
    }

    interface onConfirmListenerWithId {
        fun onConfirm(id: String?)
    }

    private var mOnConfirmListener: onConfirmListener? = null

    private var mOnConfirmListenerWithId: onConfirmListenerWithId? = null

    interface onDismissListener {
        fun onDismess()
    }

    private var mOnDismissListener: onDismissListener? = null


    constructor(context: Activity?, title: String?) {
        mContext = context
        showTitle = title
    }

    fun setOnConfirmListener(listener: onConfirmListener?) {
        mOnConfirmListener = listener
    }

    fun setOnConfirmListener(listener: onConfirmListenerWithId?) {
        mOnConfirmListenerWithId = listener
    }

    fun setOnDismissListener(listener: onDismissListener?) {
        mOnDismissListener = listener
    }


    private fun createDialog(): Dialog? {
        val inflater = LayoutInflater.from(mContext)
        val root = inflater.inflate(R.layout.dialog_is_delete, null) as ViewGroup
        initView(root)
        // 创建Dialog
        val dialog = Dialog(mContext!!, R.style.CommonDialog)
        dialog.setContentView(root)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setCancelable(false)
        // 设置dialog宽高
        val lp = WindowManager.LayoutParams()
        lp.copyFrom(dialog.window!!.attributes)
        lp.gravity = Gravity.CENTER
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.MATCH_PARENT
        dialog.window!!.attributes = lp
        dialog.setOnDismissListener {
            if (mOnDismissListener != null) {
                mOnDismissListener!!.onDismess()
            }
        }
        return dialog
    }

    private fun initView(root: ViewGroup) {
        viewById = root.findViewById(R.id.tv_message)
        btn = root.findViewById(R.id.btn_dialog_confirm)
        btn?.setOnClickListener(View.OnClickListener {
            if (StringUtil.isEmpty(id)) {
                mOnConfirmListener!!.onConfirm()
            } else {
                mOnConfirmListenerWithId!!.onConfirm(id)
            }
            dismiss()
        })
        btnCancel = root.findViewById(R.id.btn_dialog_cancel)
        btnCancel?.setOnClickListener(View.OnClickListener { dismiss() })
        viewById?.setText(showTitle)
    }


    fun show(context: Activity?, id: String?) {
        this.id = id
        if (mDialog == null) {
            mContext = context
            mDialog = createDialog()
        }
        if (!mDialog!!.isShowing) {
            mDialog!!.show()
        }
    }

    fun dismiss() {
        if (mDialog!!.isShowing) {
            mDialog!!.dismiss()
            mDialog = null
        }
    }
}