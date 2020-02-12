package com.huangyiyang.fruitsvegetablesmall.bean

class OrderListBean(
    var id: Int,
    val code: String,
    val data: String,
    val count: Int,
    val status: String,
    val payAmount: Double,
    val imageUrls: List<String>
)