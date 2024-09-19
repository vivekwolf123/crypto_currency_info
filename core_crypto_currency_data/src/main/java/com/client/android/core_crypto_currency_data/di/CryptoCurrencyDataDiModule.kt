package com.client.android.core_crypto_currency_data.di

import android.content.Context
import androidx.room.Room
import com.client.android.core_crypto_currency_data.BuildConfig
import com.client.android.core_crypto_currency_data.CryptoCurrencyDataRepository
import com.client.android.core_crypto_currency_data.api.CryptoCurrencyDataApi
import com.client.android.core_crypto_currency_data.cache.CryptoCurrencyDataDao
import com.client.android.core_crypto_currency_data.cache.CryptoCurrencyDatabase
import com.client.android.core_crypto_currency_data.repository.CryptoCurrencyDataRepositoryImpl
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

    @Provides
    @Singleton
    fun provideCryptoCurrencyInfoDao(cryptoCurrencyDatabase: CryptoCurrencyDatabase) =
        cryptoCurrencyDatabase.cryptoCurrencyDataDao()

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): CryptoCurrencyDatabase {
        return Room.databaseBuilder(
            appContext, CryptoCurrencyDatabase::class.java, "crypto_currency_info_database"
        ).build()
    }


    @Singleton
    @Provides
    fun provideCryptoCurrencyDataApi(retrofit: Retrofit) =
        retrofit.create(CryptoCurrencyDataApi::class.java)

    @Singleton
    @Provides
    fun provideCryptoCurrencyDataRepository(
        @ApplicationContext context: Context,
        cryptoCurrencyDataApi: CryptoCurrencyDataApi,
        cryptoCurrencyDataDao: CryptoCurrencyDataDao
    ): CryptoCurrencyDataRepository =
        CryptoCurrencyDataRepositoryImpl(context, cryptoCurrencyDataApi, cryptoCurrencyDataDao)

    @Singleton
    @Provides
    fun provideCryptoCurrencyDataApiRetrofit(): Retrofit {
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
