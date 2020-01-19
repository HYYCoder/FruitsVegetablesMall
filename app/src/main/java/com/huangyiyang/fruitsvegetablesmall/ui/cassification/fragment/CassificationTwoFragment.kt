package com.huangyiyang.fruitsvegetablesmall.ui.cassification.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.huangyiyang.fruitsvegetablesmall.R
import com.huangyiyang.fruitsvegetablesmall.bean.CategoryListBean
import com.huangyiyang.fruitsvegetablesmall.mvp.fragment.BaseFragment
import com.huangyiyang.fruitsvegetablesmall.ui.cassification.contract.ClassificationDetailFragmentContract
import com.huangyiyang.fruitsvegetablesmall.ui.cassification.model.ClassificationDetailFragmentModel
import com.huangyiyang.fruitsvegetablesmall.ui.cassification.presenter.ClassificationDetailFragmentPresenter

class CassificationTwoFragment : ClassificationDetailFragmentContract.ClassificationDetailFragmentView
    , View.OnClickListener,BaseFragment<ClassificationDetailFragmentModel
            , ClassificationDetailFragmentPresenter>() {

    private val tabLayout: TabLayout? = null
    private val viewPager: ViewPager? = null
    private val BEAN = "bean"
    private var categoryListBean: CategoryListBean? = null

    companion object{

        private var BEAN = "bean"

        fun newInstance(bean: CategoryListBean?): CassificationTwoFragment? {
            val args = Bundle()
            args.putParcelable(
                BEAN,
                bean
            )
            val fragment =
                CassificationTwoFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onClick(v: View?) {

    }

    override fun getLayoutResId(): Int {
        return R.layout.fragment_cassification_two
    }

    override fun initPresenter() {
        mPresenter?.setVM(this, mModel)
    }

    override fun initArgumentsData() {
        categoryListBean = arguments?.getParcelable(BEAN)
    }

    override fun initToolBar() {

    }

    override fun initView() {

    }

    override fun initData() {
        viewPager!!.adapter =
            ViewPagerAdapter(
                childFragmentManager,
                categoryListBean?.subCategories!!
            )
    }

    class ViewPagerAdapter : FragmentPagerAdapter{

        var list: List<CategoryListBean.SubCategoriesBean>? = null

        constructor(fm: FragmentManager, list: List<CategoryListBean.SubCategoriesBean>):super(fm){
            this.list = list
        }

        override fun getItem(position: Int): Fragment {
            return CassificationDetailFragment.newInstance(list?.get(position)?.id)!!
        }

        override fun getCount(): Int {
            return list!!.size
        }
    }
}