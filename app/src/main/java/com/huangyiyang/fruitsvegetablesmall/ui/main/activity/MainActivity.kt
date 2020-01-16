package com.huangyiyang.fruitsvegetablesmall.ui.main.activity

import android.os.Bundle
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.huangyiyang.fruitsvegetablesmall.R
import com.huangyiyang.fruitsvegetablesmall.event.EventParams
import com.huangyiyang.fruitsvegetablesmall.mvp.activity.BaseActivity
import com.huangyiyang.fruitsvegetablesmall.mvp.presenter.BasePresenter
import com.huangyiyang.fruitsvegetablesmall.ui.ShoppingCar.fragment.ShoppingCarFragment
import com.huangyiyang.fruitsvegetablesmall.ui.cassification.fragment.ClassificationFragment
import com.huangyiyang.fruitsvegetablesmall.ui.main.contract.MainFragmentContract
import com.huangyiyang.fruitsvegetablesmall.ui.main.fragment.MainFragment
import com.huangyiyang.fruitsvegetablesmall.ui.main.model.MainFragmentModel
import com.huangyiyang.fruitsvegetablesmall.ui.mine.fragment.MineFragment
import com.huangyiyang.fruitsvegetablesmall.view.main.NoSwipeableViewPager
import rx.functions.Action1

class MainActivity : MainFragmentContract.MainFragmentView, RadioGroup.OnCheckedChangeListener, BaseActivity<MainFragmentModel,MainFragmentContract.MainFragmentView, BasePresenter<MainFragmentModel,MainFragmentContract.MainFragmentView>>() {

    private var mRadioGroup: RadioGroup? = null
    private var mAdapter: ViewPageAdapter? = null
    private var mViewPage: NoSwipeableViewPager? = null
    private var currentFragment = 0
    private var mainActivityBottomMain: RadioButton? = null
    private var mainActivityBottomClassification: RadioButton? = null
    private var mainActivityBottomShoppingCar: RadioButton? = null
    private var tabBottomBarMine: RadioButton? = null
    private var tvShoppingCount: TextView? = null

    companion object {
        var mMainFragment: Fragment? = null
        var mMainClassificationFragment: Fragment? = null
        var mShoppingCarFragment: Fragment? = null
        var mMineFragment: Fragment? = null
        var mGameFragment: Fragment? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mRxManager.on(EventParams.EVENT_TYPE_TO_MAIN_FRAGMENT, Action1 {
            //接收设置选中首页
            mRadioGroup?.check(R.id.main_activity_bottom_main)
            mViewPage?.currentItem = 0
        })
        mRxManager.on(EventParams.EVENT_TYPE_TO_SHOPPING_CAR_FRAGMENT, Action1 {
            //接收设置选中购物车
            mRadioGroup?.check(R.id.main_activity_bottom_shopping_car)
            mViewPage?.currentItem = 2
        })
        mRxManager.on(EventParams.EVENT_TYPE_TO_CASSIFICATION_1, Action1 {
            //接收设置选中分类页面
            mRxManager.post("1", null) //跳转首页
            mRadioGroup?.check(R.id.main_activity_bottom_classification)
            mViewPage?.currentItem = 1
        })
        mRxManager.on(EventParams.EVENT_TYPE_TO_CASSIFICATION_2, Action1 {
            //接收设置选中分类页面
            mRxManager.post("2", null) //跳转首页
            mRadioGroup?.check(R.id.main_activity_bottom_classification)
            mViewPage?.currentItem = 1
        })
        mRxManager.on(EventParams.EVENT_TYPE_TO_CASSIFICATION_3, Action1 {
            //接收设置选中分类页面
            mRxManager.post("3", null) //跳转首页
            mRadioGroup?.check(R.id.main_activity_bottom_classification)
            mViewPage?.currentItem = 1
        })
        mRxManager.on(EventParams.EVENT_TYPE_TO_CASSIFICATION_4, Action1 {
            //接收设置选中分类页面
            mRxManager.post("4", null) //跳转首页
            mRadioGroup?.check(R.id.main_activity_bottom_classification)
            mViewPage?.currentItem = 1
        })
        mRxManager.on(EventParams.EVENT_TYPE_TO_CASSIFICATION_5, Action1 {
            //接收设置选中分类页面
            mRxManager.post("5", null) //跳转首页
            mRadioGroup?.check(R.id.main_activity_bottom_classification)
            mViewPage?.currentItem = 1
        })
        mRxManager.on(EventParams.EVENT_TYPE_TO_CASSIFICATION_6, Action1 {
            //接收设置选中分类页面
            mRxManager.post("6", null) //跳转首页
            mRadioGroup?.check(R.id.main_activity_bottom_classification)
            mViewPage?.currentItem = 1
        })
        mRxManager.on(EventParams.EVENT_TYPE_TO_CASSIFICATION_7, Action1 {
            //接收设置选中分类页面
            mRxManager.post("7", null) //跳转首页
            mRadioGroup?.check(R.id.main_activity_bottom_classification)
            mViewPage?.currentItem = 1
        })
        mRxManager.on(EventParams.EVENT_TYPE_TO_CASSIFICATION_8, Action1 {
            //接收设置选中分类页面
            mRxManager.post("8", null) //跳转首页
            mRadioGroup?.check(R.id.main_activity_bottom_classification)
            mViewPage?.currentItem = 1
        })

    }

    override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
        when (checkedId) {
            R.id.main_activity_bottom_main -> currentFragment = 0
            R.id.main_activity_bottom_classification -> currentFragment = 1
            R.id.main_activity_bottom_shopping_car -> currentFragment = 2
            R.id.tab_bottom_bar_mine -> currentFragment = 3
        }
        mViewPage?.currentItem = currentFragment
    }

    override fun getLayoutResId(): Int {
        return R.layout.activity_main
    }

    override fun initPresenter() {
        mPresenter?.setVM(this, mModel)
    }

    override fun initIntentData() {

    }

    override fun initToolBar() {

    }

    override fun initView() {
        mRadioGroup = findViewById(R.id.rg_main_activity)
        mRadioGroup?.setOnCheckedChangeListener(this)
        tvShoppingCount = findViewById(R.id.tv_shopping_count)
        mainActivityBottomMain = findViewById(R.id.main_activity_bottom_main)
        mainActivityBottomClassification = findViewById(R.id.main_activity_bottom_classification)
        mainActivityBottomShoppingCar = findViewById(R.id.main_activity_bottom_shopping_car)
        tabBottomBarMine = findViewById(R.id.tab_bottom_bar_mine)
        mAdapter = ViewPageAdapter(supportFragmentManager)
        mViewPage = findViewById(R.id.view_page)
        mViewPage?.offscreenPageLimit = 5
        mViewPage?.currentItem = 0
        mViewPage?.adapter = mAdapter
        mViewPage?.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                if (position == 0) {
                    (mAdapter?.getItem(0) as MainFragment?)?.scrollToTop()
                }
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
    }

    private class ViewPageAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm) {
        override fun getItem(position: Int): Fragment? {
            return when (position) {
                0 -> {
                    if (mMainFragment == null) mMainFragment = MainFragment()
                    mMainFragment
                }
                1 -> {
                    if (mMainClassificationFragment == null) mMainClassificationFragment =
                        ClassificationFragment()
                    mMainClassificationFragment
                }
                2 -> {
                    if (mShoppingCarFragment == null) mShoppingCarFragment = ShoppingCarFragment()
                    mShoppingCarFragment
                }
                3 -> {
                    if (mMineFragment == null) mMineFragment = MineFragment()
                    mMineFragment
                }
//                4 -> {
//                    if (mGameFragment == null) mGameFragment = ShakeFragment()
//                    mGameFragment
//                }
                else -> null
            }
        }

        override fun getCount(): Int {
            return 4
        }
    }
}
