package com.youssefshamass.core.di

import com.squareup.picasso.Picasso
import org.koin.dsl.module

val coreModule = module {
    single {
        Picasso.Builder(get())
            .build()
    }
}