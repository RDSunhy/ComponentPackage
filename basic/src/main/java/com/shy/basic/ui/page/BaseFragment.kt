package com.shy.basic.ui.page

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.SkinAppCompatDelegateImpl
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.shy.basic.application.BaseApplication
import com.shy.basic.extension.observeNonNull
import com.shy.basic.extension.observeNullable
import com.shy.basic.manager.NetworkStateManager
import com.shy.basic.ui.vm.AppShareViewModel
import com.shy.basic.ui.vm.BaseViewModel

abstract class BaseFragment<B : ViewDataBinding, VM : BaseViewModel> : Fragment(), IBaseView {

    protected lateinit var mBinding: B
    protected lateinit var mViewModel: VM
    protected lateinit var mAppViewModel: AppShareViewModel
    protected lateinit var mActivity: AppCompatActivity

    private lateinit var mFragmentProvider: ViewModelProvider
    private lateinit var mActivityProvider: ViewModelProvider
    private lateinit var mApplicationProvider: ViewModelProvider

    protected lateinit var mLoadService: LoadService<*>

    /**
     * AndroidX 懒加载
     */
    private var isLoaded = false

    @LayoutRes
    protected abstract fun getLayoutId(): Int

    protected abstract fun initViewModel(): VM

    /**
     *  初始化操作
     */
    protected abstract fun initParams(savedInstanceState: Bundle?)
    protected abstract fun initLoadSir()
    protected abstract fun firstLoad()
    protected abstract fun lazyLoad()
    protected abstract fun interrupt()
    protected abstract fun initListener()
    protected abstract fun initObserver()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as AppCompatActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initParams(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        mBinding.lifecycleOwner = this
        mViewModel = initViewModel()
        lifecycle.addObserver(mViewModel)
        initLoadSir()
        if (this::mLoadService.isInitialized) {
            var parentView: ViewGroup? = mLoadService.loadLayout.parent as ViewGroup?
            if (parentView != null) {
                parentView.endViewTransition(mLoadService.loadLayout)
                parentView.removeView(mLoadService.loadLayout)
            }
            return mLoadService.loadLayout
        }
        var parentView: ViewGroup? = mBinding.root.parent as ViewGroup?
        if (parentView != null) {
            parentView.endViewTransition(mBinding.root)
            parentView.removeView(mBinding.root)
        }
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
        initObserver()
        initInternalObserver()
    }

    override fun onResume() {
        super.onResume()
        if (!isLoaded && !isHidden) {
            firstLoad()
            isLoaded = true
            return
        }
        if (isLoaded && !isHidden) {
            lazyLoad()
        }
    }

    override fun onPause() {
        super.onPause()
        interrupt()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    protected fun setLoadsir(view: View?) {
        mLoadService = LoadSir.getDefault().register(view) { onRetry() }
    }

    protected fun initInternalObserver() {
        mViewModel.mLoadingUIEvent.observeNullable(this) {
            showLoading()
        }

        mViewModel.mSuccessUIEvent.observeNullable(this) {
            showContent()
        }

        mViewModel.mEmptyUIEvent.observeNullable(this) {
            showEmpty()
        }

        mViewModel.mErrorUIEvent.observeNonNull(this) {
            showError()
        }
    }


    protected open fun <T : ViewModel?> getFragmentScopeViewModel(modelClass: Class<T>): T {
        if (!this::mFragmentProvider.isInitialized) {
            mFragmentProvider = ViewModelProvider(this)
        }
        return mFragmentProvider.get(modelClass)
    }

    protected fun <T : ViewModel> getActivityScopeViewModel(modelClass: Class<T>): T {
        mActivityProvider = ViewModelProvider(this)
        return mActivityProvider.get(modelClass)
    }

    protected fun <T : ViewModel> getApplicationScopeViewModel(modelClass: Class<T>): T {
        if (!this::mApplicationProvider.isInitialized) {
            mApplicationProvider = ViewModelProvider(
                BaseApplication.instance(),
                getAppFactory(mActivity)
            )
        }
        return mApplicationProvider.get(modelClass)
    }

    protected fun getAppFactory(activity: Activity): ViewModelProvider.Factory {
        checkActivity(this)
        val application = checkApplication(activity)
        return ViewModelProvider.AndroidViewModelFactory.getInstance(application)
    }

    protected fun checkActivity(fragment: Fragment) {
        if (fragment.activity == null) {
            throw java.lang.IllegalStateException("Can't create ViewModelProvider for detached fragment")
        }
    }

    protected fun checkApplication(activity: Activity): Application {
        return activity.application
            ?: throw IllegalStateException(
                "Your activity/fragment is not yet attached to "
                        + "Application. You can't request ViewModel before onCreate call."
            )
    }

    protected open fun toggleSoftInput() {
        val imm = mActivity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS)
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
}