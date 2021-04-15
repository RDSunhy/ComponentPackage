package com.shy.study.repository.entity.converter

import androidx.room.TypeConverter
import com.blankj.utilcode.util.GsonUtils
import com.google.gson.reflect.TypeToken
import com.shy.study.repository.entity.Banner
import org.json.JSONArray
import org.json.JSONException
import java.lang.Exception


class BannerConverter {
    @TypeConverter
    fun revert(str: String?): List<Banner.Data>? {
        try {
            return GsonUtils.fromJson(str, object : TypeToken<List<Banner.Data>>(){}.type)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    @TypeConverter
    fun converter(data: List<Banner.Data>?): String {
        return GsonUtils.toJson(data)
    }
}
