package com.shy.basic.application

import android.content.Context
import android.content.pm.ApplicationInfo
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.multidex.MultiDex
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.util.CoilUtils
import com.alibaba.android.arouter.launcher.ARouter
import com.shy.basic.utils.AppExecutors
import okhttp3.OkHttpClient
import skin.support.SkinCompatManager
import skin.support.app.SkinAppCompatViewInflater
import skin.support.app.SkinCardViewInflater
import skin.support.constraint.app.SkinConstraintViewInflater
import skin.support.design.app.SkinMaterialViewInflater
import kotlin.properties.Delegates


abstract class BaseApplication : AbsApplication(),ImageLoaderFactory, ViewModelStoreOwner {

    companion object {
        private var instance: BaseApplication by Delegates.notNull()
        fun instance() = instance
        fun isDebug(): Boolean {
            return instance().applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0
        }
    }

    private val mAppViewModelStore: ViewModelStore by lazy { ViewModelStore() }

    override fun attachBaseContext(base: Context?) {

        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        ARouter.openLog()
        ARouter.openDebug()
        ARouter.init(this)
        AppExecutors.init()
        onModuleAppCreate(this)
        initModule(this)
        SkinCompatManager.withoutActivity(this)
            .addInflater(SkinAppCompatViewInflater()) // 基础控件换肤初始化
            .addInflater(SkinMaterialViewInflater()) // material design 控件换肤初始化[可选]
            .addInflater(SkinConstraintViewInflater()) // ConstraintLayout 控件换肤初始化[可选]
            .addInflater(SkinCardViewInflater()) // CardView v7 控件换肤初始化[可选]
            //.setSkinStatusBarColorEnable(false) // 关闭状态栏换肤，默认打开[可选]
            .setSkinWindowBackgroundEnable(false) // 关闭windowBackground换肤，默认打开[可选]
            .loadSkin()
    }

    override fun getViewModelStore(): ViewModelStore {
        return mAppViewModelStore
    }

    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(applicationContext)
            .crossfade(true)
            .availableMemoryPercentage(0.25)
            .okHttpClient {
                OkHttpClient.Builder()
                    .cache(CoilUtils.createDefaultCache(applicationContext))
                    .build()
            }
            .build()
    }
}