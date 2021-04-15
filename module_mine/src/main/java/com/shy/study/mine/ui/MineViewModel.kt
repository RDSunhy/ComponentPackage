package com.shy.study.mine.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.shy.basic.ui.vm.BaseViewModel
import com.shy.study.mine.repository.MineRepo

class MineViewModel: BaseViewModel() {
    var mineRepo = MineRepo()

    private var _banner = MutableLiveData<Boolean>()
    var banner = _banner.switchMap {
        mineRepo.getBanner()
    }

    fun getBanner(){
        _banner.value = true
    }
}