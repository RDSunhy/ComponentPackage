package com.shy.study.home

import com.shy.study.home.service.IHomeService

class ServiceFactory private constructor() {
    companion object {
        val instance: ServiceFactory by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            ServiceFactory()
        }
    }

    private var mHomeService: IHomeService? = null

    fun getHomeService() : IHomeService {
        if (mHomeService == null) {
            mHomeService = EmptyServiceImpl()
        }
        return mHomeService!!
    }

    fun setHomeService(service: IHomeService?) {
        this.mHomeService = service
    }

}