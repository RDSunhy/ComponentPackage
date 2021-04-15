package com.shy.flag.data.network

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.shy.basic.utils.AppExecutors
import com.shy.study.repository.network.ApiEmptyResponse
import com.shy.study.repository.network.ApiErrorResponse
import com.shy.study.repository.network.ApiResponse
import com.shy.study.repository.network.ApiSuccessResponse
import com.shy.study.repository.network.adapter.Resource

/**
 * 仿照 google gitub浏览器demo 实现
 * 将api请求回来的数据转换为LiveData<Resource<ResultType>>
 *
 * @param <ResultType> 本地存储的数据类型
 * @param <RequestType> Api请求的数据类型
 * @return LiveData<Resource<ResultType>>
 */
abstract class TransformationsLiveData<ResultType, RequestType>
@MainThread constructor() {

    private val result = MediatorLiveData<Resource<ResultType>>()

    init {
        result.value = Resource.loading(null)
        val dbSource = getByLocal()
        result.addSource(dbSource) { data ->
            result.removeSource(dbSource)
            if (shouldFetch(data)) {
                fetchFromNetwork(dbSource)
            } else {
                result.addSource(dbSource) { newData ->
                    setValue(
                        Resource.success(
                            newData
                        )
                    )
                }
            }
        }
    }

    @MainThread
    private fun setValue(newValue: Resource<ResultType>) {
        if (result.value != newValue) {
            result.value = newValue
        }
    }

    private fun fetchFromNetwork(dbSource: LiveData<ResultType>) {
        val apiResponse = getByApi()
        result.addSource(dbSource) { newData ->
            setValue(Resource.loading(newData))
        }
        result.addSource(apiResponse) { response ->
            result.removeSource(apiResponse)
            result.removeSource(dbSource)
            when (response) {
                is ApiSuccessResponse -> {
                    AppExecutors.diskIO().execute {
                        saveApiData(processResponse(response))
                        AppExecutors.mainThread().execute {
                            result.addSource(getByLocal()) { newData ->
                                setValue(Resource.success(newData))
                            }
                        }
                    }
                }
                is ApiEmptyResponse -> {
                    AppExecutors.mainThread().execute {
                        result.addSource(getByLocal()) { newData ->
                            setValue(Resource.success(newData))
                        }
                    }
                }
                is ApiErrorResponse -> {
                    onFetchFailed()
                    result.addSource(dbSource) { newData ->
                        setValue(
                            Resource.error(response.errorMessage, newData)
                        )
                    }
                }
            }
        }
    }

    protected open fun onFetchFailed() {}

    fun asLiveData() = result as MutableLiveData<Resource<ResultType>>

    @WorkerThread
    protected open fun processResponse(response: ApiSuccessResponse<RequestType>) = response.body

    @WorkerThread
    protected abstract fun saveApiData(item: RequestType)

    @MainThread
    protected abstract fun shouldFetch(data: ResultType?): Boolean

    @MainThread
    protected abstract fun getByLocal(): LiveData<ResultType>

    @MainThread
    protected abstract fun getByApi(): LiveData<ApiResponse<RequestType>>
}
