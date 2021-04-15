package com.shy.basic.ui.vm

import android.app.Application
import androidx.lifecycle.*
import com.shy.basic.extension.DefaultLifecycObserver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel(), DefaultLifecycObserver,
    IViewModel {

    lateinit var application: Application
    private lateinit var lifcycleOwner: LifecycleOwner

    // show loading page
    var mLoadingUIEvent = MutableLiveData<Boolean>()

    // show success page
    var mSuccessUIEvent = MutableLiveData<Boolean>()

    // show empty page
    var mEmptyUIEvent = MutableLiveData<Boolean>()

    // show error page
    var mErrorUIEvent = MutableLiveData<Boolean>()


    /**
     * 请求api 获取数据
     */

    /**
     * 请求api 获取数据 存入room
     */

    /**
     * 优先从本地获取数据 不符合条件则从网络获取
     */

    /**
     * 在主线程中执行一个协程
     */
    protected fun launchOnUI(block: suspend CoroutineScope.() -> Unit): Job {
        return viewModelScope.launch(Dispatchers.Main) { block() }
    }

    /**
     * 在IO线程中执行一个协程
     */
    protected fun launchOnIO(block: suspend CoroutineScope.() -> Unit): Job {
        return viewModelScope.launch(Dispatchers.IO) { block() }
    }

    override fun showLoading(isShow: Boolean) {
        mLoadingUIEvent.postValue(isShow)
    }

    override fun showEmpty(isShow: Boolean) {
        mEmptyUIEvent.postValue(isShow)
    }

    override fun showError(isShow: Boolean) {
        mErrorUIEvent.postValue(isShow)
    }

    override fun onAny(owner: LifecycleOwner, event: Lifecycle.Event) {
        this.lifcycleOwner = owner
    }

    override fun onCreate() {

    }

    override fun onStart() {

    }

    override fun onResume() {

    }

    override fun onPause() {

    }

    override fun onStop() {

    }

    override fun onDestroy() {
        onCleared()
    }
}
