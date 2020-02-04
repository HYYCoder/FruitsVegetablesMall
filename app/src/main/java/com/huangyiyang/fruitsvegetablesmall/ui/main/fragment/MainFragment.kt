package com.huangyiyang.fruitsvegetablesmall.ui.main.fragment

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.huangyiyang.fruitsvegetablesmall.R
import com.huangyiyang.fruitsvegetablesmall.api.Const
import com.huangyiyang.fruitsvegetablesmall.api.FrameConst
import com.huangyiyang.fruitsvegetablesmall.bean.CategoryListBean
import com.huangyiyang.fruitsvegetablesmall.bean.RecommendGoodsBean
import com.huangyiyang.fruitsvegetablesmall.mvp.adapter.BaseQuickAdapter
import com.huangyiyang.fruitsvegetablesmall.mvp.fragment.BaseFragment
import com.huangyiyang.fruitsvegetablesmall.ui.main.contract.MainFragmentContract
import com.huangyiyang.fruitsvegetablesmall.ui.main.model.MainFragmentModel
import com.huangyiyang.fruitsvegetablesmall.ui.main.presenter.MainFragmentPresenter
import com.huangyiyang.fruitsvegetablesmall.util.BannerUtil
import com.huangyiyang.fruitsvegetablesmall.view.main.CommonLayout
import com.youth.banner.listener.OnBannerListener
import com.zhouyou.recyclerview.XRecyclerView
import com.zhouyou.recyclerview.XRecyclerView.LoadingListener
import com.zhouyou.recyclerview.adapter.AnimationType
import com.zhouyou.recyclerview.adapter.HelperRecyclerViewHolder

class MainFragment :MainFragmentContract.MainFragmentView, View.OnClickListener,
    BannerUtil.OnLoadFinish, BaseFragment<MainFragmentModel,MainFragmentPresenter>(){

    private var mXRecyclerView: XRecyclerView? = null
    private var mCommonLayout: CommonLayout? = null
    private var mAdapter: SearchListAdapter? = null
    private var staggeredGridLayoutManager: StaggeredGridLayoutManager? = null
    private var headerView: View? = null
    private var mSearchBar: TextView? = null
    private var mBannerUtil : BannerUtil? = null
    private var ivItemMainHomeMerchantIcon1: ImageView? = null
    private var tvItemMainHomeMerchantTitle1: TextView? = null
    private var ivItemMainHomeMerchantIcon2: ImageView? = null
    private var tvItemMainHomeMerchantTitle2: TextView? = null
    private var ivItemMainHomeMerchantIcon3: ImageView? = null
    private var tvItemMainHomeMerchantTitle3: TextView? = null
    private var ivItemMainHomeMerchantIcon4: ImageView? = null
    private var tvItemMainHomeMerchantTitle4: TextView? = null
    private var ivItemMainHomeMerchantIcon5: ImageView? = null
    private var tvItemMainHomeMerchantTitle5: TextView? = null
    private var ivItemMainHomeMerchantIcon6: ImageView? = null
    private var tvItemMainHomeMerchantTitle6: TextView? = null
    private var ivItemMainHomeMerchantIcon7: ImageView? = null
    private var tvItemMainHomeMerchantTitle7: TextView? = null
    private var ivItemMainHomeMerchantIcon8: ImageView? = null
    private var tvItemMainHomeMerchantTitle8: TextView? = null

    fun scrollToTop() {
        if (mXRecyclerView == null) return
        mXRecyclerView?.postDelayed(
            { mXRecyclerView?.scrollToPosition(View.SCROLL_INDICATOR_TOP) },
            200
        )
    }

    override fun getLayoutResId(): Int {
        return R.layout.fragment_main
    }

    override fun initPresenter() {
        mPresenter?.setVM(this, mModel)
    }

    override fun initArgumentsData() {

    }

    override fun initToolBar() {

    }

    override fun initView() {
        mCommonLayout = layout?.findViewById(R.id.common_content)
        mAdapter = SearchListAdapter(this.context)
        mAdapter?.setItemAnimation(AnimationType.SLIDE_FROM_BOTTOM)
        mXRecyclerView = layout?.findViewById(R.id.main_list) as XRecyclerView
        mXRecyclerView?.isPullRefreshEnabled = false

        //mXRecyclerView.addItemDecoration(new GridItemDecoration(getActivity(), 2, 8, false));
        //mXRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        //mXRecyclerView.addItemDecoration(new GridItemDecoration(getActivity(), 2, 8, false));
        //mXRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        staggeredGridLayoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        mXRecyclerView?.layoutManager = staggeredGridLayoutManager
        //mXRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.HORIZONTAL));
        //mXRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.HORIZONTAL));
        mXRecyclerView?.isLoadingMoreEnabled = true
        mXRecyclerView?.isPullRefreshEnabled = true
        mXRecyclerView?.setLoadingListener(object : LoadingListener {
            override fun onRefresh() {
                //mPresenter.onRefresh()
                mXRecyclerView?.refreshComplete()
            }

            override fun onLoadMore() {
                //mPresenter.onLoadMore()
            }
        })
        mXRecyclerView?.setAdapter(mAdapter)


        headerView = LayoutInflater.from(activity).inflate(
            R.layout.header_view_main_home,
            mXRecyclerView?.parent as ViewGroup,
            false
        )

        mXRecyclerView?.addHeaderView(headerView)

        mSearchBar = headerView?.findViewById(R.id.search_bar)

        //svMain = headerView.findViewById(R.id.sv_main);
        //svMain = headerView.findViewById(R.id.sv_main);
        mBannerUtil = BannerUtil(headerView)
        mSearchBar?.setOnClickListener(this)
        mBannerUtil?.setOnLoadFinish(this)


        //加载分类ui
        ivItemMainHomeMerchantIcon1 =
            headerView?.findViewById(R.id.iv_item_main_home_merchant_icon_1)
        ivItemMainHomeMerchantIcon1?.setOnClickListener(this)
        tvItemMainHomeMerchantTitle1 =
            headerView?.findViewById(R.id.tv_item_main_home_merchant_title_1)

        ivItemMainHomeMerchantIcon2 =
            headerView?.findViewById(R.id.iv_item_main_home_merchant_icon_2)
        ivItemMainHomeMerchantIcon2?.setOnClickListener(this)
        tvItemMainHomeMerchantTitle2 =
            headerView?.findViewById(R.id.tv_item_main_home_merchant_title_2)

        ivItemMainHomeMerchantIcon3 =
            headerView?.findViewById(R.id.iv_item_main_home_merchant_icon_3)
        ivItemMainHomeMerchantIcon3?.setOnClickListener(this)
        tvItemMainHomeMerchantTitle3 =
            headerView?.findViewById(R.id.tv_item_main_home_merchant_title_3)

        ivItemMainHomeMerchantIcon4 =
            headerView?.findViewById(R.id.iv_item_main_home_merchant_icon_4)
        ivItemMainHomeMerchantIcon4?.setOnClickListener(this)
        tvItemMainHomeMerchantTitle4 =
            headerView?.findViewById(R.id.tv_item_main_home_merchant_title_4)

        ivItemMainHomeMerchantIcon5 =
            headerView?.findViewById(R.id.iv_item_main_home_merchant_icon_5)
        ivItemMainHomeMerchantIcon5?.setOnClickListener(this)
        tvItemMainHomeMerchantTitle5 =
            headerView?.findViewById(R.id.tv_item_main_home_merchant_title_5)

        ivItemMainHomeMerchantIcon6 =
            headerView?.findViewById(R.id.iv_item_main_home_merchant_icon_6)
        ivItemMainHomeMerchantIcon6?.setOnClickListener(this)
        tvItemMainHomeMerchantTitle6 =
            headerView?.findViewById(R.id.tv_item_main_home_merchant_title_6)

        ivItemMainHomeMerchantIcon7 =
            headerView?.findViewById(R.id.iv_item_main_home_merchant_icon_7)
        ivItemMainHomeMerchantIcon7?.setOnClickListener(this)
        tvItemMainHomeMerchantTitle7 =
            headerView?.findViewById(R.id.tv_item_main_home_merchant_title_7)

        ivItemMainHomeMerchantIcon8 =
            headerView?.findViewById(R.id.iv_item_main_home_merchant_icon_8)
        ivItemMainHomeMerchantIcon8?.setOnClickListener(this)
        tvItemMainHomeMerchantTitle8 =
            headerView?.findViewById(R.id.tv_item_main_home_merchant_title_8)

        //初始化轮播图点击事件
        mBannerUtil?.setBannerClickable(OnBannerListener { position ->
//            if (TextUtils.isEmpty(bannerLists.get(position).getEventId())) return@OnBannerListener
//            ActivityDetailActivity.goTo(
//                activity,
//                bannerLists.get(position).getEventId(),
//                false
//            )
        })

        mPresenter?.getRecommendGoodsList(Const.header())
    }

    override fun initData() {

    }

    override fun onClick(v: View?) {
        when (v!!.id) {
//            R.id.search_bar -> SearchResultListActivity.goTo(activity)
//            R.id.iv_item_main_home_merchant_icon_1 -> if (categoryListBean != null && categoryListBean.get(
//                    0
//                ) != null && categoryListBean.get(0).getCategory().getId() !== 0
//            ) mRxManager.post(EventParams.EVENT_TYPE_TO_CASSIFICATION_1, null) //跳转分类页面
//            R.id.iv_item_main_home_merchant_icon_2 -> if (categoryListBean != null && categoryListBean.get(
//                    1
//                ) != null && categoryListBean.get(1).getCategory().getId() !== 0
//            ) mRxManager.post(EventParams.EVENT_TYPE_TO_CASSIFICATION_2, null) //跳转分类页面
//            R.id.iv_item_main_home_merchant_icon_3 -> if (categoryListBean != null && categoryListBean.get(
//                    2
//                ) != null && categoryListBean.get(2).getCategory().getId() !== 0
//            ) mRxManager.post(EventParams.EVENT_TYPE_TO_CASSIFICATION_3, null) //跳转分类页面
//            R.id.iv_item_main_home_merchant_icon_4 -> if (categoryListBean != null && categoryListBean.get(
//                    3
//                ) != null && categoryListBean.get(3).getCategory().getId() !== 0
//            ) mRxManager.post(EventParams.EVENT_TYPE_TO_CASSIFICATION_4, null) //跳转分类页面
//            R.id.iv_item_main_home_merchant_icon_5 -> if (categoryListBean != null && categoryListBean.get(
//                    4
//                ) != null && categoryListBean.get(4).getCategory().getId() !== 0
//            ) mRxManager.post(EventParams.EVENT_TYPE_TO_CASSIFICATION_5, null) //跳转分类页面
//            R.id.iv_item_main_home_merchant_icon_6 -> if (categoryListBean != null && categoryListBean.get(
//                    5
//                ) != null && categoryListBean.get(5).getCategory().getId() !== 0
//            ) mRxManager.post(EventParams.EVENT_TYPE_TO_CASSIFICATION_6, null) //跳转分类页面
//            R.id.iv_item_main_home_merchant_icon_7 -> if (categoryListBean != null && categoryListBean.get(
//                    6
//                ) != null && categoryListBean.get(6).getCategory().getId() !== 0
//            ) mRxManager.post(EventParams.EVENT_TYPE_TO_CASSIFICATION_7, null) //跳转分类页面
//            R.id.iv_item_main_home_merchant_icon_8 -> if (categoryListBean != null && categoryListBean.get(
//                    7
//                ) != null && categoryListBean.get(7).getCategory().getId() !== 0
//            ) mRxManager.post(EventParams.EVENT_TYPE_TO_CASSIFICATION_8, null) //跳转分类页面
        }
    }

    override fun onLoadFinish() {

    }

    private class SearchListAdapter internal constructor(context: Context?) :
        BaseQuickAdapter<RecommendGoodsBean?>(context, R.layout.item_main_recommend_list) {

        override fun HelperBindData(
            viewHolder: HelperRecyclerViewHolder?,
            position: Int,
            item: RecommendGoodsBean?
        ) {

        }
    }

    override fun setRecommendGoodsListInfo(goodsBeanList: List<RecommendGoodsBean?>?) {
        Log.e("测试",goodsBeanList.toString())
    }

    override fun setCategoriesList(categoryListBean: List<CategoryListBean?>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}