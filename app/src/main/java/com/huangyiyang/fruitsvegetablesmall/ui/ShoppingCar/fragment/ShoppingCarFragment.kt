package com.huangyiyang.fruitsvegetablesmall.ui.ShoppingCar.fragment

import android.os.Bundle
import android.view.View
import android.widget.Button
import com.huangyiyang.fruitsvegetablesmall.R
import com.huangyiyang.fruitsvegetablesmall.event.EventParams
import com.huangyiyang.fruitsvegetablesmall.mvp.fragment.BaseFragment
import com.huangyiyang.fruitsvegetablesmall.ui.ShoppingCar.contract.ShoppingCarFragmentContract
import com.huangyiyang.fruitsvegetablesmall.ui.ShoppingCar.model.ShoppingCarFragmentModel
import com.huangyiyang.fruitsvegetablesmall.ui.ShoppingCar.presenter.ShoppingCarFragmentPresenter
import com.huangyiyang.fruitsvegetablesmall.ui.order.activity.ConfirmOrderActivity
import com.huangyiyang.fruitsvegetablesmall.util.ToastUtil
import java.util.*

class ShoppingCarFragment : ShoppingCarFragmentContract.ShoppingCarFragmentView, BaseFragment<ShoppingCarFragmentModel,ShoppingCarFragmentPresenter>() {

    private var btnSettlement : Button? = null
    private var btnEmpty : Button? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnSettlement = view.findViewById(R.id.btn_settlement)
        btnSettlement?.setOnClickListener {
            val goods = ArrayList<Int?>()
//            for (itemsBean in shoppingCarListBean().normalItems!!) {
//                    if (itemsBean.isChecked()) {
//                        goods.add(itemsBean.getId())
//                    }
//            }
            if (goods.size == 0) {
                ToastUtil().showShort(mPresenter?.mContext!!, "没有选择商品")
            } else {
                ConfirmOrderActivity.goTo(mPresenter?.mContext!!, goods)
            }
            ConfirmOrderActivity.goTo(mPresenter?.mContext!!, goods)
        }
        btnEmpty = view.findViewById(R.id.btn_empty)
        btnEmpty?.setOnClickListener {
            mRxManager.post(EventParams.EVENT_TYPE_TO_MAIN_FRAGMENT, null)
        }
    }

    override fun getLayoutResId(): Int {
        return R.layout.fragment_shopping_car
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