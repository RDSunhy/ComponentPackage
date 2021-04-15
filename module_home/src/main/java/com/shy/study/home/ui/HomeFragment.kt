package com.shy.study.home.ui

import android.os.Bundle

import android.widget.Toast
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.shy.basic.extension.navigation
import com.shy.basic.ui.page.BaseFragment
import com.shy.study.home.R
import com.shy.study.home.ServiceFactory
import com.shy.study.home.databinding.HomeFragmentHomeBinding
import skin.support.SkinCompatManager

@Route(path = "/home/fragment")
class HomeFragment : BaseFragment<HomeFragmentHomeBinding, HomeViewModel>() {
    override fun getLayoutId(): Int {
        return R.layout.home_fragment_home
    }

    override fun initViewModel(): HomeViewModel {
        return getFragmentScopeViewModel(HomeViewModel::class.java)
    }

    override fun initParams(savedInstanceState: Bundle?) {

    }

    override fun initLoadSir() {
    }

    override fun firstLoad() {
        mBinding.bnHome.setOnClickListener {
            //ARouter.getInstance().build("/home/main").navigation()
            navigation("/home/main")
        }
        mBinding.bnHomeA.setOnClickListener {
            //ARouter.getInstance().build("/home/test").navigation()
            navigation("/home/test")
        }
        mBinding.bnSquare.setOnClickListener {
            //ARouter.getInstance().build("/square/main").navigation()
            navigation("/square/main")
        }
        mBinding.bnMine.setOnClickListener {
            //ARouter.getInstance().build("/mine/main").navigation()
            navigation("/mine/main")
        }

        mBinding.bnSkin.setOnClickListener {
            SkinCompatManager.getInstance().loadSkin("test", SkinCompatManager.SKIN_LOADER_STRATEGY_BUILD_IN)
            Toast.makeText(
                mActivity,
                "换肤",
                Toast.LENGTH_SHORT
            ).show()
        }
        mBinding.bnReset.setOnClickListener {
            SkinCompatManager.getInstance().restoreDefaultTheme()
            Toast.makeText(
                mActivity,
                "恢复",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun lazyLoad() {
    }

    override fun interrupt() {
    }

    override fun initListener() {
    }

    override fun initObserver() {
    }

}