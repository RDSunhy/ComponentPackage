package com.shy.study.mine.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.shy.flag.data.network.TransformationsLiveData
import com.shy.study.repository.db.AppDataBase
import com.shy.study.repository.entity.Banner
import com.shy.study.repository.network.ApiResponse
import com.shy.study.repository.network.HttpUtils
import com.shy.study.repository.network.adapter.Resource

class MineRepo {

    private var bannerDao = AppDataBase.instance.bannerDao()

    fun getBanner(): MutableLiveData<Resource<Banner>> {
        return object : TransformationsLiveData<Banner, Banner>(){
            override fun saveApiData(item: Banner) {
                bannerDao.insert(item)
            }

            override fun shouldFetch(data: Banner?): Boolean {
                return data == null
            }

            override fun getByLocal(): LiveData<Banner> {
                return bannerDao.getNewest()
            }

            override fun getByApi(): LiveData<ApiResponse<Banner>> {
                return HttpUtils.apiService.getBanner()
            }

        }.asLiveData()
    }

}