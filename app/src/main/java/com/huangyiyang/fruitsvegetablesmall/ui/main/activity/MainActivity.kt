package com.huangyiyang.fruitsvegetablesmall.ui.main.activity

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.gyf.immersionbar.ktx.immersionBar
import com.huangyiyang.fruitsvegetablesmall.R
import com.huangyiyang.fruitsvegetablesmall.event.EventParams
import com.huangyiyang.fruitsvegetablesmall.event.ShoppingCountEvent
import com.huangyiyang.fruitsvegetablesmall.manage.UserManager
import com.huangyiyang.fruitsvegetablesmall.mvp.activity.BaseActivity
import com.huangyiyang.fruitsvegetablesmall.ui.cassification.fragment.ClassificationFragment
import com.huangyiyang.fruitsvegetablesmall.ui.login.activity.LoginActivity
import com.huangyiyang.fruitsvegetablesmall.ui.main.contract.MainActivityContract
import com.huangyiyang.fruitsvegetablesmall.ui.main.fragment.MainFragment
import com.huangyiyang.fruitsvegetablesmall.ui.main.model.MainActivityModel
import com.huangyiyang.fruitsvegetablesmall.ui.main.presenter.MainActivityPresenter
import com.huangyiyang.fruitsvegetablesmall.ui.mine.fragment.MineFragment
import com.huangyiyang.fruitsvegetablesmall.ui.shoppingCar.fragment.ShoppingCarFragment
import com.huangyiyang.fruitsvegetablesmall.util.PermissionUtil
import com.huangyiyang.fruitsvegetablesmall.util.ToastUtil
import com.huangyiyang.fruitsvegetablesmall.view.main.NoSwipeableViewPager
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import rx.functions.Action1

class MainActivity : MainActivityContract.MainActivityView, RadioGroup.OnCheckedChangeListener, BaseActivity<MainActivityModel,MainActivityPresenter>() {

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
        var isForeground = false
        var mMainFragment: Fragment? = null
        var mMainClassificationFragment: Fragment? = null
        var mShoppingCarFragment: Fragment? = null
        var mMineFragment: Fragment? = null

        fun goTo(context: Activity) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 动态权限申请
        PermissionUtil.ApplyWithOutCallBack(
            this, Manifest.permission.READ_EXTERNAL_STORAGE
            , Manifest.permission.WRITE_EXTERNAL_STORAGE
            , Manifest.permission.CAMERA
            , Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CALL_PHONE
        )

        immersionBar {
            statusBarColor(R.color.blue_4289ff)
            navigationBarColor(R.color.blue_4289ff)
        }

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

        EventBus.getDefault().register(this)
    }

    override fun onStart() {
        super.onStart()
        if (UserManager.getInstance()?.getToken_EXPIRED() != null && UserManager.getInstance()?.getToken_EXPIRED().equals(
                "t"
            )
        ) {
            ToastUtil.showLong(this, "登录已过期，请重新登录")
            LoginActivity.isL = false
        }
        if (!LoginActivity.isR) {
            //checkAppVersionCode()
        }
    }

    override fun onResume() {
        super.onResume()
        isForeground = true
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun setShoppingCount(event: ShoppingCountEvent) {
        val count: Int = event.count
        if (count == 0) {
            tvShoppingCount!!.visibility = View.GONE
        } else if (count >= 100) {
            tvShoppingCount!!.visibility = View.VISIBLE
            tvShoppingCount!!.text = "99+"
        } else {
            tvShoppingCount!!.visibility = View.VISIBLE
            tvShoppingCount!!.text = count.toString()
        }
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
                else -> null
            }
        }

        override fun getCount(): Int {
            return 4
        }
    }
}
