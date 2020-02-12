package com.huangyiyang.fruitsvegetablesmall.bean

class OrderDetailBean(
    var id: Int,
    var code: String,
    var date: String,
    var amount: Double,
    var discountAmount: Double,
    var paidAmount: Double,
    var receiver: String,
    var address: String,
    var mobile: String,
    var status: String,
    var note: String,
    var details: List<DetailsBean>
) {
    companion object{
        class DetailsBean(
            var id: Int,
            var quantity: Double,
            var name: String,
            var imageUrl: String,
            var price: Double,
            var type: String)
    }
}