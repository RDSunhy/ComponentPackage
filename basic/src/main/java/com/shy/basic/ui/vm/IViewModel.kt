package com.shy.basic.ui.vm

interface IViewModel{
    /**
     * 是否显示Loading视图
     */
    fun showLoading(isShow: Boolean)

    /**
     * 是否显示空白视图
     */
    fun showEmpty(isShow: Boolean)

    /**
     * 是否显示错误视图
     */
    fun showError(isShow: Boolean)
}