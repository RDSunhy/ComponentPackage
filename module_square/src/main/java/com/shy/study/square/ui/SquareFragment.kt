package com.shy.study.square.ui

import android.os.Bundle
import android.widget.Toast
import com.alibaba.android.arouter.facade.annotation.Route
import com.shy.basic.ui.page.BaseFragment
import com.shy.study.home.ServiceFactory
import com.shy.study.square.R
import com.shy.study.square.databinding.SquareFragmentHomeBinding

@Route(path = "/square/fragment")
class SquareFragment : BaseFragment<SquareFragmentHomeBinding, SquareViewModel>() {
    override fun getLayoutId(): Int {
        return R.layout.square_fragment_home
    }

    override fun initViewModel(): SquareViewModel {
        return getFragmentScopeViewModel(SquareViewModel::class.java)
    }

    override fun initParams(savedInstanceState: Bundle?) {

    }

    override fun initLoadSir() {
    }

    override fun firstLoad() {
    }

    override fun lazyLoad() {
    }

    override fun interrupt() {
    }

    override fun initListener() {
        mBinding.bnHomeB.setOnClickListener {
            Toast.makeText(
                mActivity,
                ServiceFactory.instance.getHomeService().getHomeModuleMessage(),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun initObserver() {
    }

}