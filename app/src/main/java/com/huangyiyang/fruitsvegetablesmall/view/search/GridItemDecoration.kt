package com.huangyiyang.fruitsvegetablesmall.view.search

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.huangyiyang.fruitsvegetablesmall.mvp.util.DensityUtil

class GridItemDecoration : RecyclerView.ItemDecoration{
    private var mContext: Context? = null
    private var spanCount = 0
    private var dividerWidth = 0
    private var hasBanner = false

    /**
     * @param spanCount      gridLayoutManager 列数
     * @param dividerWidthDp 分割块的宽/高,单位:dp（m值，已适配）
     */
    constructor(
        context: Context?,
        spanCount: Int,
        dividerWidthDp: Int,
        hasBanner: Boolean
    ) {
        mContext = context
        this.spanCount = spanCount
        dividerWidth = DensityUtil.dp2px(dividerWidthDp)
        this.hasBanner = hasBanner
    }

    override fun getItemOffsets(@NonNull outRect: Rect, @NonNull child: View, @NonNull parent: RecyclerView, @NonNull state: RecyclerView.State) {
        super.getItemOffsets(outRect, child!!, parent, state!!)
        val pos = parent.getChildAdapterPosition(child)
        // 计算这个child 处于第几列
        if (hasBanner) {
            if (pos != 1) { //跳过header
                val column = pos % spanCount
                when (column) {
                    0 -> {
                        outRect.left = column * dividerWidth / spanCount + dividerWidth
                        outRect.right = dividerWidth - (column + 1) * dividerWidth / spanCount
                    }
                    1 -> {
                        outRect.left = column * dividerWidth / spanCount
                        outRect.right =
                            dividerWidth - (column + 1) * dividerWidth / spanCount + dividerWidth
                    }
                    2 -> {
                        outRect.left = column * dividerWidth / spanCount + dividerWidth
                        outRect.right = dividerWidth - (column + 1) * dividerWidth / spanCount
                    }
                }
                if (pos >= spanCount + 2) outRect.top = dividerWidth
            }
        } else {
            val column = pos % spanCount
            when (column) {
                0 -> {
                    outRect.left = column * dividerWidth / spanCount + dividerWidth
                    outRect.right = dividerWidth - (column + 1) * dividerWidth / spanCount
                }
                1 -> {
                    outRect.left = column * dividerWidth / spanCount
                    outRect.right =
                        dividerWidth - (column + 1) * dividerWidth / spanCount + dividerWidth
                }
            }
            outRect.top = dividerWidth
        }
    }

}