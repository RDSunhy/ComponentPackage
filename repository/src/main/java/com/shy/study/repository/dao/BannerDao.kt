package com.shy.study.repository.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.shy.study.repository.db.BaseDao
import com.shy.study.repository.entity.Banner

@Dao
interface BannerDao : BaseDao<Banner> {

    @Query("SELECT * FROM banner ORDER BY 'id' DESC")
    fun getNewest(): LiveData<Banner>

    @Query("SELECT * FROM banner ORDER BY 'id' DESC")
    fun getNewest2(): Banner
}