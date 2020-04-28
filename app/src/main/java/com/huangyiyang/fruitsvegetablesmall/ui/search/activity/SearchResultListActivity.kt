package com.huangyiyang.fruitsvegetablesmall.ui.search.activity

import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.*
import com.huangyiyang.fruitsvegetablesmall.Const
import com.huangyiyang.fruitsvegetablesmall.R
import com.huangyiyang.fruitsvegetablesmall.bean.GoodsDetailBean
import com.huangyiyang.fruitsvegetablesmall.bean.ShoppingCarCountBean
import com.huangyiyang.fruitsvegetablesmall.event.EventParams
import com.huangyiyang.fruitsvegetablesmall.event.ShoppingCarRefreshEvent
import com.huangyiyang.fruitsvegetablesmall.mvp.manage.UserManager
import com.huangyiyang.fruitsvegetablesmall.mvp.activity.BaseActivity
import com.huangyiyang.fruitsvegetablesmall.mvp.adapter.BaseQuickAdapter
import com.huangyiyang.fruitsvegetablesmall.ui.goods.activity.GoodsDetailActivity
import com.huangyiyang.fruitsvegetablesmall.ui.main.activity.MainActivity
import com.huangyiyang.fruitsvegetablesmall.ui.search.contract.SearchResultListActivityContract
import com.huangyiyang.fruitsvegetablesmall.ui.search.model.SearchResultListActivityModel
import com.huangyiyang.fruitsvegetablesmall.ui.search.presenter.SearchResultListActivityPresenter
import com.huangyiyang.fruitsvegetablesmall.mvp.util.*
import com.huangyiyang.fruitsvegetablesmall.view.main.CommonLayout
import com.huangyiyang.fruitsvegetablesmall.view.main.LoadingDialog
import com.huangyiyang.fruitsvegetablesmall.view.search.GridItemDecoration
import com.zhouyou.recyclerview.XRecyclerView
import com.zhouyou.recyclerview.adapter.AnimationType
import com.zhouyou.recyclerview.adapter.HelperRecyclerViewHolder
import org.greenrobot.eventbus.EventBus
import java.util.*

public class SearchResultListActivity : BaseActivity<SearchResultListActivityModel
        , SearchResultListActivityPresenter>()
, SearchResultListActivityContract.SearchResultListActivityView
, View.OnClickListener {

    private var mCommonLayout: CommonLayout? = null
    private var mAdapter: SearchListAdapter? = null
    private var mSearchHistoryAdapter: SearchHistoryListAdapter? = null
    private var mShoppingCar: ImageView? = null
    private var mEvKeyword: EditText? = null
    private var mIvDeleteKey: ImageView? = null
    private var mHistoryRecyclerView: RecyclerView? = null
    private var mResultRecyclerView: XRecyclerView? = null
    private var history_header: RelativeLayout? = null
    private var mKeyWorld = ""
    private var back: ImageView? = null
    private var mShoppingCarCount: TextView? = null

    companion object {

        fun goTo(context: Context) {
            val intent = Intent(context, SearchResultListActivity::class.java)
            context.startActivity(intent)
        }

        /**
         * 禁止EditText输入空格
         *
         * @param editText
         */
        fun setEditTextInhibitInputSpace(editText: EditText?) {
            val filter =
                InputFilter { source, start, end, dest, dstart, dend -> if (source == " ") "" else null }
            editText?.filters = arrayOf(filter)
        }
    }

    override fun getLayoutResId(): Int {
        return R.layout.activity_search_list
    }

    override fun initPresenter() {
        mPresenter?.setVM(this, mModel)
    }

    override fun initIntentData() {

    }

    override fun initToolBar() {

    }

    override fun initView() {
        mCommonLayout = findViewById(R.id.common_content)
        mPresenter!!.isE = true
        back = findViewById(R.id.back)
        back?.setOnClickListener(this)
        mShoppingCar = findViewById(R.id.shopping_car)
        mShoppingCar?.setOnClickListener(this)
        mShoppingCarCount = findViewById(R.id.shopping_car_count)
        mEvKeyword = findViewById<EditText>(R.id.keyword)
        setEditTextInhibitInputSpace(mEvKeyword)
        history_header = findViewById(R.id.history_header)
        mEvKeyword?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) {
            }

            override fun afterTextChanged(s: Editable) {
                mKeyWorld = s.toString()
                if (!StringUtil.isEmpty(mKeyWorld)) {
                    val goodsDetailParames: MutableMap<String, String> =
                        HashMap()
                    goodsDetailParames["categoryId"] = ""
                    goodsDetailParames["name"] = mKeyWorld.trim { it <= ' ' }
                    goodsDetailParames["price"] = ""
                    goodsDetailParames["stock"] = ""
                    goodsDetailParames["reducedPrice"] = ""
                    goodsDetailParames["pageSize"] = java.lang.String.valueOf(mPresenter?.mPageSize)
                    mPresenter!!.initLoadParams(Const.header(), goodsDetailParames)
                    //初始
                    mPresenter!!.initLoadView(mCommonLayout, mResultRecyclerView, mAdapter)
                    showResult()
                    UserManager.getInstance()!!.saveKeyWord(mKeyWorld)
                }
            }
        })
        mIvDeleteKey = findViewById<ImageView>(R.id.iv_delete_key)
        mIvDeleteKey?.setOnClickListener(this)

        //搜索历史
        mHistoryRecyclerView = findViewById<RecyclerView>(R.id.history_recyclerView)
        val layoutManager = FlexboxLayoutManager(this)
        layoutManager.flexWrap = FlexWrap.WRAP //设置是否换行

        layoutManager.flexDirection = FlexDirection.ROW // 设置主轴排列方式

        layoutManager.alignItems = AlignItems.STRETCH
        layoutManager.justifyContent = JustifyContent.FLEX_START
        mHistoryRecyclerView?.layoutManager = layoutManager
        mSearchHistoryAdapter = SearchHistoryListAdapter(this)
        mHistoryRecyclerView?.adapter = mSearchHistoryAdapter

        if (UserManager.getInstance()?.getKeyWord() != null) {
            mSearchHistoryAdapter?.setListAll(
                GsonUtil.GsonToList(
                    UserManager.getInstance()?.getKeyWord().toString(),
                    String::class.java
                )
            )
        }
        //搜索结果
        mResultRecyclerView = findViewById<XRecyclerView>(R.id.result_recyclerView)
        mAdapter = SearchListAdapter(this)
        mAdapter?.setItemAnimation(AnimationType.SLIDE_FROM_BOTTOM)
        mResultRecyclerView?.addItemDecoration(GridItemDecoration(this, 2, 8, false))
        mResultRecyclerView?.layoutManager = GridLayoutManager(this, 2)
        mResultRecyclerView?.adapter = mAdapter
        showKeyHistory()
        mPresenter!!.getShoppingCarCount(Const.header())
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.back -> finish()
            R.id.shopping_car -> {
                MainActivity.goTo(this)
                mRxManager.post(EventParams.EVENT_TYPE_TO_SHOPPING_CAR_FRAGMENT, null) //跳转首页===>购物车
            }
            R.id.iv_delete_key -> {
                UserManager.getInstance()?.clearKeyWords()
                mSearchHistoryAdapter?.setListAll(ArrayList<String>())
            }
        }
    }

    private fun showKeyHistory() {
        history_header!!.visibility = View.VISIBLE
        mHistoryRecyclerView!!.visibility = View.VISIBLE
        mCommonLayout!!.visibility = View.GONE
    }

    private fun showResult() {
        history_header!!.visibility = View.GONE
        mHistoryRecyclerView!!.visibility = View.GONE
        mCommonLayout!!.visibility = View.VISIBLE
    }

    override fun setRecommendGoodsListInfo(goodsBeanList: List<GoodsDetailBean?>?) {
        if (goodsBeanList != null && goodsBeanList.isNotEmpty()) {
            mCommonLayout!!.showContent()
            mAdapter!!.setListAll(goodsBeanList)
        } else {
            mCommonLayout!!.showEmpty()
        }
    }

    override fun addShoppingCar() {
        EventBus.getDefault().post(ShoppingCarRefreshEvent(true))
        mPresenter!!.getShoppingCarCount(Const.header())
        LoadingDialog.cancelDialogForLoading()
    }

    override fun setShoppingCarCount(bean: ShoppingCarCountBean?) {
        if (bean?.count!! > 0) {
            mShoppingCarCount?.text = java.lang.String.valueOf(bean.count)
            mShoppingCarCount!!.visibility = View.VISIBLE
        } else {
            mShoppingCarCount!!.visibility = View.INVISIBLE
        }
    }

    private inner class SearchListAdapter internal constructor(context: Context?) :
        BaseQuickAdapter<GoodsDetailBean>(context, R.layout.item_main_recommend_list) {
        override fun HelperBindData(
            viewHolder: HelperRecyclerViewHolder,
            position: Int,
            data: GoodsDetailBean
        ) {
            val mGoodsImg =
                viewHolder.getView<ImageView>(R.id.iv_item_goods_img) //商品图
            ImageLoaderUtil.getInstance()?.load(mGoodsImg, data.imageUrls?.split("&&")?.get(1))
            val mGoodsName = viewHolder.getView<TextView>(R.id.tv_item_goods_name) //商品名称
            mGoodsName.text = data.name
            val mGoodsUnit =
                viewHolder.getView<TextView>(R.id.tv_item_goods_price_unit) //商品单位
            mGoodsUnit.text = "/" + data.specification
            val mGoodsActivity =
                viewHolder.getView<TextView>(R.id.tv_item_goods_activity) //商品活动
//            if (data.getLabels().size() > 0) {
//                mGoodsActivity.setText(data.getLabels().get(data.getLabels().size() - 1))
//                mGoodsActivity.visibility = View.VISIBLE
//            } else {
//                mGoodsActivity.visibility = View.INVISIBLE
//            }
            val mGoodsPrice =
                viewHolder.getView<TextView>(R.id.tv_item_goods_price) //商品优惠价格
            val mGoodsOldPrice =
                viewHolder.getView<TextView>(R.id.tv_item_goods_old_price) //商品原价
            if (data.reducedPrice > 0) { //判断是否有优惠价格
                mGoodsPrice.text = getString(R.string.common_amount, data.reducedPrice)
                mGoodsOldPrice.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG //中划线
                mGoodsOldPrice.text = getString(R.string.common_amount, data.price)
                mGoodsOldPrice.visibility = View.VISIBLE
            } else {
                mGoodsPrice.text = getString(R.string.common_amount, data.price)
                mGoodsOldPrice.visibility = View.INVISIBLE
            }
            viewHolder.itemView.setOnClickListener {
                GoodsDetailActivity.goTo(
                    this@SearchResultListActivity,
                    java.lang.String.valueOf(data.id)
                )
            }
            val shoppingCar =
                viewHolder.getView<ImageView>(R.id.iv_item_goods_shoppingcar) //购物车按钮
            shoppingCar.setOnClickListener {
                // 对最大下单量，最小下单量进行判断
                var count: Double = data.minimumIncrementQuantity
                if (DoubleUtil.compare(
                        data.minimumIncrementQuantity,
                        data.maximumOrderQuantity
                    ) === -1
                ) { // 最小增减量 > 最大下单量
                    count = data.maximumOrderQuantity
                } else if (DoubleUtil.compare(
                        data.minimumIncrementQuantity,
                        data.minimunOrderQuantity
                    ) === 1
                ) { // 最小增减量 < 最小下单量
                    count = data.minimunOrderQuantity
                }
                val map: MutableMap<String, Number> =
                    HashMap()
                map["goodsId"] = data.id
                map["quantity"] = count
                mPresenter?.addShoppingCar(
                    Const.header(),
                    ParamsUtil.getInstance()?.getBodyNumber(map)
                )
                LoadingDialog.showDialogForLoading(
                    this@SearchResultListActivity,
                    getString(R.string.call_back_loading),
                    false
                )
            }
        }
    }

    private inner class SearchHistoryListAdapter internal constructor(context: Context?) :
        BaseQuickAdapter<String>(context, R.layout.item_history_search_list) {
        override fun HelperBindData(
            viewHolder: HelperRecyclerViewHolder,
            position: Int,
            data: String?
        ) {
            val mSearchHistoryKey =
                viewHolder.getView<TextView>(R.id.tv_item_search_history_text) //搜索历史
            mSearchHistoryKey.text = data
            viewHolder.itemView.setOnClickListener { mEvKeyword?.setText(mSearchHistoryKey.text.toString()) }
        }
    }

}