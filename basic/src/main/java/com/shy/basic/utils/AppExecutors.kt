package com.shy.basic.utils

import android.os.Handler
import android.os.Looper
import android.util.Log
import java.util.concurrent.*
import java.util.concurrent.atomic.AtomicInteger
import kotlin.math.max
import kotlin.math.min

object AppExecutors {

    //按任务类型分配的池
    private val diskIO: Executor by lazy {
        Log.e("AppExecutors", "diskIO init------------")
        Executors.newSingleThreadExecutor()
    }
    private val networkIO: Executor by lazy {
        Log.e("AppExecutors", "networkIO init------------")
        Executors.newFixedThreadPool(3)
    }
    private val mainThread: Executor by lazy {
        Log.e("AppExecutors", "mainThread init------------")
        MainThreadExecutor()
    }

    //公用池
    private var THREAD_POOL_EXECUTOR: ExecutorService? = null

    private val CPU_COUNT = Runtime.getRuntime().availableProcessors()
    private val CORE_POOL_SIZE = max(2, min(CPU_COUNT - 1, 4))
    private val MAXIMUM_POOL_SIZE = CPU_COUNT * 2
    private const val KEEP_ALIVE_SECONDS = 60L
    private val sPoolWorkQueue: BlockingQueue<Runnable> = LinkedBlockingQueue(8)
    private val sThreadFactory: ThreadFactory = object : ThreadFactory {
        private var mCount: AtomicInteger = AtomicInteger(1)
        override fun newThread(r: Runnable?): Thread? {
            return Thread(r, "FlagTask #" + mCount.getAndIncrement())
        }
    }

    fun diskIO(): Executor {
        return diskIO
    }

    fun networkIO(): Executor {
        return networkIO
    }

    fun mainThread(): Executor {
        return mainThread
    }

    fun init() {
        val threadPoolExecutor: ThreadPoolExecutor = object : ThreadPoolExecutor(
            CORE_POOL_SIZE,
            MAXIMUM_POOL_SIZE,
            KEEP_ALIVE_SECONDS,
            TimeUnit.SECONDS,
            sPoolWorkQueue,
            sThreadFactory,
            RejectedHandler()
        ) {
            override fun execute(command: Runnable?) {
                super.execute(command)
            }
        }
        //允许核心线程空闲超时时被回收
        threadPoolExecutor.allowCoreThreadTimeOut(true)
        THREAD_POOL_EXECUTOR = threadPoolExecutor
        Log.e("AppExecutors", "THREAD_POOL_EXECUTOR init------------")
    }

    fun execute(command: Runnable) {
        if (THREAD_POOL_EXECUTOR == null){
            init()
        }
        THREAD_POOL_EXECUTOR!!.execute(command)
    }

    private class RejectedHandler : RejectedExecutionHandler {
        override fun rejectedExecution(r: Runnable?, executor: ThreadPoolExecutor?) {
        }
    }

    private class MainThreadExecutor : Executor {
        private val mainThreadHandler = Handler(Looper.getMainLooper())
        override fun execute(command: Runnable) {
            mainThreadHandler.post(command)
        }
    }
}