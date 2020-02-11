package com.huangyiyang.fruitsvegetablesmall.bean

class ShoppingCarListBean(
    var normalItems: List<NormalItemsBean>,
    var abnormalItems: List<NormalItemsBean>
) {

    companion object {
        class NormalItemsBean(
            var id: Int,
            var shoppingCarId: Int,
            var imageUrls: String,
            var name: String,
            var price: Double,
            var stock: Double,
            var specification: String,
            var minimunOrderQuantity: Double,
            var maximumOrderQuantity: Double,
            var minimumIncrementQuantity: Double,
            var quantity: Double,
            var isChecked: Boolean
        )
    }
}