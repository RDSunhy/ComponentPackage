package com.shy.basic.application

import android.app.Application

abstract class AbsApplication : Application() {

    /**
     * 初始化优先级
     */
    fun getPriority(): Int{
        return 5
    }

    /**
     * Application 初始化
     */
    abstract fun onModuleAppCreate(application: Application?)

    /**
     * 所有 Application 初始化后的自定义操作
     */
    abstract fun initModule(application: Application?)

}