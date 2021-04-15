package com.shy.study.mine.ui

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.facade.annotation.Route
import com.shy.basic.ui.page.BaseFragment
import com.shy.study.mine.R
import com.shy.study.mine.databinding.MineFragmentHomeBinding
import com.shy.study.repository.network.adapter.Status

@Route(path = "/mine/fragment")
class MineFragment : BaseFragment<MineFragmentHomeBinding, MineViewModel>() {
    override fun getLayoutId(): Int {
        return R.layout.mine_fragment_home
    }

    override fun initViewModel(): MineViewModel {
        return getFragmentScopeViewModel(MineViewModel::class.java)
    }

    override fun initParams(savedInstanceState: Bundle?) {

    }

    override fun initLoadSir() {
    }

    override fun firstLoad() {
        mBinding.vm = mViewModel
    }

    override fun lazyLoad() {
    }

    override fun interrupt() {
    }

    override fun initListener() {
        mBinding.bnHttp.setOnClickListener {
            mViewModel.getBanner()
        }
    }

    override fun initObserver() {
        mViewModel.banner.observe(this, Observer {
            if (it.status == Status.SUCCESS){
                mBinding.tvTag.text = it.message
            }
        })
    }

}