package com.huangyiyang.fruitsvegetablesmall.api

import com.huangyiyang.fruitsvegetablesmall.bean.*
import com.huangyiyang.fruitsvegetablesmall.mvp.http.ApiResult
import com.huangyiyang.fruitsvegetablesmall.mvp.util.BannerUtil
import okhttp3.RequestBody
import retrofit2.http.*
import rx.Observable


interface HttpApi {

    //登录接口
    @POST("/login")
    fun login(@Body body: RequestBody?): Observable<ApiResult<LoginBean>?>?

    //注册接口
    @POST("/register")
    fun register(@Body body: RequestBody?): Observable<ApiResult<LoginBean>?>?

    //获取首页Banner列表
    @GET("/specialoffers")
    fun getBannerList(@HeaderMap header: Map<String, String>?, @QueryMap body:Map<String, String>?): Observable<ApiResult<List<BannerUtil.Companion.DataBean>?>?>?

    //获取商品列表
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
    @DELETE("/cart/item/{id}")
    fun deleteShoppingCarGood(
        @HeaderMap header: Map<String, String>?, @Path(
            "id"
        ) id: String?
    ): Observable<ApiResult<Void>?>?

    //购物车批量删除商品
    @POST("/cart/items/bacthdel")
    fun deleteShoppingCarGoods(@HeaderMap header: Map<String, String>?, @Body body: RequestBody?): Observable<ApiResult<Void>?>?

    //购物车内商品数量
    @GET("/cart/items/count")
    fun getShoppingCarCount(@HeaderMap header: Map<String, String>?): Observable<ApiResult<ShoppingCarCountBean>?>?

    //更新购物车商品购买数量
    @PUT("/cart/item/{id}")
    fun updateShoppingCarCount(
        @HeaderMap header: Map<String, String>?, @Path(
            "id"
        ) id: String?, @Body body: RequestBody?
    ): Observable<ApiResult<Void>?>?

    //获取确认订单详情
    @POST("/cart/settle")
    fun getConfirmOrder(@HeaderMap header: Map<String, String>?, @Body body: RequestBody?): Observable<ApiResult<ConfirmOrderBean>?>?

    //下单
    @PUT("/orders")
    fun placeOrder(@HeaderMap header: Map<String, String>?, @Body body: RequestBody?): Observable<ApiResult<OrderBean>?>?

    //获取订单列表
    @GET("/orders")
    fun getOrderList(@HeaderMap header: Map<String, String>?, @QueryMap body: Map<String, String>?): Observable<ApiResult<List<OrderListBean>?>?>?

    //订单详情
    @GET("/order/{id}")
    fun getOrderDetail(
        @HeaderMap header: Map<String, String>?, @Path(
            "id"
        ) id: String?
    ): Observable<ApiResult<OrderDetailBean>?>?

    //更新订单详情
    @PUT("/order/{id}")
    fun updateOrderDetail(
        @HeaderMap header: Map<String, String>?, @Path(
            "id"
        ) id: String?, @Body body: RequestBody?
    ): Observable<ApiResult<Void>?>?
}