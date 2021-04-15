package com.shy.study.repository.network.interceptor

import android.os.Build
import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.util.*
import kotlin.jvm.Throws

class RequestInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request().newBuilder()
            .addHeader("device-id", uniquePseudoID)
            .build();
        return chain.proceed(request)
    }

    // Go ahead and return the serial for api => 9
    private val uniquePseudoID: String
        private get() {
            val m_szDevIDShort =
                "35" + Build.BOARD.length % 10 + Build.BRAND.length % 10 + Build.CPU_ABI.length % 10 + Build.DEVICE.length % 10 + Build.MANUFACTURER.length % 10 + Build.MODEL.length % 10 + Build.PRODUCT.length % 10
            var serial: String? = null
            try {
                serial = Build::class.java.getField("SERIAL")[null].toString()

                // Go ahead and return the serial for api => 9
                return UUID(
                    m_szDevIDShort.hashCode().toLong(),
                    serial.hashCode().toLong()
                ).toString()
            } catch (exception: Exception) {
                serial = "serial"
            }
            return UUID(m_szDevIDShort.hashCode().toLong(), serial.hashCode().toLong()).toString()
        }
}