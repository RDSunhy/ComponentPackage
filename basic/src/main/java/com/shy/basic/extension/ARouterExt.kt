package com.shy.basic.extension

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.callback.NavigationCallback
import com.alibaba.android.arouter.launcher.ARouter

fun Activity.navigation(path: String, bundle: Bundle? = null) {
    ARouter.getInstance()
        .build(path)
        .with(bundle)
        .navigation(this)
}

fun Activity.navigation(path: String, bundle: Bundle? = null, callback: NavigationCallback) {
    ARouter.getInstance()
        .build(path)
        .with(bundle)
        .navigation(this, callback)
}

fun Fragment.navigation(path: String, bundle: Bundle? = null) {
    ARouter.getInstance()
        .build(path)
        .with(bundle)
        .navigation(activity)
}

fun Fragment.navigation(path: String, bundle: Bundle? = null, callback: NavigationCallback) {
    ARouter.getInstance()
        .build(path)
        .with(bundle)
        .navigation(activity, callback)
}

fun Activity.getFragment(path: String): Fragment {
    return ARouter.getInstance().build(path).navigation() as Fragment
}

fun Fragment.getFragment(path: String): Fragment {
    return ARouter.getInstance().build(path).navigation() as Fragment
}

fun getFragmentByARouter(path: String): Fragment {
    return ARouter.getInstance().build(path).navigation() as Fragment
}