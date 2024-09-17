package com.client.android.core_crypto_currency_data.di

import android.content.Context
import com.client.android.core_crypto_currency_data.BuildConfig
import com.client.android.core_crypto_currency_data.CryptoCurrencyInfoRepository
import com.client.android.core_crypto_currency_data.api.CryptoCurrencyInfoApi
import com.client.android.core_crypto_currency_data.repository.CryptoCurrencyInfoRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CryptoCurrencyDataDiModule {

    private const val TIMEOUT_IN_SECONDS = 30L

    @Singleton
    @Provides
    fun provideCryptoCurrencyInfoApi(retrofit: Retrofit) =
        retrofit.create(CryptoCurrencyInfoApi::class.java)

    @Singleton
    @Provides
    fun provideCryptoCurrencyInfoRepository(
        @ApplicationContext context: Context,
        cryptoCurrencyInfoApi: CryptoCurrencyInfoApi,
    ): CryptoCurrencyInfoRepository = CryptoCurrencyInfoRepositoryImpl(context, cryptoCurrencyInfoApi)

    @Singleton
    @Provides
    fun provideCryptoCurrencyInfoApiRetrofit(): Retrofit {
        return Retrofit.Builder()
            .client(buildOkHttpClient())
            .baseUrl(BuildConfig.CRYPTO_CURRENCY_INFO_API)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun buildOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
            .build()
    }
}
