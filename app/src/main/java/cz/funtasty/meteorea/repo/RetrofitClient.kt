package cz.funtasty.meteorea.repo

import android.util.Log
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

import cz.funtasty.meteorea.BuildConfig
import cz.funtasty.meteorea.Config

class RetrofitClient {
    companion object {
        @Volatile
        private var sRetrofit: Retrofit? = null


        fun <T> createService(service: Class<T>): T {
            return retrofit!!.create(service)
        }


        val retrofit: Retrofit?
            get() {
                if (sRetrofit == null) {
                    synchronized(NasaRepository::class.java) {
                        if (sRetrofit == null) {
                            sRetrofit = buildRetrofit()
                        }
                    }
                }
                return sRetrofit
            }


        private fun buildRetrofit(): Retrofit {
            val builder = Retrofit.Builder()
            builder.baseUrl(Config.NASA_API_URL)
            builder.client(buildClient())
            builder.addConverterFactory(createConverterFactory())
            builder.addCallAdapterFactory(CoroutineCallAdapterFactory())
            return builder.build()
        }


        private fun buildClient(): OkHttpClient {
            val builder = OkHttpClient.Builder()
            builder.connectTimeout(Config.SERVER_TIMEOUT, TimeUnit.MILLISECONDS)
            builder.readTimeout(Config.SERVER_TIMEOUT, TimeUnit.MILLISECONDS)
            builder.writeTimeout(Config.SERVER_TIMEOUT, TimeUnit.MILLISECONDS)
            builder.addNetworkInterceptor(createLoggingInterceptor())
            return builder.build()
        }


        private fun createLoggingInterceptor(): Interceptor {
            val logger = HttpLoggingInterceptor.Logger { message -> Log.d("intercepter", message) }
            val interceptor = HttpLoggingInterceptor(logger)
            interceptor.level = if (BuildConfig.LOGS) HttpLoggingInterceptor.Level.BASIC else HttpLoggingInterceptor.Level.NONE
            return interceptor
        }


        /**
         * Enable Retrofit to convert data from JSON format to Java object
         */
        private fun createConverterFactory(): Converter.Factory {
            val builder = GsonBuilder()
            val gson = builder.create()
            return GsonConverterFactory.create(gson)
        }
    }
}