package com.huangyiyang.fruitsvegetablesmall.api

import com.huangyiyang.fruitsvegetablesmall.bean.CategoryListBean
import com.huangyiyang.fruitsvegetablesmall.bean.GoodsDetailBean
import com.huangyiyang.fruitsvegetablesmall.util.BannerUtil
import okhttp3.RequestBody
import retrofit2.http.*
import rx.Observable


interface HttpApi {

    //获取首页Banner列表
    @GET("/specialoffers")
    fun getBannerList(@HeaderMap header: Map<String, String>?, @QueryMap body:Map<String, String>?): Observable<ApiResult<List<BannerUtil.DataBean>?>?>?

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
    @PUT("/cart/items")
    fun addShoppingCar(@HeaderMap header: Map<String, String>?, @Body body: RequestBody?): Observable<ApiResult<Void>?>?

}