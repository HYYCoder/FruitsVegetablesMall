package com.huangyiyang.fruitsvegetablesmall.api

import com.huangyiyang.fruitsvegetablesmall.bean.*
import com.huangyiyang.fruitsvegetablesmall.util.BannerUtil
import okhttp3.RequestBody
import retrofit2.http.*
import rx.Observable


interface HttpApi {

    //登录接口
    @POST("/login")
    fun login(@Body body: RequestBody?): Observable<ApiResult<LoginBean>?>?

    //获取首页Banner列表
    @GET("/specialoffers")
    fun getBannerList(@HeaderMap header: Map<String, String>?, @QueryMap body:Map<String, String>?): Observable<ApiResult<List<BannerUtil.Companion.DataBean>?>?>?

    //获取首页推荐商品列表
    @GET("/goods")
    fun getGoodsList(@HeaderMap header: Map<String, String>?, @QueryMap body:Map<String, String>?): Observable<ApiResult<List<GoodsDetailBean>?>?>?

    //获取分类信息
    @GET("/categories")
    fun getCategoriesList(@HeaderMap header: Map<String, String>?): Observable<ApiResult<List<CategoryListBean>?>?>?

    //获取商品详情
    @GET("/goods/{id}")
    fun getGoodsDetail(
        @HeaderMap header: Map<String, String>?, @Path(
            "id"
        ) id: String?
    ): Observable<ApiResult<GoodsDetailBean>?>?

    //加入购物车
    @POST("/cart/items")
    fun addShoppingCar(@HeaderMap header: Map<String, String>?, @Body body: RequestBody?): Observable<ApiResult<Void>?>?

    //购物车列表
    @GET("/cart/items")
    fun getShoppingCarList(@HeaderMap header: Map<String, String>?): Observable<ApiResult<ShoppingCarListBean>?>?

    //购物车删除单个商品
    @DELETE("/cart/item/{itemId}")
    fun deleteShoppingCarGood(
        @HeaderMap header: Map<String, String>?, @Path(
            "itemId"
        ) itemId: String?
    ): Observable<ApiResult<Void>?>?

    //购物车批量删除商品
    @POST("/cart/items/bacthdel")
    fun deleteShoppingCarGoods(@HeaderMap header: Map<String, String>?, @Body body: RequestBody?): Observable<ApiResult<Void>?>?

    //购物车内商品数量
    @GET("/cart/items/count")
    fun getShoppingCarCount(@HeaderMap header: Map<String, String>?): Observable<ApiResult<ShoppingCarCountBean>?>?

    //更新购物车商品购买数量
    @PUT("/cart/item/{itemId}")
    fun updateShoppingCarCount(
        @HeaderMap header: Map<String, String>?, @Path(
            "itemId"
        ) itemId: String?, @Body body: RequestBody?
    ): Observable<ApiResult<Void>?>?
}