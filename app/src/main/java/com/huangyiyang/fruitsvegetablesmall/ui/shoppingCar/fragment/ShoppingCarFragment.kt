package com.huangyiyang.fruitsvegetablesmall.ui.shoppingCar.fragment

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.View.OnTouchListener
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.huangyiyang.fruitsvegetablesmall.R
import com.huangyiyang.fruitsvegetablesmall.Const
import com.huangyiyang.fruitsvegetablesmall.bean.ShoppingCarCountBean
import com.huangyiyang.fruitsvegetablesmall.bean.ShoppingCarListBean
import com.huangyiyang.fruitsvegetablesmall.event.EventParams
import com.huangyiyang.fruitsvegetablesmall.event.ShoppingCarRefreshEvent
import com.huangyiyang.fruitsvegetablesmall.event.ShoppingCountEvent
import com.huangyiyang.fruitsvegetablesmall.mvp.adapter.BaseQuickAdapter
import com.huangyiyang.fruitsvegetablesmall.mvp.fragment.BaseFragment
import com.huangyiyang.fruitsvegetablesmall.ui.order.activity.ConfirmOrderActivity
import com.huangyiyang.fruitsvegetablesmall.ui.shoppingCar.contract.ShoppingCarFragmentContract
import com.huangyiyang.fruitsvegetablesmall.ui.shoppingCar.model.ShoppingCarFragmentModel
import com.huangyiyang.fruitsvegetablesmall.ui.shoppingCar.presenter.ShoppingCarFragmentPresenter
import com.huangyiyang.fruitsvegetablesmall.mvp.util.DoubleUtil
import com.huangyiyang.fruitsvegetablesmall.mvp.util.ImageLoaderUtil
import com.huangyiyang.fruitsvegetablesmall.mvp.util.ParamsUtil
import com.huangyiyang.fruitsvegetablesmall.mvp.util.ToastUtil
import com.huangyiyang.fruitsvegetablesmall.view.main.LoadingDialog
import com.huangyiyang.fruitsvegetablesmall.view.shoppingCar.DeleteAlertDialog
import com.huangyiyang.fruitsvegetablesmall.view.shoppingCar.ToolbarUtil
import com.zhouyou.recyclerview.XRecyclerView
import com.zhouyou.recyclerview.XRecyclerView.LoadingListener
import com.zhouyou.recyclerview.adapter.AnimationType
import com.zhouyou.recyclerview.adapter.HelperRecyclerViewHolder
import com.zhouyou.recyclerview.refresh.ProgressStyle
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.math.BigDecimal
import java.text.DecimalFormat
import java.util.*
import kotlin.collections.ArrayList

class ShoppingCarFragment : ShoppingCarFragmentContract.ShoppingCarFragmentView,View.OnClickListener, BaseFragment<ShoppingCarFragmentModel,ShoppingCarFragmentPresenter>() {

    private var mShoppingCarList: XRecyclerView? = null
    private var rlEmpty: RelativeLayout? = null
    private var btnEmpty: Button? = null
    private var mAdapter: ShoppingCarAdapter? = null
    private var btnSettlement: Button? = null
    private var mTvTotal: TextView? = null
    private var toolbarUtil: ToolbarUtil? = null
    private var mCheckAll: CheckBox? = null
    private var shoppingCarListBean: ShoppingCarListBean? = null
    private var overdueShoppingCarId: MutableList<Int> = arrayListOf()
    private var deleteAlertDialog: DeleteAlertDialog? = null
    private var deleteOverdueAlertDialog: DeleteAlertDialog? = null
    //初始化DecimalFormat
    private var priceDF: DecimalFormat? = null
    private var rlBottom: RelativeLayout? = null
    private var list: MutableList<ShoppingCarListBean.Companion.NormalItemsBean>? = null
    private var list2: MutableList<ShoppingCarListBean.Companion.NormalItemsBean>? = null
    private var totalAccount = 0.0
    private var checkNum = 0
    private var onRightClickListener: View.OnClickListener? = null
    private var isR = false
    private var isR2 = false

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        btnSettlement = view.findViewById(R.id.btn_settlement)
//        btnSettlement?.setOnClickListener {
//            val shoppingCar = ArrayList<Int?>()
//            for (itemsBean in shoppingCarListBean?.normalItems!!) {
//                    if (itemsBean.isChecked) {
//                        shoppingCar.add(itemsBean.shoppingCarId)
//                    }
//            }
//            if (shoppingCar.size == 0) {
//                ToastUtil.showShort(mPresenter?.mContext!!, "没有选择商品")
//            } else {
//                ConfirmOrderActivity.goTo(mPresenter?.mContext!!, shoppingCar)
//            }
//            ConfirmOrderActivity.goTo(mPresenter?.mContext!!, shoppingCar)
//        }
//        btnEmpty = view.findViewById(R.id.btn_empty)
//        btnEmpty?.setOnClickListener {
//            mRxManager.post(EventParams.EVENT_TYPE_TO_MAIN_FRAGMENT, null)
//        }
//    }

    override fun getLayoutResId(): Int {
        return R.layout.fragment_shopping_car
    }

    override fun initPresenter() {
        mPresenter?.setVM(this, mModel)
    }

    override fun initArgumentsData() {
        //初始化decimalformat
        priceDF = DecimalFormat(getString(R.string.decimalformate))
    }

    override fun initToolBar() {
        toolbarUtil = ToolbarUtil(layout!!,context)
        toolbarUtil?.setLightBackTheme(R.string.shoppingCar_title)
        toolbarUtil!!.setLineBackgroundColor(R.color.white_ffffff)
        toolbarUtil!!.setLeftVisibility(View.GONE)
        toolbarUtil!!.setRightImage(R.drawable.shopping_delete)
        onRightClickListener = View.OnClickListener {
            //删除选定产品
            var num = 0
            val items: MutableList<Int> = ArrayList()
            for (itemsBean in shoppingCarListBean?.normalItems!!) {
                if (itemsBean.isChecked) {
                    num++
                    items.add(itemsBean.shoppingCarId)
                }
            }
            if (num > 0) {
                val deleteListDialog =
                    DeleteAlertDialog(activity, getString(R.string.delete_sure_num, num))
                deleteListDialog.show(activity, null)
                deleteListDialog.setOnConfirmListener(object : DeleteAlertDialog.onConfirmListener {
                    override fun onConfirm() {
                        val map: MutableMap<String, List<Int>> =
                            HashMap()
                        map["ids"] = items
                        mPresenter!!.deleteShoppingCarGoods(
                            Const.header(),
                            ParamsUtil.getInstance()!!.getBodyIntegerList(map),
                            getString(R.string.delete_success)
                        )
                    }
                })
            } else {
                ToastUtil.showShort(activity!!, getString(R.string.shoppingcar_select_empty))
            }
        }
    }

    override fun initView() {
        EventBus.getDefault().register(this)
        deleteAlertDialog = DeleteAlertDialog(activity, getString(R.string.delete_sure))
        deleteAlertDialog?.setOnConfirmListener(object : DeleteAlertDialog.onConfirmListenerWithId {
           override fun onConfirm(id: String?) {
                mPresenter!!.deleteShoppingCarGood(Const.header(), id)
            }
        })
        deleteOverdueAlertDialog =
            DeleteAlertDialog(activity, getString(R.string.delete_all_sure))
        deleteOverdueAlertDialog?.setOnConfirmListener(object : DeleteAlertDialog.onConfirmListener {
            override fun onConfirm() {
                val map: MutableMap<String, List<Int>> =
                    HashMap()
                map["ids"] = overdueShoppingCarId
                mPresenter!!.deleteShoppingCarGoods(
                    Const.header(),
                    ParamsUtil.getInstance()!!.getBodyIntegerList(map),
                    getString(R.string.delete_all_success)
                )
            }
        })
        //设置contentview使焦点离开子控件
        layout!!.setOnTouchListener(OnTouchListener { v, event ->
            if (null != activity!!.currentFocus) {
                /**
                 * 点击空白位置 隐藏软键盘
                 */
                val mInputMethodManager =
                    activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                return@OnTouchListener mInputMethodManager.hideSoftInputFromWindow(
                    activity!!.currentFocus!!.windowToken,
                    0
                )
            }
            false
        })

        rlEmpty = layout!!.findViewById(R.id.rl_empty)
        mAdapter = ShoppingCarAdapter(this.context)
        mAdapter!!.setItemAnimation(AnimationType.SLIDE_FROM_BOTTOM)
        btnEmpty = layout!!.findViewById(R.id.btn_empty)
        btnEmpty?.setOnClickListener(this)
        rlBottom = layout!!.findViewById(R.id.rl_bottom) as RelativeLayout
        mShoppingCarList = layout!!.findViewById(R.id.shopping_car_list) as XRecyclerView
        mShoppingCarList!!.layoutManager = LinearLayoutManager(activity)
        mShoppingCarList!!.isLoadingMoreEnabled = false
        mShoppingCarList!!.isPullRefreshEnabled = true
        mShoppingCarList!!.adapter = mAdapter
        mShoppingCarList!!.setFootViewText("加载中", "")
        mShoppingCarList!!.setRefreshProgressStyle(ProgressStyle.Pacman)

        mShoppingCarList!!.setLoadingListener(object : LoadingListener {
            override fun onRefresh() {
                mPresenter?.getShoppingCarList(Const.header())
            }

            override fun onLoadMore() {}
        })

        btnSettlement = layout!!.findViewById(R.id.btn_settlement)
        btnSettlement?.setOnClickListener(this)
        mTvTotal = layout!!.findViewById(R.id.shopping_car_total)
        mCheckAll = layout!!.findViewById(R.id.shopping_car_check_all)
        mCheckAll?.setOnClickListener(this)
        mCheckAll?.setOnCheckedChangeListener(mCheckListener)
        isR2 = true
        mPresenter?.getShoppingCarList(Const.header())
    }

    override fun initData() {

    }

    override fun onInvisible() {
        super.onInvisible()
        LoadingDialog.cancelDialogForLoading()
    }

    override fun onStart() {
        super.onStart()
        mPresenter?.getShoppingCarList(Const.header())
    }

    override fun onVisible() {
        super.onVisible()
        isR = false
        mPresenter?.getShoppingCarList(Const.header())
        LoadingDialog.showDialogForLoading(
            activity!!,
            activity!!.getString(R.string.call_back_loading),
            false
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    override fun setShoppingCarList(bean: ShoppingCarListBean?) {
        LoadingDialog.cancelDialogForLoading()

        if (mShoppingCarList!!.isRefreshing) {
            mShoppingCarList!!.refreshComplete()
        }
        mShoppingCarList!!.isEnabled = false

        shoppingCarListBean = bean
        if (isR == false) {
            isR = true
            for (normalItemsBean in shoppingCarListBean?.normalItems!!) {
                normalItemsBean.isChecked = true
            }
        } else {
            if (list2 != null && list2!!.size > 0) {
                for (normalItemsBean in shoppingCarListBean?.normalItems!!) {
                    for (normalItemsBean2 in list2!!) {
                        if (normalItemsBean.id === normalItemsBean2.id) {
                            normalItemsBean.isChecked = normalItemsBean2.isChecked
                            list2!!.remove(normalItemsBean2)
                            break
                        }
                    }
                }
            }
        }
        refreshTotalAmount()
        list = ArrayList<ShoppingCarListBean.Companion.NormalItemsBean>()

        list?.addAll(shoppingCarListBean?.normalItems!!)
        //失效商品列表
        overdueShoppingCarId.clear()
        for (abnormalItemsBean in shoppingCarListBean?.abnormalItems!!) {
            overdueShoppingCarId.add(abnormalItemsBean.shoppingCarId)
        }

        if (overdueShoppingCarId?.size == 0) {
        } else {
            list?.addAll(shoppingCarListBean?.abnormalItems!!)
        }
        mAdapter?.setListAll(list as List<ShoppingCarListBean.Companion.NormalItemsBean?>?)
        //设置empty图标的显示
        if (shoppingCarListBean?.normalItems != null && shoppingCarListBean?.normalItems?.size!! > 0
            || shoppingCarListBean?.abnormalItems != null && shoppingCarListBean?.abnormalItems?.size!! > 0
        ) {
            rlEmpty!!.visibility = View.GONE
            rlBottom!!.visibility = View.VISIBLE
            mShoppingCarList!!.visibility = View.VISIBLE
        } else {
            rlEmpty!!.visibility = View.VISIBLE
            rlBottom!!.visibility = View.GONE
            mShoppingCarList!!.visibility = View.GONE
        }
        //设置底部红点
       EventBus.getDefault()
            .post(ShoppingCountEvent(shoppingCarListBean?.normalItems?.size!! + shoppingCarListBean?.abnormalItems?.size!!))
        //设置顶部删除按钮图标和事件
        if (shoppingCarListBean?.normalItems?.size === 0) {
            toolbarUtil!!.setRightImage(R.drawable.shooping_delete_gray)
            toolbarUtil!!.setOnRightClickListener(null)
        } else {
            toolbarUtil!!.setRightImage(R.drawable.shopping_delete)
            toolbarUtil!!.setOnRightClickListener(onRightClickListener)
        }
        mShoppingCarList!!.isEnabled = true
    }

    override fun deleteSuccess() {
        list2 = java.util.ArrayList<ShoppingCarListBean.Companion.NormalItemsBean>()
        list2?.addAll(list!!)
        mPresenter?.getShoppingCarList(Const.header())
    }

    override fun updateSuccess() {
        list2 = java.util.ArrayList<ShoppingCarListBean.Companion.NormalItemsBean>()
        list2?.addAll(list!!)
        mPresenter?.getShoppingCarList(Const.header())
    }

    override fun setShoppingCarCount(bean: ShoppingCarCountBean?) {
        EventBus.getDefault().post(ShoppingCountEvent(bean?.count!!))
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btn_settlement -> {
                val shoppingCar = java.util.ArrayList<Int?>()
                for (itemsBean in shoppingCarListBean?.normalItems!!) {
                    if (itemsBean.isChecked) {
                        shoppingCar.add(itemsBean.shoppingCarId)
                    }
                }
                if (shoppingCar.size == 0) {
                    ToastUtil.showShort(activity!!, "没有选择商品")
                } else {
                    ConfirmOrderActivity.goTo(activity!!, shoppingCar)
                }
            }
            R.id.btn_empty -> mRxManager.post(EventParams.EVENT_TYPE_TO_MAIN_FRAGMENT, null) //跳转首页
        }
    }

    /**
     * 全选按钮的监听事件
     */
    var mCheckListener =
        CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                for (normalItemsBean in shoppingCarListBean?.normalItems!!) {
                    normalItemsBean.isChecked = true
                }
            } else {
                for (normalItemsBean in shoppingCarListBean?.normalItems!!) {
                    normalItemsBean.isChecked = false
                }
            }
            refreshTotalAmount()
            mAdapter!!.notifyDataSetChanged()
        }

    fun refreshTotalAmount() {
        totalAccount = 0.0
        checkNum = 0
        val normalItems: List<ShoppingCarListBean.Companion.NormalItemsBean> =
            shoppingCarListBean?.normalItems!!
        for (item in normalItems) {
            if (item.isChecked) {
                totalAccount += item.price * item.quantity
                checkNum++
            }
        }
        if (list != null && list?.size!! > 0) {
            list2 = ArrayList<ShoppingCarListBean.Companion.NormalItemsBean>()
            list2?.addAll(list!!)
        }
        mTvTotal!!.text = getString(R.string.total_money, priceDF!!.format(totalAccount))
        btnSettlement!!.text = getString(R.string.settlement, checkNum.toString())
        if (checkNum == 0) {
            btnSettlement?.isEnabled = false
        } else {
            btnSettlement?.isEnabled = true
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onShoppingCarUpdate(event: ShoppingCarRefreshEvent) {
        if (event.isFresh) {
            mPresenter!!.getShoppingCarCount(Const.header())
        }
    }

    private inner class ShoppingCarAdapter internal constructor(context: Context?) :
        BaseQuickAdapter<ShoppingCarListBean.Companion.NormalItemsBean?>(
            context,
            R.layout.item_shopping_car_overdue_list
        ) {
        override fun HelperBindData(
            viewHolder: HelperRecyclerViewHolder,
            position: Int,
            data: ShoppingCarListBean.Companion.NormalItemsBean?
        ) {
            val mCheckBox = viewHolder.getView<CheckBox>(R.id.shopping_car_check)
            val mGoodsImg =
                viewHolder.getView<ImageView>(R.id.goods_img) //商品图
            ImageLoaderUtil.getInstance()?.load(
                mGoodsImg,
                data?.imageUrls!!.split("&&")[1]
            )
            val mGoodsName = viewHolder.getView<TextView>(R.id.tv_item_goods_name) //商品名称
            mGoodsName.setText(data?.name)
            val mGoodsPrice =
                viewHolder.getView<TextView>(R.id.tv_item_goods_price) //商品价格
            mGoodsPrice.setText(getString(R.string.common_amount, data?.price))
            val mGoodsUnit =
                viewHolder.getView<TextView>(R.id.tv_item_goods_price_unit) //商品单位
            mGoodsUnit.setText(
                getString(
                    R.string.shopping_car_item_goods_unit,
                    data?.specification
                )
            )
            val mItemOverdue = viewHolder.getView<TextView>(R.id.tv_item_overdue) //失效
            val mOverdueB = viewHolder.getView<TextView>(R.id.btn_clear_overdue) //失效
            mOverdueB.visibility = View.GONE
            mGoodsUnit.setTextColor(Color.LTGRAY)
            if (position == shoppingCarListBean?.normalItems?.size) {
                mOverdueB.visibility = View.VISIBLE
                mOverdueB.setOnClickListener { deleteOverdueAlertDialog?.show(getActivity(), "") }
            }
            if (position >= shoppingCarListBean?.normalItems?.size!!) {
                mCheckBox.visibility = View.INVISIBLE
                mItemOverdue.setText(getString(R.string.item_overdue))
                mItemOverdue.visibility = View.VISIBLE
                mGoodsName.setTextColor(Color.LTGRAY)
                mGoodsPrice.setTextColor(Color.LTGRAY)
                mItemOverdue.setTextColor(Color.LTGRAY)
                return
            }
            mGoodsName.setTextColor(Color.BLACK)
            mGoodsPrice.setTextColor(Color.BLACK)
            mCheckBox.visibility = View.VISIBLE
            mCheckBox.isChecked = data?.isChecked!!
            //单项按钮选定监听事件
            mCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
                //单项每次选定都判断是否已经全选
                if (isChecked) { //设置控件内部和bean中的选定状态
                    data.isChecked = true
                    //判断全选项是否需要选定
                    var isCheckAll = true
                    for (item in shoppingCarListBean?.normalItems!!) {
                        if (!item.isChecked) {
                            isCheckAll = false
                            break
                        }
                    }
                    mCheckAll?.setOnCheckedChangeListener(null)
                    mCheckAll?.setChecked(isCheckAll)
                    mCheckAll?.setOnCheckedChangeListener(mCheckListener)
                } else {
                    data.isChecked = false
                    mCheckAll?.setOnCheckedChangeListener(null)
                    mCheckAll?.setChecked(false)
                    mCheckAll?.setOnCheckedChangeListener(mCheckListener)
                }
                refreshTotalAmount()
            }
            viewHolder.getView<View>(R.id.goods_count_edit).visibility = View.VISIBLE
            val etShoppingCount = viewHolder.getView<EditText>(R.id.et_shopping_count)
            etShoppingCount.setText(getString(R.string.common_amount2, data.quantity))
            val mCbGoodsDetailActivityMinus =
                viewHolder.getView<View>(R.id.cb_goods_detail_activity_minus) as FrameLayout
            mCbGoodsDetailActivityMinus.setOnClickListener {
                var count1: Double = DoubleUtil.sub(
                    etShoppingCount.text.toString().toDouble(),
                    data.minimumIncrementQuantity
                )
                if (DoubleUtil.compare(
                        data.stock,
                        data.maximumOrderQuantity
                    ) === -1
                ) {
                    if (DoubleUtil.compare(count1, data.maximumOrderQuantity) === -1) {
                        count1 = data.maximumOrderQuantity
                        etShoppingCount.setText(getString(R.string.common_amount2, count1))
                        ToastUtil.showShort(
                            context!!,
                            "订购商品数量不能大于" + data.maximumOrderQuantity
                        )
                    }
                } else {
                    if (DoubleUtil.compare(count1, data.stock) === -1) {
                        count1 = data.stock
                        etShoppingCount.setText(getString(R.string.common_amount2, count1))
                        ToastUtil.showShort(context!!, "超过最大库存，请确认输入内容")
                    }
                }
                if (DoubleUtil.compare(count1, data.minimunOrderQuantity) === 1) {
                    count1 = data.minimunOrderQuantity
                    etShoppingCount.setText(getString(R.string.common_amount2, count1))
                    ToastUtil.showShort(
                        context!!,
                        "订购商品数量不能小于" + data.minimunOrderQuantity
                    )
                } else if (DoubleUtil.compare(
                        count1,
                        data.minimumIncrementQuantity
                    ) === 1
                ) {
                    count1 = data.minimumIncrementQuantity
                    etShoppingCount.setText(getString(R.string.common_amount2, count1))
                    ToastUtil.showShort(
                        context!!,
                        "订购商品数量不能小于" + data.minimumIncrementQuantity
                    )
                } else if (DoubleUtil.compare(count1, 0.0) === 0) {
                    count1 =
                        if (data.minimunOrderQuantity > data.minimumIncrementQuantity) {
                            data.minimunOrderQuantity
                        } else {
                            data.minimumIncrementQuantity
                        }
                    etShoppingCount.setText(getString(R.string.common_amount2, count1))
                    ToastUtil.showShort(context!!, "订购商品数量不能为0")
                }
                //更新数量
                val map: MutableMap<String, Number> =
                    HashMap()
                map["quantity"] = count1
                mPresenter?.updateShoppingCarCount(
                    Const.header(),
                    data.shoppingCarId,
                    ParamsUtil.getInstance()?.getBodyNumber(map)
                )
            }
            val mCbGoodsDetailActivityPlus =
                viewHolder.getView<View>(R.id.cb_goods_detail_activity_plus) as FrameLayout
            mCbGoodsDetailActivityPlus.setOnClickListener {
                var count1: Double = DoubleUtil.sum(
                    etShoppingCount.text.toString().toDouble(),
                    data.minimumIncrementQuantity
                )
                if (DoubleUtil.compare(
                        data.stock,
                        data.maximumOrderQuantity
                    ) === -1
                ) {
                    if (DoubleUtil.compare(count1, data.maximumOrderQuantity) === -1) {
                        count1 = data.maximumOrderQuantity
                        etShoppingCount.setText(getString(R.string.common_amount2, count1))
                        ToastUtil.showShort(
                            context!!,
                            "订购商品数量不能大于" + data.maximumOrderQuantity
                        )
                    }
                } else {
                    if (DoubleUtil.compare(count1, data.stock) === -1) {
                        count1 = data.stock
                        etShoppingCount.setText(getString(R.string.common_amount2, count1))
                        ToastUtil.showShort(context!!, "超过最大库存，请确认输入内容")
                    }
                }
                if (DoubleUtil.compare(count1, data.minimunOrderQuantity) === 1) {
                    count1 = data.minimunOrderQuantity
                    etShoppingCount.setText(getString(R.string.common_amount2, count1))
                    ToastUtil.showShort(
                        context!!,
                        "订购商品数量不能小于" + data.minimunOrderQuantity
                    )
                } else if (DoubleUtil.compare(
                        count1,
                        data.minimumIncrementQuantity
                    ) === 1
                ) {
                    count1 = data.minimumIncrementQuantity
                    etShoppingCount.setText(getString(R.string.common_amount2, count1))
                    ToastUtil.showShort(
                        context!!,
                        "订购商品数量不能小于" + data.minimumIncrementQuantity
                    )
                } else if (DoubleUtil.compare(count1, 0.0) === 0) {
                    count1 =
                        if (data.minimunOrderQuantity > data.minimumIncrementQuantity) {
                            data.minimunOrderQuantity
                        } else {
                            data.minimumIncrementQuantity
                        }
                    etShoppingCount.setText(getString(R.string.common_amount2, count1))
                    ToastUtil.showShort(context!!, "订购商品数量不能为0")
                }
                //更新数量
                val map: MutableMap<String, Number> =
                    HashMap()
                map["quantity"] = count1
                mPresenter!!.updateShoppingCarCount(
                    Const.header(),
                    data.shoppingCarId,
                    ParamsUtil.getInstance()?.getBodyNumber(map)
                )
            }
            viewHolder.itemView.setOnLongClickListener {
                deleteAlertDialog?.show(getActivity(), java.lang.String.valueOf(data.id))
                true
            }
            etShoppingCount.onFocusChangeListener =
                OnFocusChangeListener { v, hasFocus ->
                    //当焦点离开editText
                    if (!hasFocus) {
                        try {
                            etShoppingCount.text.toString().toDouble()
                        } catch (e: Exception) {
                            etShoppingCount.setText(
                                getString(
                                    R.string.common_amount2,
                                    data.minimumIncrementQuantity
                                )
                            )
                        }
                        val s2 = etShoppingCount.text.toString()
                        var newCount = s2.toDouble()
                        if (data.quantity === newCount) {
                            return@OnFocusChangeListener
                        }
                        if (data.stock !== newCount) {
                            val b: Double = data.minimumIncrementQuantity
                            val f = newCount * 100000 % (b * 100000)
                            if (f != 0.0) {
                                var t: Double =
                                    DoubleUtil.div(newCount, b, BigDecimal.ROUND_DOWN)
                                //                                t = (int)t*b;
                                //                                newCount = t;
                                t = DoubleUtil.mul(t, b)
                                newCount = t
                            }
                        }
                        if (DoubleUtil.compare(
                                data.stock,
                                data.maximumOrderQuantity
                            ) === -1
                        ) {
                            if (DoubleUtil.compare(
                                    newCount,
                                    data.maximumOrderQuantity
                                ) === -1
                            ) {
                                newCount = data.maximumOrderQuantity
                                ToastUtil.showShort(
                                    context!!,
                                    "订购商品数量不能大于" + data.maximumOrderQuantity
                                )
                            }
                        } else {
                            if (DoubleUtil.compare(newCount, data.stock) === -1) {
                                newCount = data.stock
                                ToastUtil.showShort(context!!, "超过最大库存，请确认输入内容")
                            }
                        }
                        if (DoubleUtil.compare(newCount, data.minimunOrderQuantity) === 1) {
                            newCount = data.minimunOrderQuantity
                            ToastUtil.showShort(
                                context!!,
                                "订购商品数量不能小于" + data.minimunOrderQuantity
                            )
                        } else if (DoubleUtil.compare(
                                newCount,
                                data.minimumIncrementQuantity
                            ) === 1
                        ) {
                            newCount = data.minimumIncrementQuantity
                            ToastUtil.showShort(
                                context!!,
                                "订购商品数量不能小于" + data.minimumIncrementQuantity
                            )
                        } else if (DoubleUtil.compare(newCount, 0.0) === 0) {
                            newCount =
                                if (data.minimunOrderQuantity > data.minimumIncrementQuantity) {
                                    data.minimunOrderQuantity
                                } else {
                                    data.minimumIncrementQuantity
                                }
                            ToastUtil.showShort(context!!, "订购商品数量不能为0")
                        }
                        if (etShoppingCount.text.toString() != s2) {
                            etShoppingCount.setText(getString(R.string.common_amount2, newCount))
                        }
                        //更新数量
                        val map: MutableMap<String, Number> =
                            HashMap()
                        map["quantity"] = newCount
                        mPresenter!!.updateShoppingCarCount(
                            Const.header(),
                            data.shoppingCarId,
                            ParamsUtil.getInstance()?.getBodyNumber(map)
                        )
                    }
                }
        }
    }
}