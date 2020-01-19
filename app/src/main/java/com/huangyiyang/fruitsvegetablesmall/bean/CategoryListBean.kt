package com.huangyiyang.fruitsvegetablesmall.bean

import android.os.Parcel
import android.os.Parcelable

class CategoryListBean() : Parcelable {

    var category: CategoryBean? = null
    var subCategories: List<SubCategoriesBean>? = null

    class CategoryBean{

    }

    class SubCategoriesBean{
        var id = 0
    }

    constructor(parcel: Parcel) : this() {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CategoryListBean> {
        override fun createFromParcel(parcel: Parcel): CategoryListBean {
            return CategoryListBean(parcel)
        }

        override fun newArray(size: Int): Array<CategoryListBean?> {
            return arrayOfNulls(size)
        }
    }

}