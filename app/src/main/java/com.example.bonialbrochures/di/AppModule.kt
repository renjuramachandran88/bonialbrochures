package com.example.bonialbrochures.di

import android.content.Context
import com.example.bonialbrochures.BonialBrochureApp
import com.example.bonialbrochures.data.entity.BrochureContentJsonAdapter
import com.example.bonialbrochures.data.entity.ContentList
import com.example.bonialbrochures.data.remote.BrochureService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): BonialBrochureApp {
        return app as BonialBrochureApp
    }

    @Provides
    @Singleton
    fun provideContext(application: BonialBrochureApp): Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    fun providesOkHttpCache(context: Context): Cache {
        return Cache(context.cacheDir, 10 * 1024 * 1024)
    }

    @Provides
    @Singleton
    fun providesHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.NONE
        }
    }

    @Provides
    @Singleton
    fun providesOkHttpClient(
        cache: Cache, httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder().apply {
            addInterceptor(httpLoggingInterceptor)
            readTimeout(5, TimeUnit.SECONDS)
            connectTimeout(5, TimeUnit.SECONDS)
            cache(cache)
        }.build()
    }

    @Provides
    @Singleton
    fun providesRetrofit(
        okHttpClient: OkHttpClient,
    ): Retrofit {
        return Retrofit.Builder().apply {
            baseUrl("https://test-mobile-configuration-files.s3.eu-central-1.amazonaws.com")
            addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder().registerTypeAdapter(
                        ContentList::class.java,
                        BrochureContentJsonAdapter(Gson())
                    ).create()
                )
            )
            client(okHttpClient)
        }.build()
    }

    @Provides
    @Singleton
    fun provideBrochureApi(retrofit: Retrofit): BrochureService {
        return retrofit.create(BrochureService::class.java)
    }

}