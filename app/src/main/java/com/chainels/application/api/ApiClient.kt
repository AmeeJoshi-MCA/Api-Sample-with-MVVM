package com.chainels.application.api

import android.content.Context
import com.chainels.application.utils.CheckValidation
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {

    private var context : Context? = null

    fun init(context : Context) { ApiClient.context = context   }

    private fun retrofitService(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient(context!!))
            .baseUrl(AppConstant.BASE_URL)
            .build()
    }

    private fun okHttpClient(context : Context): OkHttpClient {
        val cashSize: Long = (5 * 1024 * 1024).toLong()
        val cache = Cache(context.cacheDir, cashSize)
        return OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor { chain: Interceptor.Chain ->
                var requst = chain.request()
                requst = if (CheckValidation.isConnected(context)) {
                    requst
                        .newBuilder()
                        .header("Cache-Control", "public, max-age=" + 5)
                        .build()
                } else {
                    requst
                        .newBuilder()
                        .header(
                            "Cache-Control",
                            "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7
                        )
                        .build()
                }
                chain.proceed(requst)
            }
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .build()
    }

    val instance: ApiService by lazy {
        retrofitService().create(ApiService::class.java)
    }

}