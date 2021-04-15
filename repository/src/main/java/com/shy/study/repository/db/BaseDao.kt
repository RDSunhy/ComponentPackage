package com.shy.study.repository.db

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(element: T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMany(elements: MutableList<T>)

    @Delete
    fun delete(element: T)

    @Delete
    fun deleteMany(elements:MutableList<T>)

    @Update
    fun update(element: T)

}