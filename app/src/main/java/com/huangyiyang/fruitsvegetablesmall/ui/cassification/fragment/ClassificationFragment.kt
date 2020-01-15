package com.huangyiyang.fruitsvegetablesmall.ui.cassification.fragment

import android.view.View
import com.huangyiyang.fruitsvegetablesmall.R
import com.huangyiyang.fruitsvegetablesmall.mvp.fragment.BaseFragment
import com.huangyiyang.fruitsvegetablesmall.mvp.presenter.BasePresenter
import com.huangyiyang.fruitsvegetablesmall.ui.cassification.contract.ClassificationFragmentContract
import com.huangyiyang.fruitsvegetablesmall.ui.cassification.model.ClassificationFragmentModel

class ClassificationFragment : ClassificationFragmentContract.ClassificationFragmentView, View.OnClickListener ,
    BaseFragment<ClassificationFragmentModel, ClassificationFragmentContract.ClassificationFragmentView, BasePresenter<ClassificationFragmentModel, ClassificationFragmentContract.ClassificationFragmentView>>() {
    override fun getLayoutResId(): Int {
        return R.layout.fragment_classification
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