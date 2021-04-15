package com.shy.study.home.service

import com.alibaba.android.arouter.facade.template.IProvider

interface IHomeService: IProvider {

    /**
     * 获取Home模块信息
     */
    fun getHomeModuleMessage(): String
}