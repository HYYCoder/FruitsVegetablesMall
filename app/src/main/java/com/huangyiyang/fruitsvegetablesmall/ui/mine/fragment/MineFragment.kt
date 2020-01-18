package com.huangyiyang.fruitsvegetablesmall.ui.mine.fragment

import com.huangyiyang.fruitsvegetablesmall.R
import com.huangyiyang.fruitsvegetablesmall.mvp.fragment.BaseFragment
import com.huangyiyang.fruitsvegetablesmall.ui.mine.contract.MineFragmentContract
import com.huangyiyang.fruitsvegetablesmall.ui.mine.model.MineFragmentModel
import com.huangyiyang.fruitsvegetablesmall.ui.mine.presenter.MineFragmentPresenter

class MineFragment : MineFragmentContract.MineFragmentView, BaseFragment<MineFragmentModel,MineFragmentPresenter>(){
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