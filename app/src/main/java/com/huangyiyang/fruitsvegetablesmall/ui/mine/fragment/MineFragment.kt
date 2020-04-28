package com.huangyiyang.fruitsvegetablesmall.ui.mine.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import com.huangyiyang.fruitsvegetablesmall.R
import com.huangyiyang.fruitsvegetablesmall.mvp.manage.UserManager
import com.huangyiyang.fruitsvegetablesmall.mvp.fragment.BaseFragment
import com.huangyiyang.fruitsvegetablesmall.ui.login.activity.LoginActivity
import com.huangyiyang.fruitsvegetablesmall.ui.mine.contract.MineFragmentContract
import com.huangyiyang.fruitsvegetablesmall.ui.mine.model.MineFragmentModel
import com.huangyiyang.fruitsvegetablesmall.ui.mine.presenter.MineFragmentPresenter
import com.huangyiyang.fruitsvegetablesmall.ui.order.activity.OrderListActivity
import com.huangyiyang.fruitsvegetablesmall.view.shoppingCar.ToolbarUtil

class MineFragment : MineFragmentContract.MineFragmentView, BaseFragment<MineFragmentModel,MineFragmentPresenter>(){

    private var btnAllOrderList: RelativeLayout? = null
    private var btnReceivedOrderList: RelativeLayout? = null
    private var btnCompleteOrderList: RelativeLayout? = null
    private var btnLogout: Button? = null
    private var toolbarUtil: ToolbarUtil? = null
    private var tvMineName: TextView? = null
    private var tvMineMobile: TextView? = null
    private var tvMineAddress: TextView? = null
    private var tvMineUsername: TextView? = null
    private var tvMineReceivingPhone: TextView? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbarUtil = ToolbarUtil(view,this.context)
        btnAllOrderList = view.findViewById(R.id.rl_order_all)
        btnAllOrderList?.setOnClickListener {
            OrderListActivity.goTo(mPresenter?.mContext!!, 0)
        }
        btnReceivedOrderList = view.findViewById(R.id.rl_order_to_be_received)
        btnReceivedOrderList?.setOnClickListener {
            OrderListActivity.goTo(mPresenter?.mContext!!, 2)
        }
        btnCompleteOrderList = view.findViewById(R.id.rl_mine_complete)
        btnCompleteOrderList?.setOnClickListener {
            OrderListActivity.goTo(mPresenter?.mContext!!, 3)
        }
        btnLogout = view.findViewById(R.id.btn_logout)
        btnLogout?.setOnClickListener {
            val name: String = UserManager.getInstance()?.getName()!!
            val pwd: String = UserManager.getInstance()?.getUserPassword()!!
            UserManager.getInstance()?.clearCache()
            UserManager.getInstance()?.saveName(name)
            UserManager.getInstance()?.saveUserPassword(pwd)
            val intent = Intent(activity, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            activity?.startActivity(intent)
            activity?.finish()
        }
        tvMineName = view.findViewById(R.id.tv_mine_name)
        tvMineName?.text = "姓名："+UserManager.getInstance()?.getUserName()
        tvMineMobile = view.findViewById(R.id.tv_mine_mobile)
        tvMineMobile?.text = "手机号："+UserManager.getInstance()?.getUserPhone()
        tvMineAddress = view.findViewById(R.id.tv_mine_address)
        tvMineAddress?.text = "收货地址："+UserManager.getInstance()?.getUserAddress()
        tvMineUsername = view.findViewById(R.id.tv_mine_username)
        tvMineUsername?.text = "用户名："+UserManager.getInstance()?.getName()
        tvMineReceivingPhone = view.findViewById(R.id.tv_mine_receiving_phone)
        tvMineReceivingPhone?.text = "收货手机号："+UserManager.getInstance()?.getReceivingPhone()
    }

    override fun getLayoutResId(): Int {
        return R.layout.fragment_mine
    }

    override fun initPresenter() {
        mPresenter?.setVM(this, mModel)
    }

    override fun initArgumentsData() {

    }

    override fun initToolBar() {
        toolbarUtil?.setLightBackTheme("")
        toolbarUtil?.setLineBackgroundColor(R.color.white_ffffff)
        toolbarUtil?.setLeftVisibility(View.GONE)
        toolbarUtil?.setRightImage(R.drawable.setting)
        //toolbarUtil?.setOnRightClickListener(View.OnClickListener { SettingActivity.goTo(activity) })
    }

    override fun initView() {

    }

    override fun initData() {

    }

}