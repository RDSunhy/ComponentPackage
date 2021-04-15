package com.shy.study

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.SkinAppCompatDelegateImpl
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.launcher.ARouter
import com.shy.basic.extension.getFragment
import com.shy.basic.extension.loadFragments
import com.shy.basic.extension.showHideFragment
import com.shy.basic.ui.page.BaseActivity
import com.shy.study.databinding.ActivityMainBinding
import com.shy.study.home.ServiceFactory
import com.shy.study.repository.db.AppDataBase
import skin.support.SkinCompatManager

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    private val mHomeFragment = getFragment("/home/fragment")
    private val mSquareFragment =  getFragment("/square/fragment")
    private val mMineFragment = getFragment("/mine/fragment")

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initViewModel(): MainViewModel {
        return getActivityScopeViewModel(MainViewModel::class.java)
    }

    override fun initParams(savedInstanceState: Bundle?) {
    }

    override fun initView() {
        loadFragments(R.id.flHost, 0, *mutableListOf<Fragment>(
            mHomeFragment,
            mSquareFragment,
            mMineFragment
        ).toTypedArray())
    }

    override fun initData() {
    }

    override fun initListener() {
        mBinding.bottomView.setOnNavigationItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                R.id.home ->{
                    showHideFragment(mHomeFragment)
                }
                R.id.square ->{
                    showHideFragment(mSquareFragment)
                }
                R.id.mine ->{
                    showHideFragment(mMineFragment)
                }
            }
            true
        }
    }

    override fun initObserver() {
    }
}