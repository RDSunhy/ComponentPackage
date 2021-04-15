package com.shy.study.home.service

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import com.shy.study.home.router.HomeRouterTable
import com.shy.study.home.service.IHomeService

@Route(path = HomeRouterTable.PATH_SERVICE_HOME)
class HomeServiceImpl : IHomeService {

    override fun getHomeModuleMessage(): String {
        return "Home Module Message : Success~"
    }

    override fun init(context: Context?) {
    }

}