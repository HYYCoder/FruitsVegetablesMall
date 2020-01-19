package com.huangyiyang.fruitsvegetablesmall.ui.mine.fragment

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import com.huangyiyang.fruitsvegetablesmall.R
import com.huangyiyang.fruitsvegetablesmall.mvp.fragment.BaseFragment
import com.huangyiyang.fruitsvegetablesmall.ui.mine.contract.MineFragmentContract
import com.huangyiyang.fruitsvegetablesmall.ui.mine.model.MineFragmentModel
import com.huangyiyang.fruitsvegetablesmall.ui.mine.presenter.MineFragmentPresenter
import com.huangyiyang.fruitsvegetablesmall.ui.order.activity.OrderListActivity

class MineFragment : MineFragmentContract.MineFragmentView, BaseFragment<MineFragmentModel,MineFragmentPresenter>(){

    private var btnAllOrderList: RelativeLayout? = null
    private var btnReceivedOrderList: RelativeLayout? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnAllOrderList = view.findViewById(R.id.rl_order_all)
        btnAllOrderList?.setOnClickListener {
            OrderListActivity.goTo(mPresenter?.mContext!!, 0)
        }
        btnReceivedOrderList = view.findViewById(R.id.rl_order_to_be_received)
        btnReceivedOrderList?.setOnClickListener {
            OrderListActivity.goTo(mPresenter?.mContext!!, 1)
        }

//        when (view?.id) {
//            R.id.tv_mine_membership -> {
////                MembershipActivity.goTo(activity)
//            }
//            R.id.tv_mine_mybeed -> {
////                MineBeedActivity.goTo(activity, mineBeedCount)
//            }
//            R.id.rl_order_all -> {
//                OrderListActivity.goTo(context!!, 0)
//            }
//            R.id.rl_order_to_be_received -> {
////                (UserManager.getInstance().getUserLevel().equals(
////                    Const.CORPORATE_OWNED
////                )
////            ) { //CORPORATE_OWNED:直营
////                OrderListActivity.goTo(activity, 2)
////            } else if (UserManager.getInstance().getUserLevel().equals(Const.FRANCHISED)) { //FRANCHISED:加盟店
////                OrderListActivity.goTo(activity, 2)
////                //                    ToastUtil.showShort(getActivity(), "测试金额 : 999");
//////                    PayActivity.goTo(getActivity(), 999, 999,0); // 999 测试金额
////            }
//            }
//            R.id.rl_mine_discounts_center -> {
////                DiscountCenterActivity.goTo(activity)
//            }
//            R.id.mine_address -> {
////                AddressActivity.goTo(activity)
//            }
//            R.id.mine_help -> {
////                WebViewActivity.goTo(
////                activity,
////                getString(R.string.mine_help),
////                "/about/help")
//            }
//            R.id.mine_connect_us -> {
////                WebViewActivity.goTo(
////                activity,
////                getString(R.string.mine_connect_us),
////                "/about/contact")
//            }
//            R.id.mine_feedback -> {
////                FeedbackActivity.goTo(activity)
//            }
//            R.id.btn_logout -> {
////                val name: String = UserManager.getInstance().getName()
////                val pwd: String = UserManager.getInstance().getUserPassword()
////                UserManager.getInstance().clearCache()
////                UserManager.getInstance().saveName(name)
////                UserManager.getInstance().saveUserPassword(pwd)
////                val intent = Intent(activity, LoginActivity::class.java)
////                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
////                activity!!.startActivity(intent)
////                activity!!.finish()
//            }
//        }
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

    }

    override fun initView() {

    }

    override fun initData() {

    }

}