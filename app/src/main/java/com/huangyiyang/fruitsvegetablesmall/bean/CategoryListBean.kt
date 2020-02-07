package com.huangyiyang.fruitsvegetablesmall.bean

import android.os.Parcel
import android.os.Parcelable

data class CategoryListBean(var category: CategoryBean?,var subCategories: List<SubCategoriesBean>?) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readParcelable(CategoryBean::class.java.classLoader),
        parcel.createTypedArrayList(SubCategoriesBean)
    )

    class CategoryBean(var id:Int?,var orders:Int?,var name:String?,var imageUrl:String?) : Parcelable{
        constructor(parcel: Parcel) : this(
            parcel.readValue(Int::class.java.classLoader) as? Int,
            parcel.readValue(Int::class.java.classLoader) as? Int,
            parcel.readString(),
            parcel.readString()
        )

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeValue(id)
            parcel.writeValue(orders)
            parcel.writeString(name)
            parcel.writeString(imageUrl)
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

    class SubCategoriesBean(var id:Int?,var pid:Int?,var orders:Int?,var name:String?) : Parcelable{
        constructor(parcel: Parcel) : this(
            parcel.readValue(Int::class.java.classLoader) as? Int,
            parcel.readValue(Int::class.java.classLoader) as? Int,
            parcel.readValue(Int::class.java.classLoader) as? Int,
            parcel.readString()
        )

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeValue(id)
            parcel.writeValue(pid)
            parcel.writeValue(orders)
            parcel.writeString(name)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<SubCategoriesBean> {
            override fun createFromParcel(parcel: Parcel): SubCategoriesBean {
                return SubCategoriesBean(parcel)
            }

            override fun newArray(size: Int): Array<SubCategoriesBean?> {
                return arrayOfNulls(size)
            }
        }

    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(category, flags)
        parcel.writeTypedList(subCategories)
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