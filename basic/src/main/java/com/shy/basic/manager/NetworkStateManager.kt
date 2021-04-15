package com.shy.basic.manager

import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.blankj.utilcode.util.Utils
import com.shy.basic.extension.DefaultLifecycObserver
import com.shy.basic.receive.NetworkStateReceive

class NetworkStateManager: DefaultLifecycObserver {

    companion object {
        private var instance: NetworkStateManager? = null
            get() {
                if (field == null) {
                    field = NetworkStateManager()
                }
                return field
            }
        fun instance(): NetworkStateManager{
            return instance!!
        }
    }

    private val mNetworkStateReceive: NetworkStateReceive by lazy { NetworkStateReceive() }

    override fun onAny(owner: LifecycleOwner, event: Lifecycle.Event) {

    }

    override fun onCreate() {

    }

    override fun onStart() {

    }

    override fun onResume() {
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        Utils.getApp().applicationContext.registerReceiver(mNetworkStateReceive, filter)
    }

    override fun onPause() {
        Utils.getApp().applicationContext.unregisterReceiver(mNetworkStateReceive)
    }

    override fun onStop() {

    }

    override fun onDestroy() {

    }
}