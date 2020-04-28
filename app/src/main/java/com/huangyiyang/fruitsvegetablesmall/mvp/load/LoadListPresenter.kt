package com.huangyiyang.fruitsvegetablesmall.mvp.load

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.huangyiyang.fruitsvegetablesmall.R
import com.huangyiyang.fruitsvegetablesmall.mvp.http.ApiCallBack
import com.huangyiyang.fruitsvegetablesmall.mvp.http.ServerException
import com.huangyiyang.fruitsvegetablesmall.mvp.adapter.BaseQuickAdapter
import com.huangyiyang.fruitsvegetablesmall.mvp.presenter.BasePresenter
import com.huangyiyang.fruitsvegetablesmall.view.main.CommonLayout
import com.huangyiyang.fruitsvegetablesmall.view.main.LoadingDialog
import com.zhouyou.recyclerview.XRecyclerView
import com.zhouyou.recyclerview.XRecyclerView.LoadingListener
import com.zhouyou.recyclerview.refresh.BaseRefreshHeader
import com.zhouyou.recyclerview.refresh.LoadingMoreFooter
import com.zhouyou.recyclerview.refresh.ProgressStyle
import java.util.*

abstract class LoadListPresenter<T, M, V> : LoadingListener , BasePresenter<M, V> {

    var mPage = 0
    var mPageSize = 10
    var isE = false
    var mParamsMap: MutableMap<String, String>? = null
    var header: Map<String, String>? = null
    var param: String? = null
    private var mCommonLayout: CommonLayout? = null
    private var mRecyclerView: XRecyclerView? = null
    private var mAdapter: BaseQuickAdapter<T>? = null
    private var mCallBack: ApiCallBack<List<T>?>? = null
    private var mSwipeRefreshLayout: SwipeRefreshLayout? = null

    constructor()

    constructor(pageSize: Int){
        mPageSize = pageSize
    }

    open fun initLoadParams(
        header: Map<String, String>?,
        params: MutableMap<String, String>?
    ) {
        this.header = header
        mParamsMap = params
    }

    open fun initLoadParams(
        header: Map<String, String>?,
        param: String?
    ) {
        this.header = header
        this.param = param
    }

    open fun initLoadParams(
        header: Map<String, String>?,
        param: String?,
        params: MutableMap<String, String>?
    ) {
        this.header = header
        mParamsMap = params
        this.param = param
    }

    open fun initLoadView(
        commonLayout: CommonLayout?,
        recyclerView: XRecyclerView?,
        adapter: BaseQuickAdapter<T>?
    ) {
        initLoadView(commonLayout, recyclerView, adapter, null, null)
    }

    open fun initLoadView(
        commonLayout: CommonLayout?,
        refreshLayout: SwipeRefreshLayout?,
        recyclerView: XRecyclerView?,
        adapter: BaseQuickAdapter<T>?
    ) {
        initLoadView(commonLayout, recyclerView, adapter, null, refreshLayout)
    }

    open fun initLoadView(
        commonLayout: CommonLayout?,
        recyclerView: XRecyclerView?,
        adapter: BaseQuickAdapter<T>?,
        refreshHeader: BaseRefreshHeader?,
        refreshLayout: SwipeRefreshLayout?
    ) {
        val footer = LoadingMoreFooter(mContext)
        footer.setProgressStyle(ProgressStyle.TriangleSkewSpin)
        mCommonLayout = commonLayout
        mRecyclerView = recyclerView
        mAdapter = adapter
        mRecyclerView!!.setLoadingMoreFooter(footer)
        mRecyclerView!!.isLoadingMoreEnabled = true
        mRecyclerView!!.setLoadingListener(this)
        if (refreshHeader != null) {
            mRecyclerView!!.refreshHeader = refreshHeader
        } else {
            mRecyclerView!!.setRefreshProgressStyle(ProgressStyle.Pacman)
        }
        mRecyclerView!!.setFootViewText(
            mContext.getString(R.string.call_back_loading_more),
            mContext.getString(R.string.common_no_more_date)
        )
        mCommonLayout?.setContentView(recyclerView)
        reload()
        mSwipeRefreshLayout = refreshLayout
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout?.isEnabled = false
        }
    }

    private fun reload() {
        mRecyclerView!!.reset()
        mAdapter?.setListAll(ArrayList<T>())
        mPage = 0
        // mPageSize = 10;
        mCommonLayout?.showLoading()
        if (mParamsMap != null) {
            mParamsMap!!["current"] = mPage.toString()
        }
        requestNextPage()
    }

    private fun loadMore() {
        if (mParamsMap != null) {
            mParamsMap!!["current"] = mPage.toString()
        }
        requestNextPage()
    }

    abstract fun requestNextPage()
    open fun response(bean: List<T>?) {}
    open fun errorBack(exception: ServerException?) {}

    open fun getCallBack(): ApiCallBack<List<T>?>? {
        mCallBack = object : ApiCallBack<List<T>?>(mContext) {
            override fun _onNext(
                bean: List<T>?,
                message: String?
            ) {
                if (bean == null || bean == null) {
                    if (mPage == 0) {
                        if (isE) { //                              mCommonLayout.cancelShow();
                            mCommonLayout?.showEmpty()
                        } else {
                            mCommonLayout?.showContent()
                        }
                        if (mSwipeRefreshLayout != null) {
                            mSwipeRefreshLayout?.isEnabled = true
                        }
                        //                        mRecyclerView.loadMoreComplete();
//                        mRecyclerView.setNoMore(true);//没有下一页
                    } else {
                        mRecyclerView!!.setNoMore(true) //没有下一页
                    }
                    LoadingDialog
                        .cancelDialogForLoading()
                    return
                }
                if (bean.size <= mPageSize) {
                    mAdapter?.addItemsToLast(bean)
                    if (bean.size >= mPageSize) {
                        mRecyclerView!!.loadMoreComplete()
                    } else {
                        mRecyclerView!!.setNoMore(true) //没有下一页
                    }
                }
                if (mPage == 0) {
                    if (bean.isNotEmpty()) {
                        mPageSize = bean.size // API可能只需要page, 而不需要page size
                        if (mPageSize < 6) { //
                            mPageSize = 6
                        }
                        mCommonLayout?.showContent()
                        if (mSwipeRefreshLayout != null) {
                            mSwipeRefreshLayout?.isEnabled = false
                        }
                    } else {
                        if (isE) { //                              mCommonLayout.cancelShow();
                            mCommonLayout?.showEmpty()
                        } else {
                            mCommonLayout?.showContent()
                        }
                        mRecyclerView!!.loadMoreComplete()
                    }
                }
                mPage++
                if (mRecyclerView!!.isRefreshing) { //                    mRecyclerView.refreshComplete();
                }
                if (mSwipeRefreshLayout != null) {
                    if (mSwipeRefreshLayout!!.isRefreshing) {
                        mSwipeRefreshLayout?.isRefreshing = false
                    }
                }
                response(bean)
                LoadingDialog.cancelDialogForLoading()
            }

            override fun _onError(exception: ServerException?) {
                errorBack(exception)
                println(exception)
                if (mPage == 0) {
                    if (!exception?.mErrorCode.equals(ServerException().ERROR_NO_DATA)) {
                        mCommonLayout?.showError()
                        if (mSwipeRefreshLayout != null) {
                            mSwipeRefreshLayout?.isEnabled = true
                        }
                    } else {
                        mCommonLayout?.showEmpty()
                        if (mSwipeRefreshLayout != null) {
                            mSwipeRefreshLayout?.isEnabled = true
                        }
                    }
                } else {
                    if (exception?.mErrorCode.equals(ServerException().ERROR_NO_DATA)) {
                        mRecyclerView!!.setNoMore(true) //没有下一页
                    } else {
                        mRecyclerView!!.setFootViewText(
                            mContext.getString(R.string.call_back_loading_more),
                            mContext.getString(R.string.call_back_loading_failed)
                        )
                        mRecyclerView!!.setNoMore(true)
                    }
                }
                if (mRecyclerView!!.isRefreshing) { //                    mRecyclerView.refreshComplete();
                }
                if (mSwipeRefreshLayout != null) {
                    if (mSwipeRefreshLayout!!.isRefreshing) {
                        mSwipeRefreshLayout?.isRefreshing = false
                    }
                }
                LoadingDialog.cancelDialogForLoading()
            }
        }
        return mCallBack
    }

    open fun showEmpty() {
        mCommonLayout?.showEmpty()
    }

    //刷新
    override fun onRefresh() {
        reload()
    }

    //加载更多
    override fun onLoadMore() {
        LoadingDialog.showDialogForLoading(
            mContext,
            mContext.getString(R.string.call_back_loading),
            false
        )
        loadMore()
    }
}