package com.huangyiyang.fruitsvegetablesmall.ui.cassification.fragment

import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.huangyiyang.fruitsvegetablesmall.R
import com.huangyiyang.fruitsvegetablesmall.Const
import com.huangyiyang.fruitsvegetablesmall.bean.CategoryListBean
import com.huangyiyang.fruitsvegetablesmall.mvp.fragment.BaseFragment
import com.huangyiyang.fruitsvegetablesmall.ui.cassification.contract.ClassificationFragmentContract
import com.huangyiyang.fruitsvegetablesmall.ui.cassification.model.ClassificationFragmentModel
import com.huangyiyang.fruitsvegetablesmall.ui.cassification.presenter.ClassificationFragmentPresenter
import com.huangyiyang.fruitsvegetablesmall.view.classification.VerticalTabLayout
import com.huangyiyang.fruitsvegetablesmall.view.main.CommonLayout
import rx.functions.Action1

class ClassificationFragment : ClassificationFragmentContract.ClassificationFragmentView, View.OnClickListener ,
    BaseFragment<ClassificationFragmentModel,ClassificationFragmentPresenter>() {

    private var verticalTabLayout: VerticalTabLayout? = null
    private var viewPager: ViewPager? = null
    private var mSearchBar: TextView? = null
    private var mCommonLayout: CommonLayout? = null
    private var index = 0

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

        mCommonLayout = layout!!.findViewById(R.id.common_content)
        verticalTabLayout = layout!!.findViewById(R.id.classification_one_content)
        viewPager = layout!!.findViewById(R.id.vp)
        viewPager?.offscreenPageLimit = 3
        mSearchBar = layout!!.findViewById(R.id.search_bar)
        mSearchBar?.setOnClickListener(this)

        //首页分类点击过来===>跳转
        mRxManager.on("1", Action1<Void> {
            //接收设置选中分类页面
            viewPager?.currentItem = 0
            index = 0
        })
        mRxManager.on("2", Action1<Void> {
            //接收设置选中分类页面
            viewPager?.currentItem = 1
            index = 1
        })
        mRxManager.on("3", Action1<Void> {
            //接收设置选中分类页面
            viewPager?.currentItem = 2
            index = 2
        })
        mRxManager.on("4", Action1<Void> {
            //接收设置选中分类页面
            viewPager?.currentItem = 3
            index = 3
        })
        mRxManager.on("5", Action1<Void> {
            //接收设置选中分类页面
            viewPager?.currentItem = 4
            index = 4
        })
        mRxManager.on("6", Action1<Void> {
            //接收设置选中分类页面
            viewPager?.currentItem = 5
            index = 5
        })
        mRxManager.on("7", Action1<Void> {
            //接收设置选中分类页面
            viewPager?.currentItem = 6
            index = 6
        })
        mRxManager.on("8", Action1<Void> {
            //接收设置选中分类页面
            viewPager?.currentItem = 7
            index = 7
        })

    }

    override fun initData() {

    }

    override fun onClick(v: View?) {
//        when (v!!.id) {
//            R.id.search_bar -> SearchResultListActivity.goTo(activity)
//        }
    }

    override fun onVisible() {
        super.onVisible()
        mPresenter!!.getCategoriesList(Const.header())
    }

    override fun setCategoriesList(categoryListBean: List<CategoryListBean>?) {
        viewPager?.adapter =
            ViewPagerAdapter(
                activity?.supportFragmentManager!!,
                categoryListBean!!
            )
        verticalTabLayout?.setupWithViewPager(viewPager)
        viewPager?.currentItem = index
        index = 0
    }

    class ViewPagerAdapter : FragmentPagerAdapter{

        var list: List<CategoryListBean>? = null

        constructor(fm:FragmentManager,list: List<CategoryListBean>):super(fm){
            this.list = list
        }

        override fun getItem(position: Int): Fragment {
            return CassificationTwoFragment.newInstance(list?.get(position))!!
        }

        override fun getCount(): Int {
            return list?.size!!
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return list!![position].category?.name
        }
    }

}