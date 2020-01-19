package com.huangyiyang.fruitsvegetablesmall.bean

import android.os.Parcel
import android.os.Parcelable

class CategoryBean() : Parcelable {
    constructor(parcel: Parcel) : this() {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CategoryBean> {
        override fun createFromParcel(parcel: Parcel): CategoryBean {
            return CategoryBean(parcel)
        }

        override fun newArray(size: Int): Array<CategoryBean?> {
            return arrayOfNulls(size)
        }
    }
}