package com.huangyiyang.fruitsvegetablesmall.view.main

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView

class ImageViewSquare(context: Context?) : ImageView(context) {
    constructor(context:Context?, attrs:AttributeSet?):this(context)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }
}