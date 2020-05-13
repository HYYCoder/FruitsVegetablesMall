package com.huangyiyang.fruitsvegetablesmall.ui.goods.activity

import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.View.OnTouchListener
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.gyf.immersionbar.ktx.immersionBar
import com.huangyiyang.fruitsvegetablesmall.R
import com.huangyiyang.fruitsvegetablesmall.Const
import com.huangyiyang.fruitsvegetablesmall.bean.GoodsDetailBean
import com.huangyiyang.fruitsvegetablesmall.bean.ShoppingCarCountBean
import com.huangyiyang.fruitsvegetablesmall.event.EventParams
import com.huangyiyang.fruitsvegetablesmall.event.ShoppingCountEvent
import com.huangyiyang.fruitsvegetablesmall.mvp.activity.BaseActivity
import com.huangyiyang.fruitsvegetablesmall.ui.goods.contract.GoodsDetailActivityContract
import com.huangyiyang.fruitsvegetablesmall.ui.goods.model.GoodsDetailActivityModel
import com.huangyiyang.fruitsvegetablesmall.ui.goods.presenter.GoodsDetailActivityPresenter
import com.huangyiyang.fruitsvegetablesmall.ui.main.activity.MainActivity
import com.huangyiyang.fruitsvegetablesmall.mvp.util.*
import com.huangyiyang.fruitsvegetablesmall.view.main.CommonLayout
import com.huangyiyang.fruitsvegetablesmall.view.main.LoadingDialog
import org.greenrobot.eventbus.EventBus
import java.math.BigDecimal
import java.util.*

class GoodsDetailActivity : GoodsDetailActivityContract.GoodsDetailActivityView, View.OnClickListener, BaseActivity<GoodsDetailActivityModel,
        GoodsDetailActivityPresenter>(){

    private var mBannerUtil //Banner控制类
            : BannerUtil? = null
    private var mShoppingCar: ImageView? = null
    private var back: ImageView? = null
    private var mSoldOut: ImageView? = null
    private var mTvGoodsName: TextView? = null
    private var mTvGoodsPrice: TextView? = null
    private var mTvGoodsOldPrice: TextView? = null
    private var mTvGoodsStock: TextView? = null
    private var mCb1: CheckBox? = null
    private var mCb2: CheckBox? = null
    private var mCbGoodsDetailActivityMinus: FrameLayout? = null
    private var mCbGoodsDetailActivityPlus: FrameLayout? = null
    private var mEtGoodsCount: EditText? = null
    private var mBtnAddShoppingCar: Button? = null
    private var goodsId: String? = null
    private var mCommonLayout: CommonLayout? = null
    private var mShoppingCarCount: TextView? = null
    private var mGoodsUnit: TextView? = null
    private var goodsDetailBean: GoodsDetailBean? = null
    private var mTvGoodsDetails : TextView? = null

    companion object {
        private val ID = "goods_id"
        fun goTo(context: Context, goodsId: String?) {
            val intent = Intent(context, GoodsDetailActivity::class.java)
            intent.putExtra(ID, goodsId)
            context.startActivity(intent)
        }
    }

    override fun getLayoutResId(): Int {
        return R.layout.activity_goods_detail
    }

    override fun initPresenter() {
        mPresenter?.setVM(this, mModel)
    }

    override fun initIntentData() {
        goodsId = intent.getStringExtra(ID)
    }

    override fun initToolBar() {

    }

    override fun initView() {

        immersionBar {
            statusBarColor(R.color.green_4CAF65)
            navigationBarColor(R.color.gray_999999)
            autoDarkModeEnable(true)
        }

        mCommonLayout = findViewById(R.id.common_content)
        mBannerUtil = BannerUtil(this)
        mShoppingCar = findViewById(R.id.shopping_car)
        mShoppingCar?.setOnClickListener(this)
        back = findViewById(R.id.back)
        back?.setOnClickListener(this)
        mSoldOut = findViewById(R.id.iv_sold_out)
        mTvGoodsName = findViewById(R.id.tv_goods_detail_name)
        mGoodsUnit = findViewById(R.id.tv_goods_price_unit)
        mTvGoodsDetails  = findViewById(R.id.tv_goods_details)
        mTvGoodsPrice = findViewById(R.id.tv_goods_price)
        mTvGoodsOldPrice = findViewById(R.id.tv_goods_old_price)
        mTvGoodsStock = findViewById(R.id.tv_goods_stock)
        mCb1 = findViewById<CheckBox>(R.id.cb_1)
        mCb2 = findViewById<CheckBox>(R.id.cb_2)
        mCbGoodsDetailActivityMinus =
            findViewById<FrameLayout>(R.id.cb_goods_detail_activity_minus)
        mCbGoodsDetailActivityMinus!!.setOnClickListener(this)
        mCbGoodsDetailActivityPlus = findViewById<FrameLayout>(R.id.cb_goods_detail_activity_plus)
        mCbGoodsDetailActivityPlus!!.setOnClickListener(this)
        mEtGoodsCount = findViewById(R.id.et_shopping_count)
        mBtnAddShoppingCar = findViewById(R.id.add_shopping_car)
        mBtnAddShoppingCar?.setOnClickListener(this)
        mShoppingCarCount = findViewById(R.id.shopping_car_count)
        mCommonLayout?.showLoading()

        //设置contentview使焦点离开子控件
        window.decorView.setOnTouchListener(OnTouchListener { v, event ->
            if (null != currentFocus) {
                /**
                 * 点击空白位置 隐藏软键盘
                 */
                val mInputMethodManager =
                    getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                return@OnTouchListener mInputMethodManager.hideSoftInputFromWindow(
                    currentFocus!!.windowToken,
                    0
                )
            }
            false
        })

        mEtGoodsCount?.onFocusChangeListener = OnFocusChangeListener { v, hasFocus ->
            //当焦点离开editText
            if (!hasFocus) {
                var n: Double = goodsDetailBean?.minimumIncrementQuantity!!
                if (goodsDetailBean?.minimumIncrementQuantity!! > goodsDetailBean?.minimunOrderQuantity!!) n =
                    goodsDetailBean?.minimumIncrementQuantity!!
                if (StringUtil.isEmpty(mEtGoodsCount?.text.toString())) mEtGoodsCount?.setText(
                    getString(R.string.common_amount2, n)
                )
                try {
                    mEtGoodsCount?.text.toString().toDouble()
                } catch (e: Exception) {
                    mEtGoodsCount?.setText(getString(R.string.common_amount2, n))
                }
                var newCount = mEtGoodsCount?.text.toString().toDouble()
                if (goodsDetailBean?.stock !== newCount) {
                    val b: Double = goodsDetailBean?.minimumIncrementQuantity!!
                    val f = newCount * 100000 % (b * 100000)
                    if (f != 0.0) {
                        var t: Double =
                            DoubleUtil.div(newCount, b, BigDecimal.ROUND_DOWN)
                        //                            t = ((int) t) * b;
                        //                            newCount = t;
                        t = DoubleUtil.mul(t, b)
                        newCount = t
                    }
                }
                mEtGoodsCount?.setText(getString(R.string.common_amount2, newCount))
                if (DoubleUtil.compare(
                        goodsDetailBean?.stock!!,
                        goodsDetailBean?.maximumOrderQuantity!!
                    ) === -1
                ) {
                    if (DoubleUtil.compare(
                            newCount,
                            goodsDetailBean?.maximumOrderQuantity!!
                        ) === -1
                    ) {
                        newCount = goodsDetailBean?.maximumOrderQuantity!!
                        mEtGoodsCount?.setText(getString(R.string.common_amount2, newCount))
                        ToastUtil.showShort(
                            this,
                            "订购商品数量不能大于" + goodsDetailBean?.maximumOrderQuantity
                        )
                    }
                } else {
                    if (DoubleUtil.compare(newCount, goodsDetailBean?.stock!!) === -1) {
                        newCount = goodsDetailBean?.stock!!
                        mEtGoodsCount?.setText(getString(R.string.common_amount2, newCount))
                        ToastUtil.showShort(this, "超过最大库存，请确认输入内容")
                    }
                }
                if (DoubleUtil.compare(newCount, n) === 1) {
                    newCount = n
                    mEtGoodsCount?.setText(getString(R.string.common_amount2, newCount))
                    ToastUtil.showShort(this, "订购商品数量不能小于$n")
                }
            }
        }

        mPresenter?.getGoodsDetail(Const.header(), goodsId)
        mPresenter?.getShoppingCarCount(Const.header())
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.add_shopping_car -> {
                val map: MutableMap<String, Number> =
                    HashMap()
                map["goodsId"] = goodsId!!.toInt()
                map["quantity"] = mEtGoodsCount!!.text.toString().toDouble()
                mPresenter!!.addShoppingCar(
                    Const.header(),
                    ParamsUtil.getInstance()?.getBodyNumber(map)
                )
                LoadingDialog.showDialogForLoading(
                    this,
                    mContext.getString(R.string.call_back_loading),
                    false
                )
            }
            R.id.shopping_car -> {
                mRxManager.post(EventParams.EVENT_TYPE_TO_SHOPPING_CAR_FRAGMENT, null) //跳转首页===>购物车
                MainActivity.goTo(this)
            }
            R.id.back -> finish()
            R.id.cb_goods_detail_activity_minus -> {
                if (goodsDetailBean?.stock === 0.0) {
                    return
                }
                var n: Double = goodsDetailBean?.minimumIncrementQuantity!!
                if (goodsDetailBean?.minimumIncrementQuantity!! > goodsDetailBean?.minimunOrderQuantity!!) n =
                    goodsDetailBean?.minimumIncrementQuantity!!
                if (StringUtil.isEmpty(mEtGoodsCount!!.text.toString())) mEtGoodsCount!!.setText(
                    getString(R.string.common_amount2, n)
                )
                try {
                    mEtGoodsCount!!.text.toString().toDouble()
                } catch (e: Exception) {
                    mEtGoodsCount!!.setText(getString(R.string.common_amount2, n))
                }
                var count2: Double = DoubleUtil.sub(
                    mEtGoodsCount!!.text.toString().toDouble(),
                    goodsDetailBean?.minimumIncrementQuantity!!
                )
                mEtGoodsCount!!.setText(getString(R.string.common_amount2, count2))
                if (DoubleUtil.compare(
                        goodsDetailBean?.stock!!,
                        goodsDetailBean?.maximumOrderQuantity!!
                    ) === -1
                ) {
                    if (DoubleUtil.compare(
                            count2,
                            goodsDetailBean?.maximumOrderQuantity!!
                        ) === -1
                    ) {
                        count2 = goodsDetailBean?.maximumOrderQuantity!!
                        mEtGoodsCount!!.setText(getString(R.string.common_amount2, count2))
                        ToastUtil.showShort(
                            this,
                            "订购商品数量不能大于" + goodsDetailBean?.maximumOrderQuantity
                        )
                    }
                } else {
                    if (DoubleUtil.compare(count2, goodsDetailBean?.stock!!) === -1) {
                        count2 = goodsDetailBean?.stock!!
                        mEtGoodsCount!!.setText(getString(R.string.common_amount2, count2))
                        ToastUtil.showShort(this, "超过最大库存，请确认输入内容")
                    }
                }
                if (DoubleUtil.compare(count2, n) === 1) {
                    count2 = n
                    mEtGoodsCount!!.setText(getString(R.string.common_amount2, count2))
                    ToastUtil.showShort(this, "订购商品数量不能小于$n")
                }
            }
            R.id.cb_goods_detail_activity_plus -> {
                if (goodsDetailBean?.stock === 0.0) {
                    return
                }
                var n2: Double = goodsDetailBean?.minimumIncrementQuantity!!
                if (goodsDetailBean?.minimumIncrementQuantity!! > goodsDetailBean?.minimunOrderQuantity!!) n2 =
                    goodsDetailBean?.minimumIncrementQuantity!!
                if (StringUtil.isEmpty(mEtGoodsCount!!.text.toString())) mEtGoodsCount!!.setText(
                    getString(R.string.common_amount2, n2)
                )
                try {
                    mEtGoodsCount!!.text.toString().toDouble()
                } catch (e: Exception) {
                    mEtGoodsCount!!.setText(getString(R.string.common_amount2, n2))
                }
                var count3: Double = DoubleUtil.sum(
                    mEtGoodsCount!!.text.toString().toDouble(),
                    goodsDetailBean?.minimumIncrementQuantity!!
                )
                mEtGoodsCount!!.setText(getString(R.string.common_amount2, count3))
                if (DoubleUtil.compare(count3, n2) === 1) {
                    count3 = n2
                    mEtGoodsCount!!.setText(getString(R.string.common_amount2, count3))
                    ToastUtil.showShort(
                        this,
                        "订购商品数量不能小于" + goodsDetailBean?.minimunOrderQuantity
                    )
                }
                if (DoubleUtil.compare(
                        goodsDetailBean?.stock!!,
                        goodsDetailBean?.maximumOrderQuantity!!
                    ) === -1
                ) {
                    if (DoubleUtil.compare(
                            count3,
                            goodsDetailBean?.maximumOrderQuantity!!
                        ) === -1
                    ) {
                        count3 = goodsDetailBean?.maximumOrderQuantity!!
                        mEtGoodsCount!!.setText(getString(R.string.common_amount2, count3))
                        ToastUtil.showShort(
                            this,
                            "订购商品数量不能大于" + goodsDetailBean?.maximumOrderQuantity
                        )
                    }
                } else {
                    if (DoubleUtil.compare(count3, goodsDetailBean?.stock!!) === -1) {
                        count3 = goodsDetailBean?.stock!!
                        mEtGoodsCount!!.setText(
                            getString(
                                R.string.common_amount2,
                                goodsDetailBean?.stock
                            )
                        )
                        ToastUtil.showShort(this, "超过最大库存，请确认输入内容")
                    }
                }
            }
        }
    }

    override fun setGoodsDetail(bean: GoodsDetailBean?) {
        goodsDetailBean = bean
        val list: MutableList<BannerUtil.Companion.DataBean> = ArrayList<BannerUtil.Companion.DataBean>()
        for (url in bean?.imageUrls?.split("&&")!!) {
            if(url != "") {
                list.add(BannerUtil.Companion.DataBean("", "", url, ""))
            }
        }
        mBannerUtil?.setBanner(list)
        if (bean.stock <= 0) {
            mSoldOut!!.visibility = View.VISIBLE
        }
        mTvGoodsName?.text = bean.name
        mGoodsUnit!!.text = "/" + bean.specification

        val reducePrice: Double = bean.reducedPrice
        if (reducePrice > 0.0) {
            mTvGoodsPrice!!.text = getString(R.string.common_amount, bean.price-bean.reducedPrice)
            mTvGoodsOldPrice!!.text = getString(R.string.common_amount, bean.price)
            mTvGoodsOldPrice!!.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG //中划线
        } else {
            mTvGoodsPrice!!.text = getString(R.string.common_amount, bean.price)
            mTvGoodsOldPrice!!.visibility = View.GONE
        }

        mTvGoodsDetails?.text = bean.detail

        mTvGoodsStock!!.text = getString(
            R.string.shopping_stock_text,
            java.lang.String.valueOf(bean.stock)
        )
        //判断库存显示购物车按钮
        if (bean.stock > 0) {
            mBtnAddShoppingCar!!.isEnabled = true
            mBtnAddShoppingCar?.setTextColor(ContextCompat.getColor(this, R.color.white_ffffff))
        } else {
            mBtnAddShoppingCar!!.isEnabled = false
            mBtnAddShoppingCar?.setTextColor(ContextCompat.getColor(this, R.color.gray_999999))
        }
        //mDiscountsAdapter.setListAll(bean.getApplicableCampaigns())

        //初始化mEtGoodsCount数量
        if (bean.minimunOrderQuantity >= bean.minimumIncrementQuantity) {
            mEtGoodsCount!!.setText(
                getString(
                    R.string.common_amount2,
                    bean.minimunOrderQuantity
                )
            )
        } else {
            mEtGoodsCount!!.setText(
                getString(
                    R.string.common_amount2,
                    bean.minimumIncrementQuantity
                )
            )
        }

        //=============控制checkbox是否可点击
        if (bean.stock === 0.0) {
            mEtGoodsCount!!.setText("0")
            mEtGoodsCount!!.isEnabled = false
        }
        mCommonLayout!!.showContent()

    }

    override fun addShoppingCar() {
        LoadingDialog.cancelDialogForLoading()
        mPresenter?.getShoppingCarCount(Const.header())
    }

    override fun setShoppingCarCount(bean: ShoppingCarCountBean?) {
        if (bean?.count === 0) {
            mShoppingCarCount!!.visibility = View.INVISIBLE
        } else {
            mShoppingCarCount!!.visibility = View.VISIBLE
        }
        mShoppingCarCount?.text = java.lang.String.valueOf(bean?.count)
        EventBus.getDefault().post(ShoppingCountEvent(bean?.count!!))
    }

}