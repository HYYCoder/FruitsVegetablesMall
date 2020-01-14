package com.huangyiyang.fruitsvegetablesmall.mvp.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.huangyiyang.fruitsvegetablesmall.mvp.model.BaseModelInterface
import com.huangyiyang.fruitsvegetablesmall.mvp.presenter.BasePresenter
import com.huangyiyang.fruitsvegetablesmall.mvp.view.BaseViewInterface
import com.huangyiyang.fruitsvegetablesmall.rxevent.RxManager
import com.huangyiyang.fruitsvegetablesmall.util.TypeUtil

abstract class BaseFragment<M : BaseModelInterface,V : BaseViewInterface,P : BasePresenter<M, V>> : Fragment(){

    protected var layout: View? = null

    lateinit var mRxManager: RxManager

    var mModel:M? = null

    var mPresenter:P? = null

    private var isVisible : Boolean? = null  //是否可见状态
    private var isPrepared = true  //标志位，View已经初始化完成。
    private var isFirstLoad = true //是否第一次加载

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (layout == null) {
            layout = inflater.inflate(getLayoutResId(), container, false)
        }
        mRxManager = RxManager()
        isFirstLoad = true
        isPrepared = true
        mModel = TypeUtil.getTypeObject(this, 0)
        mPresenter = TypeUtil.getTypeObject(this, 1)
        // 实例化Presenter中的Context
        if (mPresenter != null) {
            mPresenter?.mContext = activity!!
            mPresenter?.mManager = mRxManager
        }
        initPresenter()
        initArgumentsData()
        initToolBar()
        initView()
        lazyLoad()

        Log.e("当前Activity为----------->" , javaClass.canonicalName)

        return layout
    }

    /**
     * 如果是与ViewPager一起使用，调用的是setUserVisibleHint
     */
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (userVisibleHint) {
            isVisible = true
            onVisible()
        } else {
            isVisible = false
            onInvisible()
        }
    }

    /**
     * 如果是通过FragmentTransaction的show和hide的方法来控制显示，调用的是onHiddenChanged.
     * 若是初始就show的Fragment 为了触发该事件 需要先hide再show
     */
    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            isVisible = true
            onVisible()
        } else {
            isVisible = false
            onInvisible()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mRxManager.clear()
    }

    protected open fun onVisible() {
        lazyLoad()
    }

    protected open fun onInvisible() {}

    protected open fun lazyLoad() {
        if (!isPrepared!! || !isVisible!! || !isFirstLoad) {
            return
        }
        isFirstLoad = false
        initData()
    }

    open fun setDataInitiated(init: Boolean) {
        isFirstLoad = init
    }

    abstract fun getLayoutResId(): Int

    protected abstract fun initPresenter()

    protected abstract fun initArgumentsData()

    protected abstract fun initToolBar()

    protected abstract fun initView()

    protected abstract fun initData()

    open fun startActivity(clazz: Class<out Activity?>?) {
        val intent = Intent(activity, clazz)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

}