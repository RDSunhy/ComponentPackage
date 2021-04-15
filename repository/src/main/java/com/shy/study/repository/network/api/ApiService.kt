package com.shy.study.repository.network.api

import androidx.lifecycle.LiveData
import com.shy.study.repository.entity.Banner
import com.shy.study.repository.network.ApiResponse
import retrofit2.http.GET

interface ApiService {

    @GET("https://wanandroid.com/wxarticle/chapters/json")
    fun getBanner(): LiveData<ApiResponse<Banner>>

}