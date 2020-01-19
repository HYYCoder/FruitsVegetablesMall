package com.huangyiyang.fruitsvegetablesmall.ui.cassification.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.huangyiyang.fruitsvegetablesmall.R
import com.huangyiyang.fruitsvegetablesmall.bean.CategoryListBean
import com.huangyiyang.fruitsvegetablesmall.bean.RecommendGoodsBean
import com.huangyiyang.fruitsvegetablesmall.mvp.adapter.BaseQuickAdapter
import com.huangyiyang.fruitsvegetablesmall.mvp.fragment.BaseFragment
import com.huangyiyang.fruitsvegetablesmall.ui.cassification.contract.ClassificationDetailFragmentContract
import com.huangyiyang.fruitsvegetablesmall.ui.cassification.model.ClassificationDetailFragmentModel
import com.huangyiyang.fruitsvegetablesmall.ui.cassification.presenter.ClassificationDetailFragmentPresenter
import com.huangyiyang.fruitsvegetablesmall.view.main.CommonLayout
import com.huangyiyang.fruitsvegetablesmall.view.main.LoadingDialog
import com.zhouyou.recyclerview.XRecyclerView
import com.zhouyou.recyclerview.XRecyclerView.LoadingListener
import com.zhouyou.recyclerview.adapter.HelperRecyclerViewHolder

class CassificationDetailFragment : ClassificationDetailFragmentContract.ClassificationDetailFragmentView
    , View.OnClickListener,BaseFragment<ClassificationDetailFragmentModel
            , ClassificationDetailFragmentPresenter>(){

    private val BEAN = "bean"
    private var categoryListBean: CategoryListBean? = null
    private var mXRecyclerViewOne: XRecyclerView? = null
    private var classificationDetaiListAdapter: ClassificationDetaiListAdapter? =
        null
    private var common_layout: CommonLayout? = null
    private val categoryId = 0
    private val imagUrl: String? = null
    companion object{

        private var ID = "id"

        fun newInstance(id: Int?): CassificationDetailFragment? {
            val args = Bundle()
            args.putInt(ID, id!!)
            val fragment = CassificationDetailFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onClick(v: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getLayoutResId(): Int {
        return R.layout.fragment_cassification_detail
    }

    override fun initPresenter() {
        mPresenter?.setVM(this, mModel)
    }

    override fun initArgumentsData() {

    }

    override fun initToolBar() {

    }

    override fun initView() {
        common_layout = layout!!.findViewById(R.id.common_layout)
        classificationDetaiListAdapter = ClassificationDetaiListAdapter(activity!!)
        mXRecyclerViewOne = layout!!.findViewById(R.id.classification_list)
        mXRecyclerViewOne?.layoutManager = LinearLayoutManager(activity)
        mXRecyclerViewOne?.isPullRefreshEnabled = true
        mXRecyclerViewOne?.isLoadingMoreEnabled = false
        mXRecyclerViewOne?.adapter = classificationDetaiListAdapter
        mXRecyclerViewOne?.setLoadingListener(object : LoadingListener {
            override fun onRefresh() {
                mPresenter!!.onRefresh()
            }

            override fun onLoadMore() {
                mPresenter!!.onLoadMore()
            }
        })
    }

    override fun initData() {

    }

    override fun onInvisible() {
        super.onInvisible()
        LoadingDialog().cancelDialogForLoading()
    }

    class ClassificationDetaiListAdapter : BaseQuickAdapter<RecommendGoodsBean>{
        constructor(context: Context) : super(context, R.layout.item_classification_three_list)

        override fun HelperBindData(
            viewHolder: HelperRecyclerViewHolder?,
            position: Int,
            item: RecommendGoodsBean?
        ) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }
}