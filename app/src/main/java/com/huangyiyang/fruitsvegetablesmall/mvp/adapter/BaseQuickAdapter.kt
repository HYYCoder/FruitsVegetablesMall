package com.huangyiyang.fruitsvegetablesmall.mvp.adapter


import android.animation.Animator
import android.content.Context
import android.view.View
import android.view.animation.Interpolator
import com.zhouyou.recyclerview.adapter.*

abstract class BaseQuickAdapter<T> :
    HelperRecyclerViewAdapter<T> {


    private var mAnimationType: AnimationType? = null
    private var mAnimationDuration = 300
    private var showItemAnimationEveryTime = false
    private var mItemAnimationInterpolator: Interpolator? = null
    private var mCustomAnimator: HelperRecyclerViewAnimAdapter.CustomAnimator? = null
    private var mLastItemPosition = -1

    constructor(context: Context?):super(context)

    constructor(
        context: Context?,
        layoutIds: Int
    ) : super(context,layoutIds)

    constructor(
        data: List<T>?,
        context: Context?
    ):super(data,context)

    constructor(
        data: List<T>?,
        context: Context?,
        layoutId: Int
    ): super(data,context,layoutId)

//    @Override
//    public void onBindViewHolder(BH holder, int position, List<Object> payloads) {
//        addAnimation(holder);
//        super.onBindViewHolder(holder, position, payloads);
//    }

    //    @Override
//    public void onBindViewHolder(BH holder, int position, List<Object> payloads) {
//        addAnimation(holder);
//        super.onBindViewHolder(holder, position, payloads);
//    }
    override fun onBindViewHolder(holder: BH, position: Int) {
        super.onBindViewHolder(holder, position)
        addAnimation(holder)
    }

    protected fun addAnimation(holder: BH) {
        val currentPosition = holder.adapterPosition
        if (null != mCustomAnimator) {
            mCustomAnimator!!.getAnimator(holder.itemView).setDuration(mAnimationDuration.toLong())
                .start()
        } else if (null != mAnimationType) {
            if (showItemAnimationEveryTime || currentPosition > mLastItemPosition) {
                AnimationUtil()
                    .setAnimationType(mAnimationType)
                    .setTargetView(holder.itemView)
                    .setDuration(mAnimationDuration)
                    .setInterpolator(mItemAnimationInterpolator)
                    .start()
                mLastItemPosition = currentPosition
            }
        }
    }

    /**
     * Animation api
     */
    open fun setItemAnimation(animationType: AnimationType?) {
        mAnimationType = animationType
    }

    open fun setItemAnimationDuration(animationDuration: Int) {
        mAnimationDuration = animationDuration
    }

    open fun setItemAnimationInterpolator(animationInterpolator: Interpolator?) {
        mItemAnimationInterpolator = animationInterpolator
    }

    open fun setShowItemAnimationEveryTime(showItemAnimationEveryTime: Boolean) {
        this.showItemAnimationEveryTime = showItemAnimationEveryTime
    }

    open fun setCustomItemAnimator(customAnimator: HelperRecyclerViewAnimAdapter.CustomAnimator?) {
        mCustomAnimator = customAnimator
    }

    interface CustomAnimator {
        fun getAnimator(itemView: View?): Animator?
    }

    open fun addData(data: List<T>?) {
        if (data != null && data.isNotEmpty()) {
            mList.addAll(data)
            notifyDataSetChanged()
        }
    }

}