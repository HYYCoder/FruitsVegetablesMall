package com.huangyiyang.fruitsvegetablesmall.ui.order.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.gyf.immersionbar.ktx.immersionBar
import com.huangyiyang.fruitsvegetablesmall.R
import com.huangyiyang.fruitsvegetablesmall.mvp.activity.BaseActivity
import com.huangyiyang.fruitsvegetablesmall.ui.order.contract.OrderListActivityContract
import com.huangyiyang.fruitsvegetablesmall.ui.order.fragment.OrderListFragment
import com.huangyiyang.fruitsvegetablesmall.ui.order.model.OrderListActivityModel
import com.huangyiyang.fruitsvegetablesmall.ui.order.presenter.OrderListActivityPresenter
import com.huangyiyang.fruitsvegetablesmall.view.shoppingCar.ToolbarUtil
import java.util.*

class OrderListActivity : OrderListActivityContract.OrderListActivityView, View.OnClickListener,BaseActivity<OrderListActivityModel
        , OrderListActivityPresenter>(){

    private var toolbarUtil: ToolbarUtil? = null
    private var allPagerTitle: List<String> = arrayListOf("全部订单", "待支付", "待收货", "已完成")
    private var pagerTitle: List<String>? = null
    private var tabLayout: TabLayout? = null
    private var viewPager: ViewPager? = null
    private var index = 0

    companion object{

        private var KEY_INDEX = "index"

        fun goTo(context: Context, index: Int) {
            val intent = Intent(context, OrderListActivity::class.java)
            intent.putExtra(KEY_INDEX, index)
            if (context !is Activity) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
        }
    }

    override fun getLayoutResId(): Int {
        return R.layout.activity_order_list
    }

    override fun initPresenter() {
        mPresenter?.setVM(this, mModel)
    }

    override fun initIntentData() {
        index = intent.getIntExtra(KEY_INDEX, 0)
    }

    override fun initToolBar() {
        toolbarUtil = ToolbarUtil(this,mContext)
        toolbarUtil!!.setLightBackTheme("订单列表")
        toolbarUtil!!.setLineBackgroundColor(R.color.white_ffffff)
    }

    override fun initView() {
        immersionBar {
            statusBarColor(R.color.green_4CAF65)
            navigationBarColor(R.color.green_4CAF65)
            autoDarkModeEnable(true)
        }
        tabLayout = findViewById(R.id.tab_layout)
        viewPager = findViewById(R.id.vp_detail)
        viewPager?.setOffscreenPageLimit(4)
        tabLayout?.setTabGravity(TabLayout.GRAVITY_FILL)
        tabLayout?.setTabMode(TabLayout.MODE_FIXED)
        tabLayout?.setupWithViewPager(viewPager)

        pagerTitle = allPagerTitle
        val fragments: MutableList<OrderListFragment> =
            ArrayList<OrderListFragment>()
        fragments.add(OrderListFragment.newInstance(1)!!)
        fragments.add(OrderListFragment.newInstance(2)!!)
        fragments.add(OrderListFragment.newInstance(3)!!)
        fragments.add(OrderListFragment.newInstance(4)!!)
        viewPager?.setAdapter(
            ViewPagerAdapter(
                supportFragmentManager,
                fragments
            )
        )
        if (index > 0) {
            viewPager?.setCurrentItem(index)
        }
    }

    override fun onClick(v: View?) {

    }

    inner class ViewPagerAdapter : FragmentPagerAdapter{

        var list: List<OrderListFragment>? = null

        constructor(fm: FragmentManager, list: List<OrderListFragment>):super(fm){
            this.list = list
        }

        override fun getItem(position: Int): Fragment {
            return list?.get(position)!!
        }

        override fun getCount(): Int {
            return list?.size!!
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return return pagerTitle?.get(position)
        }
    }
}