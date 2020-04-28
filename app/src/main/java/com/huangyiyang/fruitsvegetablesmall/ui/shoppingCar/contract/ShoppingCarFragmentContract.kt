package com.huangyiyang.fruitsvegetablesmall.ui.shoppingCar.contract

import com.huangyiyang.fruitsvegetablesmall.mvp.http.ApiResult
import com.huangyiyang.fruitsvegetablesmall.bean.ShoppingCarCountBean
import com.huangyiyang.fruitsvegetablesmall.bean.ShoppingCarListBean
import com.huangyiyang.fruitsvegetablesmall.mvp.model.BaseModelInterface
import com.huangyiyang.fruitsvegetablesmall.mvp.presenter.BasePresenter
import com.huangyiyang.fruitsvegetablesmall.mvp.view.BaseViewInterface
import okhttp3.RequestBody
import rx.Observable

interface ShoppingCarFragmentContract {

    interface ShoppingCarFragmentModel : BaseModelInterface {

        fun getShoppingCarList(header: Map<String, String>?): Observable<ApiResult<ShoppingCarListBean>?>?

        fun deleteShoppingCarGoods(
            header: Map<String, String>?,
            param: RequestBody?
        ): Observable<ApiResult<Void>?>?

        fun deleteShoppingCarGood(
            header: Map<String, String>?,
            param: String?
        ): Observable<ApiResult<Void>?>?

        fun updateShoppingCarCount(
            header: Map<String, String>?,
            id: Int,
            body: RequestBody?
        ): Observable<ApiResult<Void>?>?

        fun getShoppingCarCount(header: Map<String, String>?): Observable<ApiResult<ShoppingCarCountBean>?>?
    }

    interface ShoppingCarFragmentView : BaseViewInterface {
        fun setShoppingCarList(bean: ShoppingCarListBean?)
        fun deleteSuccess()
        fun updateSuccess()
        fun setShoppingCarCount(bean: ShoppingCarCountBean?)
    }

    abstract class ShoppingCarFragmentPresenter :
        BasePresenter<ShoppingCarFragmentModel, ShoppingCarFragmentView>() {
        abstract fun getShoppingCarList(header: Map<String, String>?)
        abstract fun deleteShoppingCarGoods(
            header: Map<String, String>?,
            param: RequestBody?,
            toastStr: String?
        )

        abstract fun deleteShoppingCarGood(
            header: Map<String, String>?,
            param: String?
        )

        abstract fun updateShoppingCarCount(
            header: Map<String, String>?,
            id: Int,
            body: RequestBody?
        )

        abstract fun getShoppingCarCount(header: Map<String, String>?)
    }
}