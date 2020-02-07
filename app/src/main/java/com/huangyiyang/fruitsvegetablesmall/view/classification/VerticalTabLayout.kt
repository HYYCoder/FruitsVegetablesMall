package com.huangyiyang.fruitsvegetablesmall.view.classification

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.animation.ValueAnimator.AnimatorUpdateListener
import android.content.Context
import android.database.DataSetObserver
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.Gravity
import android.view.View.OnClickListener
import android.widget.LinearLayout
import android.widget.ScrollView
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.huangyiyang.fruitsvegetablesmall.R
import java.util.*

open class VerticalTabLayout : ScrollView {
    private var mContext: Context? = null
    private var mTabStrip: TabStrip? = null
    private var mColorIndicator = 0
    private var mSelectedTab: TabView? = null
    private var mTabMargin = 0
    private var mIndicatorWidth = 0
    private var mIndicatorGravity = 0
    private var mIndicatorCorners = 0f
    private var mTabMode = 0
    private var mTabHeight = 0

    var TAB_MODE_FIXED = 10
    var TAB_MODE_SCROLLABLE = 11

    private var mViewPager: ViewPager? = null
    private var mPagerAdapter: PagerAdapter? = null
    private var mTabAdapter: TabAdapter? = null

    private var mTabSelectedListeners: MutableList<OnTabSelectedListener>? = null
    private var mTabPageChangeListener: OnTabPageChangeListener? = null
    private var mPagerAdapterObserver: DataSetObserver? = null

    private var mTabFragmentManager: TabFragmentManager? = null

    constructor(context: Context) : this(context,null)

    constructor(
        context: Context,
        attrs: AttributeSet?
    ):this(context,attrs,0)

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ):super(context,attrs,defStyleAttr) {
        mContext = context
        mTabSelectedListeners = ArrayList()
        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.VerticalTabLayout)
        mColorIndicator = typedArray.getColor(
            R.styleable.VerticalTabLayout_indicator_color,
            context.resources.getColor(R.color.colorAccent)
        )
        mIndicatorWidth = typedArray.getDimension(
            R.styleable.VerticalTabLayout_indicator_width,
            DisplayUtil().dp2px(context, 3f).toFloat()
        ).toInt()
        mIndicatorCorners =
            typedArray.getDimension(R.styleable.VerticalTabLayout_indicator_corners, 0f)
        mIndicatorGravity =
            typedArray.getInteger(R.styleable.VerticalTabLayout_indicator_gravity, Gravity.LEFT)
        if (mIndicatorGravity == 3) {
            mIndicatorGravity = Gravity.LEFT
        } else if (mIndicatorGravity == 5) {
            mIndicatorGravity = Gravity.RIGHT
        } else if (mIndicatorGravity == 119) {
            mIndicatorGravity = Gravity.FILL
        }
        mTabMargin = typedArray.getDimension(R.styleable.VerticalTabLayout_tab_margin, 0f).toInt()
        mTabMode = typedArray.getInteger(R.styleable.VerticalTabLayout_tab_mode, TAB_MODE_FIXED)
        val defaultTabHeight = LinearLayout.LayoutParams.WRAP_CONTENT
        mTabHeight = typedArray.getDimension(
            R.styleable.VerticalTabLayout_tab_height,
            defaultTabHeight.toFloat()
        ).toInt()
        typedArray.recycle()
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        if (childCount > 0) removeAllViews()
        initTabStrip()
    }

    private fun initTabStrip() {
        mTabStrip =
            TabStrip(
                mContext
            )
        addView(
            mTabStrip,
            LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT
            )
        )
    }

    fun removeAllTabs() {
        mTabStrip?.removeAllViews()
        mSelectedTab = null
    }

    fun getTabAt(position: Int): TabView {
        return mTabStrip?.getChildAt(position) as TabView
    }

    fun getTabCount(): Int {
        return mTabStrip?.childCount!!
    }

    fun getSelectedTabPosition(): Int {
        val index : Int = mTabStrip!!.indexOfChild(mSelectedTab)
        return if (index == -1) 0 else index
    }

    private fun addTabWithMode(tabView: TabView) {
        var params = LinearLayout.LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.WRAP_CONTENT
        )
        initTabWithMode(params)
        mTabStrip?.addView(tabView, params)
        if (mTabStrip?.indexOfChild(tabView) == 0) {
            tabView.isChecked = true
            params = tabView.layoutParams as LinearLayout.LayoutParams
            params.setMargins(0, 0, 0, 0)
            tabView.layoutParams = params
            mSelectedTab = tabView
            mTabStrip?.post {
                mTabStrip?.moveIndicator(0f) }
        }
    }

    private fun initTabWithMode(params: LinearLayout.LayoutParams) {
        if (mTabMode == TAB_MODE_FIXED) {
            params.height = 0
            params.weight = 1.0f
            params.setMargins(0, 0, 0, 0)
            isFillViewport = true
        } else if (mTabMode == TAB_MODE_SCROLLABLE) {
            params.height = mTabHeight
            params.weight = 0f
            params.setMargins(0, mTabMargin, 0, 0)
            isFillViewport = false
        }
    }

    private fun scrollToTab(position: Int) {
        val tabView: TabView = getTabAt(position)
        val y = scrollY
        val tabTop: Int = tabView.top + tabView.height / 2 - y
        val target = height / 2
        if (tabTop > target) {
            smoothScrollBy(0, tabTop - target)
        } else if (tabTop < target) {
            smoothScrollBy(0, tabTop - target)
        }
    }

    private var mLastPositionOffset = 0f

    private fun scrollByTab(position: Int, positionOffset: Float) {
        val tabView: TabView = getTabAt(position)
        val y = scrollY
        val tabTop: Int = tabView.top + tabView.height / 2 - y
        val target = height / 2
        val nextScrollY: Int = tabView.height + mTabMargin
        if (positionOffset > 0) {
            val percent = positionOffset - mLastPositionOffset
            if (tabTop > target) {
                smoothScrollBy(0, (nextScrollY * percent).toInt())
            }
        }
        mLastPositionOffset = positionOffset
    }

    fun addTab(tabView: TabView?) {
        if (tabView != null) {
            addTabWithMode(tabView)
            tabView.setOnClickListener(OnClickListener { view ->
                val position = mTabStrip?.indexOfChild(view)
                setTabSelected(position!!)
            })
        } else {
            throw IllegalStateException("tabview can't be null")
        }
    }

    fun setTabSelected(position: Int) {
        setTabSelected(position, updataIndicator = true, callListener = true)
    }

    private fun setTabSelected(
        position: Int,
        updataIndicator: Boolean,
        callListener: Boolean
    ) {
        post { setTabSelectedImpl(position, updataIndicator, callListener) }
    }

    private fun setTabSelectedImpl(
        position: Int,
        updataIndicator: Boolean,
        callListener: Boolean
    ) {
        val view: TabView = getTabAt(position)
        var selected: Boolean
        if ((view !== mSelectedTab).also { selected = it }) {
            if (mSelectedTab != null) {
                mSelectedTab?.isChecked = false
            }
            view.isChecked = true
            if (updataIndicator) {
                mTabStrip?.moveIndicatorWithAnimator(position)
            }
            mSelectedTab = view
            scrollToTab(position)
        }
        if (callListener) {
            for (i in mTabSelectedListeners?.indices!!) {
                val listener = mTabSelectedListeners!![i]
                if (listener != null) {
                    if (selected) {
                        listener.onTabSelected(view, position)
                    } else {
                        listener.onTabReselected(view, position)
                    }
                }
            }
        }
    }

    fun setTabBadge(tabPosition: Int, badgeNum: Int) {
        getTabAt(tabPosition).getBadgeView()?.badgeNumber = badgeNum
    }

    fun setTabBadge(tabPosition: Int, badgeText: String?) {
        getTabAt(tabPosition).getBadgeView()?.badgeText = badgeText
    }

    fun setTabMode(mode: Int) {
        check(!(mode != TAB_MODE_FIXED && mode != TAB_MODE_SCROLLABLE)) { "only support TAB_MODE_FIXED or TAB_MODE_SCROLLABLE" }
        if (mode == mTabMode) return
        mTabMode = mode
        for (i in 0 until mTabStrip?.childCount!!) {
            val view = mTabStrip?.getChildAt(i)
            val params =
                view?.layoutParams as LinearLayout.LayoutParams
            initTabWithMode(params)
            if (i == 0) {
                params.setMargins(0, 0, 0, 0)
            }
            view.layoutParams = params
        }
        mTabStrip?.invalidate()
        mTabStrip?.post { mTabStrip?.updataIndicator() }
    }

    /**
     * only in TAB_MODE_SCROLLABLE mode will be supported
     *
     * @param margin margin
     */
    fun setTabMargin(margin: Int) {
        if (margin == mTabMargin) return
        mTabMargin = margin
        if (mTabMode == TAB_MODE_FIXED) return
        for (i in 0 until mTabStrip?.childCount!!) {
            val view = mTabStrip?.getChildAt(i)
            val params =
                view?.layoutParams as LinearLayout.LayoutParams
            params.setMargins(0, if (i == 0) 0 else mTabMargin, 0, 0)
            view.layoutParams = params
        }
        mTabStrip?.invalidate()
        mTabStrip?.post {
                    mTabStrip?.updataIndicator()
            }
    }

    /**
     * only in TAB_MODE_SCROLLABLE mode will be supported
     *
     * @param height height
     */
    fun setTabHeight(height: Int) {
        if (height == mTabHeight) return
        mTabHeight = height
        if (mTabMode == TAB_MODE_FIXED) return
        for (i in 0 until mTabStrip?.childCount!!) {
            val view = mTabStrip?.getChildAt(i)
            val params =
                view?.layoutParams as LinearLayout.LayoutParams
            params.height = mTabHeight
            view.layoutParams = params
        }
        mTabStrip?.invalidate()
        mTabStrip?.post { mTabStrip?.updataIndicator() }
    }

    fun setIndicatorColor(color: Int) {
        mColorIndicator = color
        mTabStrip?.invalidate()
    }

    fun setIndicatorWidth(width: Int) {
        mIndicatorWidth = width
        mTabStrip?.setIndicatorGravity()
    }

    fun setIndicatorCorners(corners: Int) {
        mIndicatorCorners = corners.toFloat()
        mTabStrip?.invalidate()
    }

    /**
     * @param gravity only support Gravity.LEFT,Gravity.RIGHT,Gravity.FILL
     */
    fun setIndicatorGravity(gravity: Int) {
        if (gravity == Gravity.LEFT || gravity == Gravity.RIGHT || Gravity.FILL == gravity) {
            mIndicatorGravity = gravity
            mTabStrip?.setIndicatorGravity()
        } else {
            throw IllegalStateException("only support Gravity.LEFT,Gravity.RIGHT,Gravity.FILL")
        }
    }

//    public void setTabPadding(int padding) {
//
//    }
//
//    public void setTabPadding(int start, int top, int end, int bottom) {
//
//    }

    fun addOnTabSelectedListener(listener: OnTabSelectedListener?) {
        if (listener != null) {
            mTabSelectedListeners?.add(listener)
        }
    }

    fun removeOnTabSelectedListener(listener: OnTabSelectedListener?) {
        if (listener != null) {
            mTabSelectedListeners?.remove(listener)
        }
    }

    fun setTabAdapter(adapter: TabAdapter?) {
        removeAllTabs()
        if (adapter != null) {
            mTabAdapter = adapter
            for (i in 0 until adapter.getCount()) {
                addTab(
                    QTabView(
                        mContext!!
                    ).setIcon(adapter.getIcon(i) as ITabView.TabIcon)
                        ?.setTitle(adapter.getTitle(i) as ITabView.TabTitle)?.setBadge(adapter.getBadge(i) as ITabView.TabBadge)
                        ?.setBackground(adapter.getBackground(i))
                )
            }
        }
    }

    fun setupWithFragment(
        manager: FragmentManager?,
        fragments: List<Fragment>?
    ) {
        setupWithFragment(manager, 0, fragments)
    }

    fun setupWithFragment(
        manager: FragmentManager?,
        fragments: List<Fragment>?,
        adapter: TabAdapter?
    ) {
        setupWithFragment(manager, 0, fragments, adapter)
    }

    fun setupWithFragment(
        manager: FragmentManager?,
        containerResid: Int,
        fragments: List<Fragment>?
    ) {
        if (mTabFragmentManager != null) {
            mTabFragmentManager?.detach()
        }
        if (containerResid != 0) {
            mTabFragmentManager =
                TabFragmentManager(
                    manager,
                    containerResid,
                    fragments,
                    this
                )
        } else {
            mTabFragmentManager =
                TabFragmentManager(
                    manager,
                    fragments,
                    this
                )
        }
    }

    fun setupWithFragment(
        manager: FragmentManager?,
        containerResid: Int,
        fragments: List<Fragment>?,
        adapter: TabAdapter?
    ) {
        setTabAdapter(adapter)
        setupWithFragment(manager, containerResid, fragments)
    }

    fun setupWithViewPager(@Nullable viewPager: ViewPager?) {
        if (mViewPager != null && mTabPageChangeListener != null) {
            mViewPager?.removeOnPageChangeListener(mTabPageChangeListener as ViewPager.OnPageChangeListener)
        }
        if (viewPager != null) {
            val adapter: PagerAdapter = viewPager.adapter
                ?: throw IllegalArgumentException("ViewPager does not have a PagerAdapter set")
            mViewPager = viewPager
            if (mTabPageChangeListener == null) {
                mTabPageChangeListener =
                    OnTabPageChangeListener()
            }
            viewPager.addOnPageChangeListener(mTabPageChangeListener  as ViewPager.OnPageChangeListener)
            addOnTabSelectedListener(object :
                OnTabSelectedListener {
                override fun onTabSelected(tab: TabView?, position: Int) {
                    if (mViewPager != null && mViewPager?.adapter!!.count >= position) {
                        mViewPager?.currentItem = position
                    }
                }

                override fun onTabReselected(tab: TabView?, position: Int) {}
            })
            setPagerAdapter(adapter, true)
        } else {
            mViewPager = null
            setPagerAdapter(null, true)
        }
    }

    private fun setPagerAdapter(@Nullable adapter: PagerAdapter?, addObserver: Boolean) {
        if (mPagerAdapter != null && mPagerAdapterObserver != null) {
            mPagerAdapter?.unregisterDataSetObserver(mPagerAdapterObserver!!)
        }
        mPagerAdapter = adapter
        if (addObserver && adapter != null) {
            if (mPagerAdapterObserver == null) {
                mPagerAdapterObserver =
                    PagerAdapterObserver()
            }
            adapter.registerDataSetObserver(mPagerAdapterObserver!!)
        }
        populateFromPagerAdapter()
    }

    private fun populateFromPagerAdapter() {
        removeAllTabs()
        if (mPagerAdapter != null) {
            val adapterCount: Int = mPagerAdapter?.count!!
            if (mPagerAdapter is TabAdapter) {
                setTabAdapter(mPagerAdapter as TabAdapter?)
            } else {
                for (i in 0 until adapterCount) {
                    val title =
                        if (mPagerAdapter?.getPageTitle(i) == null) "tab$i" else mPagerAdapter?.getPageTitle(
                            i
                        ).toString()
                    addTab(
                        QTabView(
                            mContext!!
                        ).setTitle(
                            ITabView.TabTitle.Builder().setContent(title).build()
                        )
                    )
                }
            }
            // Make sure we reflect the currently set ViewPager item
            if (mViewPager != null && adapterCount > 0) {
                val curItem: Int = mViewPager?.currentItem!!
                if (curItem != getSelectedTabPosition() && curItem < getTabCount()) {
                    setTabSelected(curItem)
                }
            }
        } else {
            removeAllTabs()
        }
    }

    private inner class TabStrip :
        LinearLayout{
        private var mIndicatorTopY = 0f
        private var mIndicatorX = 0f
        private var mIndicatorBottomY = 0f
        private var mLastWidth = 0
        private var mIndicatorPaint: Paint
        private var mIndicatorRect: RectF
        private var mIndicatorAnimatorSet: AnimatorSet? = null

        constructor(context: Context?) : super(context){
            setWillNotDraw(false)
            orientation = VERTICAL
            mIndicatorPaint = Paint()
            mIndicatorPaint.isAntiAlias = true
            mIndicatorGravity = if (mIndicatorGravity == 0) Gravity.LEFT else mIndicatorGravity
            mIndicatorRect = RectF()
            setIndicatorGravity()
        }

        fun setIndicatorGravity() {
            if (mIndicatorGravity == Gravity.LEFT) {
                mIndicatorX = 0f
                if (mLastWidth != 0) mIndicatorWidth = mLastWidth
                setPadding(mIndicatorWidth, 0, 0, 0)
            } else if (mIndicatorGravity == Gravity.RIGHT) {
                if (mLastWidth != 0) mIndicatorWidth = mLastWidth
                setPadding(0, 0, mIndicatorWidth, 0)
            } else if (mIndicatorGravity == Gravity.FILL) {
                mIndicatorX = 0f
                setPadding(0, 0, 0, 0)
            }
            post {
                if (mIndicatorGravity == Gravity.RIGHT) {
                    mIndicatorX = width - mIndicatorWidth.toFloat()
                } else if (mIndicatorGravity == Gravity.FILL) {
                    mLastWidth = mIndicatorWidth
                    mIndicatorWidth = width
                }
                invalidate()
            }
        }

        private fun calcIndicatorY(offset: Float) {
            val index = Math.floor(offset.toDouble()).toInt()
            val childView = getChildAt(index)
            if (Math.floor(offset.toDouble()) != childCount - 1.toDouble() && Math.ceil(
                    offset.toDouble()
                ) != 0.0
            ) {
                val nextView = getChildAt(index + 1)
                mIndicatorTopY =
                    childView.top + (nextView.top - childView.top) * (offset - index)
                mIndicatorBottomY = childView.bottom + (nextView.bottom -
                        childView.bottom) * (offset - index)
            } else {
                mIndicatorTopY = childView.top.toFloat()
                mIndicatorBottomY = childView.bottom.toFloat()
            }
        }

        fun updataIndicator() {
            moveIndicatorWithAnimator(getSelectedTabPosition())
        }

        fun moveIndicator(offset: Float) {
            calcIndicatorY(offset)
            invalidate()
        }

        /**
         * move indicator to a tab location
         *
         * @param index tab location's index
         */
        fun moveIndicatorWithAnimator(index: Int) {
            val direction: Int = index - getSelectedTabPosition()
            val childView = getChildAt(index)
            val targetTop = childView.top.toFloat()
            val targetBottom = childView.bottom.toFloat()
            if (mIndicatorTopY == targetTop && mIndicatorBottomY == targetBottom) return
            if (mIndicatorAnimatorSet != null && mIndicatorAnimatorSet!!.isRunning) {
                mIndicatorAnimatorSet!!.end()
            }
            post {
                var startAnime: ValueAnimator? = null
                var endAnime: ValueAnimator? = null
                if (direction > 0) {
                    startAnime = ValueAnimator.ofFloat(mIndicatorBottomY, targetBottom)
                        .setDuration(100)
                    startAnime.addUpdateListener(AnimatorUpdateListener { animation ->
                        mIndicatorBottomY =
                            animation.animatedValue.toString().toFloat()
                        invalidate()
                    })
                    endAnime = ValueAnimator.ofFloat(mIndicatorTopY, targetTop).setDuration(100)
                    endAnime.addUpdateListener(AnimatorUpdateListener { animation ->
                        mIndicatorTopY = animation.animatedValue.toString().toFloat()
                        invalidate()
                    })
                } else if (direction < 0) {
                    startAnime =
                        ValueAnimator.ofFloat(mIndicatorTopY, targetTop).setDuration(100)
                    startAnime.addUpdateListener(AnimatorUpdateListener { animation ->
                        mIndicatorTopY = animation.animatedValue.toString().toFloat()
                        invalidate()
                    })
                    endAnime =
                        ValueAnimator.ofFloat(mIndicatorBottomY, targetBottom).setDuration(100)
                    endAnime.addUpdateListener(AnimatorUpdateListener { animation ->
                        mIndicatorBottomY =
                            animation.animatedValue.toString().toFloat()
                        invalidate()
                    })
                }
                if (startAnime != null) {
                    mIndicatorAnimatorSet = AnimatorSet()
                    mIndicatorAnimatorSet!!.play(endAnime).after(startAnime)
                    mIndicatorAnimatorSet!!.start()
                }
            }
        }

        override fun onDraw(canvas: Canvas) {
            super.onDraw(canvas)
            mIndicatorPaint.color = mColorIndicator
            mIndicatorRect.left = mIndicatorX
            mIndicatorRect.top = mIndicatorTopY
            mIndicatorRect.right = mIndicatorX + mIndicatorWidth
            mIndicatorRect.bottom = mIndicatorBottomY
            if (mIndicatorCorners != 0f) {
                canvas.drawRoundRect(
                    mIndicatorRect,
                    mIndicatorCorners,
                    mIndicatorCorners,
                    mIndicatorPaint
                )
            } else {
                canvas.drawRect(mIndicatorRect, mIndicatorPaint)
            }
        }

        init {
            setWillNotDraw(false)
            orientation = VERTICAL
            mIndicatorPaint = Paint()
            mIndicatorPaint.isAntiAlias = true
            mIndicatorGravity = if (mIndicatorGravity == 0) Gravity.LEFT else mIndicatorGravity
            mIndicatorRect = RectF()
            setIndicatorGravity()
        }
    }

    private inner class OnTabPageChangeListener : ViewPager.OnPageChangeListener {
        private var mPreviousScrollState = 0
        private var mScrollState = 0
        var mUpdataIndicator = false

        constructor()

        override fun onPageScrollStateChanged(state: Int) {
            mPreviousScrollState = mScrollState
            mScrollState = state
            mUpdataIndicator = !(mScrollState == ViewPager.SCROLL_STATE_SETTLING
                    && mPreviousScrollState == ViewPager.SCROLL_STATE_IDLE)
        }

        override fun onPageScrolled(
            position: Int, positionOffset: Float,
            positionOffsetPixels: Int
        ) {
            if (mUpdataIndicator) {
                mTabStrip?.moveIndicator(positionOffset + position)
            }
        }

        override fun onPageSelected(position: Int) {
            if (position != getSelectedTabPosition()) {
                setTabSelected(position, !mUpdataIndicator, true)
            }
        }
    }

    private inner class PagerAdapterObserver : DataSetObserver() {
        override fun onChanged() {
            populateFromPagerAdapter()
        }

        override fun onInvalidated() {
            populateFromPagerAdapter()
        }
    }

    interface OnTabSelectedListener {
        fun onTabSelected(tab: TabView?, position: Int)
        fun onTabReselected(tab: TabView?, position: Int)
    }
}