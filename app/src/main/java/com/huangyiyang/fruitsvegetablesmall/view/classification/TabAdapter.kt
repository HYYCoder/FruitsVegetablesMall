package com.huangyiyang.fruitsvegetablesmall.view.classification

interface TabAdapter {
    fun getCount(): Int

    fun getBadge(position: Int)

    fun getIcon(position: Int)

    fun getTitle(position: Int)

    fun getBackground(position: Int): Int
}