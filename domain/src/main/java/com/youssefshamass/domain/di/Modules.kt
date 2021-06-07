package com.youssefshamass.domain.di

import com.youssefshamass.domain.users.ObservePreviousMatches
import com.youssefshamass.domain.users.ObserveUser
import com.youssefshamass.domain.users.RefreshUser
import com.youssefshamass.domain.users.SearchUser
import org.koin.dsl.module

val domainModule = module {
    factory { ObservePreviousMatches(get()) }
    factory { ObserveUser(get()) }
    factory { RefreshUser(get()) }
    factory { SearchUser(get()) }
}