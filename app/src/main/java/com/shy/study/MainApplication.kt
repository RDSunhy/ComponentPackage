package com.shy.study

import android.app.Application
import coil.ImageLoaderFactory
import com.shy.basic.application.BaseApplication

class MainApplication : BaseApplication() {

    private val mHomeApp = "com.shy.study.home.HomeApplication"
    private val mSquareApp = "com.shy.study.square.SquareApplication"
    private val mMineApp = "com.shy.study.mine.MineApplication"

    private val moduleApps = arrayOf(
        mHomeApp,
        mSquareApp,
        mMineApp
    )

    override fun onCreate() {
        super.onCreate()
    }

    override fun onModuleAppCreate(application: Application?) {
        for (moduleApp in moduleApps) {
            try {
                val clazz = Class.forName(moduleApp)
                val mApp: BaseApplication = clazz.newInstance() as BaseApplication
                mApp.onModuleAppCreate(this)
            } catch (e: ClassNotFoundException) {
                e.printStackTrace()
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            } catch (e: InstantiationException) {
                e.printStackTrace()
            }
        }
    }

    override fun initModule(application: Application?) {
        for (moduleApp in moduleApps) {
            try {
                val clazz = Class.forName(moduleApp)
                val mApp: BaseApplication = clazz.newInstance() as BaseApplication
                mApp.initModule(this)
            } catch (e: ClassNotFoundException) {
                e.printStackTrace()
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            } catch (e: InstantiationException) {
                e.printStackTrace()
            }
        }
    }

}