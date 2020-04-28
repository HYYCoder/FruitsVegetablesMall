package com.huangyiyang.fruitsvegetablesmall.ui.cassification.fragment

import android.content.Context
import android.content.DialogInterface
import android.graphics.Paint
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.huangyiyang.fruitsvegetablesmall.R
import com.huangyiyang.fruitsvegetablesmall.Const
import com.huangyiyang.fruitsvegetablesmall.bean.GoodsDetailBean
import com.huangyiyang.fruitsvegetablesmall.event.ShoppingCarRefreshEvent
import com.huangyiyang.fruitsvegetablesmall.mvp.adapter.BaseQuickAdapter
import com.huangyiyang.fruitsvegetablesmall.mvp.fragment.BaseFragment
import com.huangyiyang.fruitsvegetablesmall.ui.cassification.contract.ClassificationDetailFragmentContract
import com.huangyiyang.fruitsvegetablesmall.ui.cassification.model.ClassificationDetailFragmentModel
import com.huangyiyang.fruitsvegetablesmall.ui.cassification.presenter.ClassificationDetailFragmentPresenter
import com.huangyiyang.fruitsvegetablesmall.ui.goods.activity.GoodsDetailActivity
import com.huangyiyang.fruitsvegetablesmall.mvp.util.*
import com.huangyiyang.fruitsvegetablesmall.view.main.CommonLayout
import com.huangyiyang.fruitsvegetablesmall.view.main.LoadingDialog
import com.zhouyou.recyclerview.XRecyclerView
import com.zhouyou.recyclerview.XRecyclerView.LoadingListener
import com.zhouyou.recyclerview.adapter.HelperRecyclerViewHolder
import org.greenrobot.eventbus.EventBus
import java.math.BigDecimal
import java.util.*

class CassificationDetailFragment : ClassificationDetailFragmentContract.ClassificationDetailFragmentView
    , View.OnClickListener,BaseFragment<ClassificationDetailFragmentModel
            , ClassificationDetailFragmentPresenter>(){

    private var mXRecyclerViewOne: XRecyclerView? = null
    private var classificationDetaiListAdapter: ClassificationDetaiListAdapter? = null
    private var common_layout: CommonLayout? = null
    private var categoryId = 0

    companion object{

        private var ID = "id"
        private var imagUrl: String? = null
        fun newInstance(id: Int?): CassificationDetailFragment? {
            val args = Bundle()
            args.putInt(ID, id!!)
            val fragment = CassificationDetailFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun getLayoutResId(): Int {
        return R.layout.fragment_cassification_detail
    }

    override fun initPresenter() {
        mPresenter?.setVM(this, mModel)
    }

    override fun initArgumentsData() {
        categoryId = arguments!!.getInt(ID)
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

        val goodsDetailParames: MutableMap<String, String> =
            HashMap()
        goodsDetailParames["categoryId"] = java.lang.String.valueOf(categoryId)
        goodsDetailParames["name"] = ""
        goodsDetailParames["price"] = ""
        goodsDetailParames["stock"] = ""
        goodsDetailParames["reducedPrice"] = ""
        goodsDetailParames["hotGoods"] = ""
        goodsDetailParames["pageSize"] = java.lang.String.valueOf(mPresenter?.mPageSize)
        mPresenter!!.initLoadParams(Const.header(), goodsDetailParames)
        mPresenter!!.initLoadView(common_layout, mXRecyclerViewOne, classificationDetaiListAdapter)
    }

    override fun initData() {

    }

    override fun onClick(v: View?) {

    }

    override fun onInvisible() {
        super.onInvisible()
        LoadingDialog.cancelDialogForLoading()
    }

    override fun setCategoriesDetailList(categoryListBean: List<GoodsDetailBean>?) {

    }

    override fun addShoppingCar() {
        LoadingDialog.cancelDialogForLoading()
        EventBus.getDefault().post(ShoppingCarRefreshEvent(true))
    }

    override fun setGoodsDetail(data: GoodsDetailBean?) {
        LoadingDialog.cancelDialogForLoading()
        EventBus.getDefault().post(ShoppingCarRefreshEvent(true))
        val dialog =
            BottomSheetDialog(this.context!!)
        val view: View = layoutInflater.inflate(R.layout.dialog_shopping, null)
        val img: ImageView = view.findViewById(R.id.classification_img)
        val tvGoodsName = view.findViewById<TextView>(R.id.tv_item_goods_name)
        val tvGoodsPrice = view.findViewById<TextView>(R.id.tv_item_goods_price)
        val tvGoodsUnit = view.findViewById<TextView>(R.id.tv_item_goods_price_unit)
        val tvGoodsOldPrice = view.findViewById<TextView>(R.id.tv_item_goods_old_price)
        val tvInventory =
            view.findViewById<TextView>(R.id.tv_join_shopping_car_inventory_sum)
        val etShoppingCount = view.findViewById<EditText>(R.id.et_shopping_count)
        val btnJoinShoppingCar =
            view.findViewById<Button>(R.id.btn_join_shopping_car_join_ok)
        val btnCancelDialog =
            view.findViewById<Button>(R.id.btn_join_shopping_car_cancel_dialog)

        ImageLoaderUtil.getInstance()?.load(img, imagUrl)
        tvGoodsName.text = data?.name

        val reducePrice: Double = data?.reducedPrice!!
        if (reducePrice > 0.0) {
            tvGoodsPrice.text = getString(R.string.common_amount, data.reducedPrice)
            tvGoodsOldPrice.text = getString(R.string.common_amount, data.price)
            tvGoodsOldPrice.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG //中划线
            tvGoodsOldPrice.visibility = View.VISIBLE
        } else {
            tvGoodsPrice.text = getString(R.string.common_amount, data.price)
            tvGoodsOldPrice.visibility = View.INVISIBLE
        }
        tvGoodsUnit.text = "/" + data.specification
        tvInventory.text = getString(
            R.string.shopping_stock_text,
            data.stock.toString() + data.specification
        )

        dialog.setContentView(view)
        dialog.show()

        dialog.setOnDismissListener(DialogInterface.OnDismissListener {
            Handler().postDelayed({
                val imm =
                    activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(layout!!.windowToken, 0)
            }, 100)
        })

        val mCbGoodsDetailActivityMinus =
            view.findViewById<View>(R.id.cb_goods_detail_activity_minus) as FrameLayout
        mCbGoodsDetailActivityMinus.setOnClickListener {
            var count1: Double = DoubleUtil.sub(
                etShoppingCount.text.toString().toDouble(),
                data.minimumIncrementQuantity
            )
            if (DoubleUtil.compare(data.stock, data.maximumOrderQuantity) === -1) {
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
            } else if (DoubleUtil.compare(count1, data.minimumIncrementQuantity) === 1) {
                count1 = data.minimumIncrementQuantity
                etShoppingCount.setText(getString(R.string.common_amount2, count1))
                ToastUtil.showShort(
                    context!!,
                    "订购商品数量不能小于" + data.minimumIncrementQuantity
                )
            } else if (DoubleUtil.compare(count1, 0.0) === 0) {
                count1 = if (data.minimunOrderQuantity > data.minimumIncrementQuantity) {
                    data.minimunOrderQuantity
                } else {
                    data.minimumIncrementQuantity
                }
                etShoppingCount.setText(getString(R.string.common_amount2, count1))
                ToastUtil.showShort(context!!, "订购商品数量不能为0")
            }
            etShoppingCount.setText(getString(R.string.common_amount2, count1))
        }
        val mCbGoodsDetailActivityPlus =
            view.findViewById<View>(R.id.cb_goods_detail_activity_plus) as FrameLayout
        mCbGoodsDetailActivityPlus.setOnClickListener {
            var count1: Double = DoubleUtil.sum(
                etShoppingCount.text.toString().toDouble(),
                data.minimumIncrementQuantity
            )
            if (DoubleUtil.compare(data.stock, data.maximumOrderQuantity) === -1) {
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
            } else if (DoubleUtil.compare(count1, data.minimumIncrementQuantity) === 1) {
                count1 = data.minimumIncrementQuantity
                etShoppingCount.setText(getString(R.string.common_amount2, count1))
                ToastUtil.showShort(
                    context!!,
                    "订购商品数量不能小于" + data.minimumIncrementQuantity
                )
            } else if (DoubleUtil.compare(count1, 0.0) === 0) {
                count1 = if (data.minimunOrderQuantity > data.minimumIncrementQuantity) {
                    data.minimunOrderQuantity
                } else {
                    data.minimumIncrementQuantity
                }
                etShoppingCount.setText(getString(R.string.common_amount2, count1))
                ToastUtil.showShort(context!!, "订购商品数量不能为0")
            }
            etShoppingCount.setText(getString(R.string.common_amount2, count1))
        }

        etShoppingCount.onFocusChangeListener =
            View.OnFocusChangeListener { v, hasFocus ->
                //当焦点离开editText
                if (!hasFocus) {
                    var n: Double = data.minimumIncrementQuantity
                    if (data.minimumIncrementQuantity > data.minimunOrderQuantity) n =
                        data.minimumIncrementQuantity
                    if (StringUtil.isEmpty(etShoppingCount.text.toString())) etShoppingCount.setText(
                        getString(R.string.common_amount2, n)
                    )
                    try {
                        etShoppingCount.text.toString().toDouble()
                    } catch (e: Exception) {
                        etShoppingCount.setText(getString(R.string.common_amount2, n))
                    }
                    var newCount = etShoppingCount.text.toString().toDouble()
                    if (data.stock !== newCount) {
                        val b: Double = data.minimumIncrementQuantity
                        val f = newCount * 100000 % (b * 100000)
                        if (f != 0.0) {
                            var t: Double =
                                DoubleUtil.div(newCount, b, BigDecimal.ROUND_DOWN)
                            t = DoubleUtil.mul(t, b)
                            newCount = t
                        }
                    }
                    etShoppingCount.setText(getString(R.string.common_amount2, newCount))
                    if (DoubleUtil.compare(
                            data.stock,
                            data.maximumOrderQuantity
                        ) === -1
                    ) {
                        if (DoubleUtil.compare(newCount, data.maximumOrderQuantity) === -1) {
                            newCount = data.maximumOrderQuantity
                            etShoppingCount.setText(getString(R.string.common_amount2, newCount))
                            ToastUtil.showShort(
                                context!!,
                                "订购商品数量不能大于" + data.maximumOrderQuantity
                            )
                        }
                    } else {
                        if (DoubleUtil.compare(newCount, data.stock) === -1) {
                            newCount = data.stock
                            etShoppingCount.setText(getString(R.string.common_amount2, newCount))
                            ToastUtil.showShort(context!!, "超过最大库存，请确认输入内容")
                        }
                    }
                    if (DoubleUtil.compare(newCount, n) === 1) {
                        newCount = n
                        etShoppingCount.setText(getString(R.string.common_amount2, newCount))
                        ToastUtil.showShort(context!!, "订购商品数量不能小于$n")
                    }
                }
            }

        //根据库存判断按钮是否可用
        if (data.stock > 0.0) { //初始化etShoppingCount数量
            if (data.minimunOrderQuantity >= data.minimumIncrementQuantity) {
                etShoppingCount.setText(
                    getString(
                        R.string.common_amount2,
                        data.minimunOrderQuantity
                    )
                )
            } else {
                etShoppingCount.setText(
                    getString(
                        R.string.common_amount2,
                        data.minimumIncrementQuantity
                    )
                )
            }
            btnJoinShoppingCar.isEnabled = true
            btnJoinShoppingCar.setTextColor(
                ContextCompat.getColor(
                    dialog.getContext(),
                    R.color.white_ffffff
                )
            )
        } else {
            btnJoinShoppingCar.isEnabled = false
            btnJoinShoppingCar.setTextColor(
                ContextCompat.getColor(
                    dialog.getContext(),
                    R.color.gray_999999
                )
            )
            etShoppingCount.setText("0")
            mCbGoodsDetailActivityMinus.isClickable = false
            mCbGoodsDetailActivityPlus.isClickable = false
            etShoppingCount.isFocusable = false
        }

        //=============点击确定按钮加入购物车
        btnJoinShoppingCar.setOnClickListener(View.OnClickListener {
            var n: Double = data.minimumIncrementQuantity
            if (data.minimumIncrementQuantity > data.minimunOrderQuantity) n =
                data.minimumIncrementQuantity
            if (StringUtil.isEmpty(etShoppingCount.text.toString())) etShoppingCount.setText(
                getString(R.string.common_amount2, n)
            )
            try {
                etShoppingCount.text.toString().toDouble()
            } catch (e: Exception) {
                etShoppingCount.setText(getString(R.string.common_amount2, n))
            }
            var newCount = etShoppingCount.text.toString().toDouble()
            if (data.stock !== newCount) {
                val b: Double = data.minimumIncrementQuantity
                val f = newCount * 100000 % (b * 100000)
                if (f != 0.0) {
                    var t: Double =
                        DoubleUtil.div(newCount, b, BigDecimal.ROUND_DOWN)
                    t = DoubleUtil.mul(t, b)
                    newCount = t
                }
            }
            etShoppingCount.setText(getString(R.string.common_amount2, newCount))
            if (DoubleUtil.compare(data.stock, data.maximumOrderQuantity) === -1) {
                if (DoubleUtil.compare(newCount, data.maximumOrderQuantity) === -1) {
                    newCount = data.maximumOrderQuantity
                    etShoppingCount.setText(getString(R.string.common_amount2, newCount))
                    ToastUtil.showShort(
                        context!!,
                        "订购商品数量不能大于" + data.maximumOrderQuantity
                    )
                    return@OnClickListener
                }
            } else {
                if (DoubleUtil.compare(newCount, data.stock) === -1) {
                    newCount = data.stock
                    etShoppingCount.setText(getString(R.string.common_amount2, newCount))
                    ToastUtil.showShort(context!!, "超过最大库存，请确认输入内容")
                    return@OnClickListener
                }
            }
            if (DoubleUtil.compare(newCount, n) === 1) {
                newCount = n
                etShoppingCount.setText(getString(R.string.common_amount2, newCount))
                ToastUtil.showShort(context!!, "订购商品数量不能小于$n")
                return@OnClickListener
            }
            val map: MutableMap<String, Number> =
                HashMap()
            map["goodsId"] = data.id
            map["quantity"] = etShoppingCount.text.toString().toDouble()
            mPresenter!!.addShoppingCar(
                Const.header(),
                ParamsUtil.getInstance()?.getBodyNumber(map)
            )
            LoadingDialog.showDialogForLoading(
                activity!!,
                activity!!.getString(R.string.call_back_loading),
                false
            )
            dialog.cancel()
        })

        btnCancelDialog.setOnClickListener { dialog.cancel() }
    }

    inner class ClassificationDetaiListAdapter : BaseQuickAdapter<GoodsDetailBean>{

        var context: Context? = null

        constructor(context: Context) : super(context, R.layout.item_classification_three_list){
            this.context = context
        }

        override fun HelperBindData(
            viewHolder: HelperRecyclerViewHolder?,
            position: Int,
            data: GoodsDetailBean?
        ) {
            val img =
                viewHolder?.getView<ImageView>(R.id.classification_img)

            val addShoppingCar =
                viewHolder?.getView<ImageView>(R.id.add_shopping_car)

            val tvItemGoodsName =
                viewHolder?.getView<TextView>(R.id.tv_item_goods_name)

            val tvItemGoodsPrice =
                viewHolder?.getView<TextView>(R.id.tv_item_goods_price)

            val tvItemGoodsOldPrice =
                viewHolder?.getView<TextView>(R.id.tv_item_goods_old_price)

            val tvItemGoodsUnit =
                viewHolder?.getView<TextView>(R.id.tv_item_goods_price_unit)


            val reducePrice: Double = data?.reducedPrice!!
            if (reducePrice > 0.0) {
                tvItemGoodsPrice?.text = context?.getString(
                    R.string.common_amount,
                    data.price-data.reducedPrice
                )
                tvItemGoodsOldPrice?.text = context?.getString(R.string.common_amount, data.price)
                tvItemGoodsOldPrice?.paint?.flags = Paint.STRIKE_THRU_TEXT_FLAG //中划线
                tvItemGoodsOldPrice?.visibility = View.VISIBLE
            } else {
                tvItemGoodsPrice?.text = context?.getString(R.string.common_amount, data.price)
                tvItemGoodsOldPrice?.visibility = View.INVISIBLE
            }

            ImageLoaderUtil.getInstance()
                ?.load(img!!, data.imageUrls.split("&&")[1])

            tvItemGoodsName?.text = data.name
            tvItemGoodsUnit?.text = "/" + data.specification

            //加入购物车
            addShoppingCar?.setOnClickListener {
                imagUrl = data.imageUrls.split("&&")?.get(1)
                LoadingDialog.cancelDialogForLoading()
                LoadingDialog.showDialogForLoading(
                    context!!,
                    context?.getString(R.string.call_back_loading),
                    false
                )
                mPresenter?.getGoodsDetail(
                    Const.header(),
                    java.lang.String.valueOf(data.id)
                )
            }

            viewHolder?.itemView?.setOnClickListener {
                GoodsDetailActivity.goTo(
                    context!!,
                    java.lang.String.valueOf(data.id)
                )
            }
        }
    }
}