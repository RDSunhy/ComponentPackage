package com.shy.study.home

import android.app.Application
import android.util.Log
import com.shy.basic.application.BaseApplication
import com.shy.study.home.service.HomeServiceImpl

class HomeApplication : BaseApplication() {

    override fun onModuleAppCreate(application: Application?) {
        Log.e("测试Application", "success")
        ServiceFactory.instance.setHomeService(HomeServiceImpl())
    }

    override fun initModule(application: Application?) {
    }
}