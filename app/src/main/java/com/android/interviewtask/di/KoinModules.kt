package com.android.interviewtask.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.android.interviewtask.rest.*
import com.android.interviewtask.ui.alertlist.AlertListViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {

    // provides the cards repository implementation
    fun provideWeatherRepo(weatherApi: WeatherApi): WeatherRepository = WeatherRepositoryImpl(weatherApi)

    // provide Gson object
    fun provideGson() = GsonBuilder().create()

    // provide logging interceptor
    fun provideLoggingInterceptor() =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    // provide okhttp client
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor) =
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()

    // providing the retrofit builder
    fun provideWeatherApi(okHttpClient: OkHttpClient, gson: Gson): WeatherApi =
        Retrofit.Builder()
            .baseUrl(WeatherApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
            .create(WeatherApi::class.java)

    single { provideGson() }
    single { provideLoggingInterceptor() }
    single { provideOkHttpClient(get()) }
    single { provideWeatherApi(get(), get()) }
    single { provideWeatherRepo(get()) }
}

val viewModelModule = module {
    viewModel {
        AlertListViewModel(get())
    }
}