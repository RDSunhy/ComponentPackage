package com.shy.study.home

import android.content.Context
import com.shy.study.home.service.IHomeService

class EmptyServiceImpl : IHomeService {

    override fun getHomeModuleMessage(): String {
        return "error"
    }

    override fun init(context: Context?) {
    }

}