package com.huangyiyang.fruitsvegetablesmall.ui.ShoppingCar.fragment

import android.view.View
import com.huangyiyang.fruitsvegetablesmall.R
import com.huangyiyang.fruitsvegetablesmall.mvp.fragment.BaseFragment
import com.huangyiyang.fruitsvegetablesmall.ui.ShoppingCar.contract.ShoppingCarFragmentContract
import com.huangyiyang.fruitsvegetablesmall.ui.ShoppingCar.model.ShoppingCarFragmentModel
import com.huangyiyang.fruitsvegetablesmall.ui.ShoppingCar.presenter.ShoppingCarFragmentPresenter

class ShoppingCarFragment : ShoppingCarFragmentContract.ShoppingCarFragmentView,
    View.OnClickListener, BaseFragment<ShoppingCarFragmentModel,ShoppingCarFragmentPresenter>() {

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

    override fun onClick(v: View?) {

    }
}