package com.youssefshamass.domain.di

import androidx.paging.ExperimentalPagingApi
import com.youssefshamass.domain.mapper.FollowerToUserHeader
import com.youssefshamass.domain.mapper.FollowingToUserHeader
import com.youssefshamass.domain.mapper.LocalUserToDomainUser
import com.youssefshamass.domain.users.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.core.component.KoinApiExtension
import org.koin.dsl.module

@OptIn(KoinApiExtension::class)
@ExperimentalCoroutinesApi
@ExperimentalPagingApi
val domainModule = module {
    factory { LocalUserToDomainUser() }
    factory { FollowerToUserHeader() }
    factory { FollowingToUserHeader() }

    factory { ObservePreviousMatches(get(), get()) }
    factory { ObserveUser(get(), get()) }
    factory { RefreshUser(get()) }
    factory { SearchUser(get(), get()) }
    factory { PaginateFollowers(get(), get()) }
    factory { PaginateFollowings(get(), get()) }
}