package com.huangyiyang.fruitsvegetablesmall.api

import com.huangyiyang.fruitsvegetablesmall.bean.CategoryListBean
import com.huangyiyang.fruitsvegetablesmall.bean.RecommendGoodsBean
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.Path
import retrofit2.http.QueryMap
import rx.Observable


interface HttpApi {

    //获取首页推荐商品列表
    @GET("/goods")
    fun getRecommendList(@HeaderMap header: Map<String?, String?>?): Observable<ApiResult<List<RecommendGoodsBean?>?>?>?

    //获取分类信息
    @GET("/categories")
    fun getCategoriesList(@HeaderMap header: Map<String?, String?>?): Observable<ApiResult<List<CategoryListBean?>?>?>?

}