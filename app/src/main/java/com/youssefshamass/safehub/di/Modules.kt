package com.youssefshamass.safehub.di

import com.google.gson.*
import com.youssefshamass.safehub.BuildConfig
import com.youssefshamass.safehub.presentation.details.UserDetailsViewModel
import com.youssefshamass.safehub.presentation.home.HomeViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single {
        OkHttpClient().newBuilder()
            .apply {
                addInterceptor {
                    val request = it.request()
                        .newBuilder()
                        .addHeader("Accept", "application/vnd.github.v3+json")
                        .build()

                    it.proceed(request)
                }

                if (BuildConfig.DEBUG)
                    addInterceptor(HttpLoggingInterceptor().also {
                        it.setLevel(HttpLoggingInterceptor.Level.BODY)
                    })
            }
            .build()
    }

    single<Retrofit> {
        Retrofit.Builder()
            .client(get())
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create(get()))
            .build()
    }

    single<Gson> {
        GsonBuilder()
            .create()
    }
}

val viewModelModule = module {
    viewModel {
        HomeViewModel(get(), get())
    }

    viewModel { (userId: Int) ->
        UserDetailsViewModel(userId, get(), get())
    }
}