package com.youssefshamass.data.di

import androidx.paging.ExperimentalPagingApi
import androidx.room.Room
import com.youssefshamass.core.database.DatabaseTransactionRunner
import com.youssefshamass.data.datasources.mediators.FollowersRemoteMediator
import com.youssefshamass.data.datasources.mediators.FollowingsRemoteMediator
import com.youssefshamass.data.datasources.remote.UserService
import com.youssefshamass.data.db.ApplicationDatabase
import com.youssefshamass.data.db.RoomTransactionRunner
import com.youssefshamass.data.entities.mappers.GithubUserHeaderToFollower
import com.youssefshamass.data.entities.mappers.GithubUserHeaderToFollowing
import com.youssefshamass.data.entities.mappers.GithubUserToUser
import com.youssefshamass.data.repositories.IUserRepository
import com.youssefshamass.data.repositories.UserRepository
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module
import retrofit2.Retrofit

@ExperimentalPagingApi
val dataModule = module {
    single {
        Room.databaseBuilder(
            get(),
            ApplicationDatabase::class.java,
            ApplicationDatabase.DATABASE_NAME
        ).build()
    }

    single {
        val retrofit: Retrofit = get()
        retrofit.create(UserService::class.java)
    }

    single {
        val db = get<ApplicationDatabase>()
        db.users()
    }

    single {
        val db = get<ApplicationDatabase>()
        db.followers()
    }

    single {
        val db = get<ApplicationDatabase>()
        db.followings()
    }

    factory<DatabaseTransactionRunner> {
        RoomTransactionRunner(get())
    }

    factory {
        GithubUserToUser()
    }

    factory { (userId: Int) ->
        GithubUserHeaderToFollowing(userId)
    }

    factory { (userId: Int) ->
        GithubUserHeaderToFollower(userId)
    }

    factory { (userId: Int) ->
        FollowersRemoteMediator(userId, get(), get(), get(), get(), get {
            parametersOf(userId)
        })
    }

    factory { (userId: Int) ->
        FollowingsRemoteMediator(userId, get(), get(), get(), get(), get {
            parametersOf(userId)
        })
    }

    single<IUserRepository> {
        UserRepository(get(), get(), get(), get(), get(), get())
    }
}