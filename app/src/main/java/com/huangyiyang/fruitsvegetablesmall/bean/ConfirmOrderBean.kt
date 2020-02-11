package com.huangyiyang.fruitsvegetablesmall.bean

class ConfirmOrderBean(var mobile : String,
                       var address : String,
                       var discountAmount : Float,
                       var amount : Double,
                       var paidAmount : Double,
                       var coupons : MutableList<Int>,
                       var cartItems : MutableList<CartItemsBean>,
                       var gifts : MutableList<GiftsBean>) {
    class CartItemsBean(var id : Int,
                        var name : String,
                        var imageUrls : String,
                        var price : Double,
                        var quantity : Double,
                        var stock : Double,
                        var specification : String)
    class GiftsBean(var id : Int,
                    var name : String,
                    var imageUrl : String,
                    var price : Double,
                    var quantity : Double,
                    var specification : String)
}