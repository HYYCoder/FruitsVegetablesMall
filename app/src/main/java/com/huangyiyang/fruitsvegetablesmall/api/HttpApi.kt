package com.huangyiyang.fruitsvegetablesmall.api

import com.huangyiyang.fruitsvegetablesmall.bean.CategoryListBean
import com.huangyiyang.fruitsvegetablesmall.bean.RecommendGoodsBean
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
    fun getRecommendList(@HeaderMap header: Map<String, String>?, @QueryMap body:Map<String, String>?): Observable<ApiResult<List<RecommendGoodsBean>?>?>?

    //获取分类信息
    @GET("/categories")
    fun getCategoriesList(@HeaderMap header: Map<String, String>?): Observable<ApiResult<List<CategoryListBean>?>?>?

    //加入购物车
    @PUT("/cart/items")
    fun addShoppingCar(@HeaderMap header: Map<String, String>?, @Body body: RequestBody?): Observable<ApiResult<Void>?>?

}