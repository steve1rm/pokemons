package me.androidbox.pokemon.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.Reusable
import me.androidbox.pokemon.BuildConfig
import me.androidbox.pokemon.R
import me.androidbox.pokemon.data.service.PokemonService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named

@Module
class NetworkModule {

    @Reusable
    @Provides
    fun httpLoginInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()

        loggingInterceptor.level = if(BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        }
        else {
            HttpLoggingInterceptor.Level.NONE
        }

        return loggingInterceptor
    }

    @Reusable
    @Provides
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Named("BaseUrl")
    @Reusable
    @Provides
    fun provideBaseUrl(context: Context): String =
        context.getString(R.string.baseUrl)

    @Reusable
    @Provides
    fun provideRetrofit(@Named("BaseUrl") baseUrl: String, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
    }

    @Reusable
    @Provides
    fun provideTamboonService(retrofit: Retrofit): PokemonService =
        retrofit.create(PokemonService::class.java)

}