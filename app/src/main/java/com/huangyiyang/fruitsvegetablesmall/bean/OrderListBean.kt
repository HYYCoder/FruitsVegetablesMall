package com.huangyiyang.fruitsvegetablesmall.bean

class OrderListBean(
    var id: Int,
    val code: String,
    val data: String,
    val skusCount: Int,
    val status: String,
    val payAmount: Double,
    val commissions: Int,
    val isRevised: Boolean,
    val imageUrls: List<String>
)