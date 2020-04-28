package com.huangyiyang.fruitsvegetablesmall.mvp.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import com.huangyiyang.fruitsvegetablesmall.R
import com.huangyiyang.fruitsvegetablesmall.mvp.manage.AppForegroundStateManager
import com.huangyiyang.fruitsvegetablesmall.mvp.rxevent.RxManager
import com.huangyiyang.fruitsvegetablesmall.mvp.model.BaseModelInterface
import com.huangyiyang.fruitsvegetablesmall.mvp.presenter.BasePresenter
import com.huangyiyang.fruitsvegetablesmall.mvp.util.KeyboardUtil
import com.huangyiyang.fruitsvegetablesmall.mvp.util.ToastUtil
import com.huangyiyang.fruitsvegetablesmall.mvp.util.TypeUtil
import java.util.*

abstract class BaseActivity<M : BaseModelInterface,P : BasePresenter<*, *>>:AppCompatActivity() {

    lateinit var mContext:Context

    lateinit var mRxManager: RxManager

    var mModel:M? = null

    var mPresenter:P? = null

    companion object {
        private var sActivities = ArrayList<Any>()
    }

    private var mDestroyed = false

    private var mExitTimestamp: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sActivities.add(this)
        mRxManager = RxManager()
        // 设置布局
        setContentView(getLayoutResId())
        mContext = this
        // 实例化Model
        mModel = TypeUtil.getTypeObject(this,0)
        // 实例化Presenter
        mPresenter = TypeUtil.getTypeObject(this,1)

        if(mPresenter != null){
            mPresenter?.mContext = this
            mPresenter?.mManager = this.mRxManager
        }

        initIntentData()
        initPresenter()
        initView()
        initToolBar()

        Log.e("当前Activity为----------->" , javaClass.canonicalName)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT // 禁用横屏

    }

    abstract fun getLayoutResId(): Int

    protected abstract fun initPresenter()

    protected abstract fun initIntentData()

    protected abstract fun initToolBar()

    protected abstract fun initView()

    override fun onStart() {
        super.onStart()
        AppForegroundStateManager.getInstance()?.onActivityVisible(this)
    }

    override fun onStop() {
        super.onStop()
        AppForegroundStateManager.getInstance()?.onActivityNotVisible(this)
    }

    override fun onDestroy() {
        sActivities.remove(this)
        mDestroyed = true
        super.onDestroy()
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            KeyboardUtil.hiddenKeyboard(event, this)
        }
        return super.dispatchTouchEvent(event)
    }

    override fun isDestroyed(): Boolean {
        return mDestroyed || isFinishing
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (sActivities.size == 1) {
            if (System.currentTimeMillis() - mExitTimestamp > 3000) {
                mExitTimestamp = System.currentTimeMillis()
                ToastUtil.showShort(this, R.string.common_exit_app)
            } else {
                super.onBackPressed()
            }
        } else {
            super.onBackPressed()
        }
    }

    open fun startActivity(clazz: Class<out Activity?>?) {
        val intent = Intent(this, clazz)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}