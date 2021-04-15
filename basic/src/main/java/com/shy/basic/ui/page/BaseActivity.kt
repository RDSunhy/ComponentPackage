package com.shy.basic.ui.page

import android.app.Activity
import android.app.Application
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.annotation.LayoutRes
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.SkinAppCompatDelegateImpl
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.parfoismeng.slidebacklib.registerSlideBack
import com.parfoismeng.slidebacklib.unregisterSlideBack
import com.shy.basic.application.BaseApplication
import com.shy.basic.extension.observeNonNull
import com.shy.basic.extension.observeNullable
import com.shy.basic.manager.NetworkStateManager
import com.shy.basic.ui.vm.BaseViewModel
import com.shy.basic.ui.vm.AppShareViewModel

abstract class BaseActivity<B : ViewDataBinding, VM : BaseViewModel> : AppCompatActivity(), IBaseView {

    protected lateinit var mBinding: B
    protected lateinit var mViewModel: VM
    protected lateinit var mAppViewModel: AppShareViewModel

    private lateinit var mActivityProvider: ViewModelProvider
    private lateinit var mApplicationProvider: ViewModelProvider

    protected lateinit var mLoadService: LoadService<*>

    @LayoutRes
    protected abstract fun getLayoutId(): Int

    protected abstract fun initViewModel(): VM

    /**
     *  初始化操作
     */
    protected abstract fun initParams(savedInstanceState: Bundle?)
    protected abstract fun initView()
    protected abstract fun initData()
    protected abstract fun initListener()
    protected abstract fun initObserver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStatusBarTransparent(this)
        setStatusBarText()
        init()
        initParams(savedInstanceState)
        initView()
        initData()
        initListener()
        initObserver()
        lifecycle.addObserver(NetworkStateManager.instance())
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterSlideBack()
    }

    protected open fun init() {
        initDataBinding()
        mViewModel = initViewModel()
        lifecycle.addObserver(mViewModel)
        initInternalObserver()
        registerSlideBack {
            finish()
        }
    }

    protected fun setLoadsir(view: View?) {
        mLoadService = LoadSir.getDefault().register(view) { onRetry() }
    }

    protected open fun initDataBinding() {
        mBinding = DataBindingUtil.setContentView(this, getLayoutId())
        mBinding.lifecycleOwner = this
    }

    protected fun initInternalObserver() {
        mViewModel.mLoadingUIEvent.observeNullable(this) {
            showLoading()
        }

        mViewModel.mSuccessUIEvent.observeNullable(this){
            showContent()
        }

        mViewModel.mEmptyUIEvent.observeNullable(this){
            showEmpty()
        }

        mViewModel.mErrorUIEvent.observeNonNull(this) {
            showError()
        }
    }


    protected fun <T : ViewModel> getActivityScopeViewModel(modelClass: Class<T>): T {
        mActivityProvider = ViewModelProvider(this)
        return mActivityProvider.get(modelClass)
    }

    protected fun <T : ViewModel> getApplicationScopeViewModel(modelClass: Class<T>): T {
        if (!this::mApplicationProvider.isInitialized) {
            mApplicationProvider = ViewModelProvider(
                this.applicationContext as BaseApplication,
                getAppFactory(this)
            )
        }
        return mApplicationProvider.get(modelClass)
    }

    protected fun getAppFactory(activity: Activity): ViewModelProvider.Factory {
        val application: Application = checkApplication(activity)
        return ViewModelProvider.AndroidViewModelFactory.getInstance(application)
    }

    protected fun checkApplication(activity: Activity): Application {
        return activity.application
            ?: throw IllegalStateException(
                "Your activity/fragment is not yet attached to "
                        + "Application. You can't request ViewModel before onCreate call."
            )
    }

    fun setStatusBarTransparent(activity: Activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return
        }
        val window = activity.window
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            val option =
                window.decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            window.decorView.systemUiVisibility = option
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                window.statusBarColor = Color.parseColor("#00000000")
            } else {
                window.statusBarColor = Color.parseColor("#20000000")
            }
        } else {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
    }

    /**
     * 设置状态栏字体颜色 是否为黑色
     * @param isBlack  true：字体为黑色
     */
    @RequiresApi(Build.VERSION_CODES.HONEYCOMB)
    fun setStatusBarText(isBlack: Boolean = true) {
        val window = this.window
        if (isBlack) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            setStatusBarTransparent(this)
        } else {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            setStatusBarTransparent(this)
        }
    }

    /**
     * 隐藏状态栏
     * @param isHidden true：隐藏
     */
    fun setStatusBarHidden(isHidden: Boolean = true) {
        val window = this.window
        if (isHidden) {
            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        } else {
            window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }
    }

    override fun onRetry() {
        showLoading()
    }

    override fun showLoadingDialog() {

    }

    override fun dismissLoadingDialog() {

    }

    override fun showLoading() {
        if (this::mLoadService.isInitialized) {
            mLoadService.showSuccess()
        }
    }

    override fun showContent() {
        if (this::mLoadService.isInitialized) {
            mLoadService.showSuccess()
        }
    }

    override fun showEmpty() {
        if (this::mLoadService.isInitialized) {
            mLoadService.showSuccess()
        }
    }

    override fun showError() {
        if (this::mLoadService.isInitialized) {
            mLoadService.showSuccess()
        }
    }

    override fun getDelegate(): AppCompatDelegate {
        return SkinAppCompatDelegateImpl.get(this, this)
    }
}