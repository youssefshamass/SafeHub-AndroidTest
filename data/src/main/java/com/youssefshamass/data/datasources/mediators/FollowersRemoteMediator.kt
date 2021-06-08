package com.youssefshamass.data.datasources.mediators

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.youssefshamass.core.database.DatabaseTransactionRunner
import com.youssefshamass.core.errors.NotFoundError
import com.youssefshamass.data.datasources.local.FollowerDAO
import com.youssefshamass.data.datasources.local.UserDAO
import com.youssefshamass.data.datasources.remote.UserService
import com.youssefshamass.data.entities.local.Follower
import com.youssefshamass.data.entities.mappers.GithubUserHeaderToFollower

@ExperimentalPagingApi
class FollowersRemoteMediator(
    private val forUserId: Int,
    private val transactionRunner: DatabaseTransactionRunner,
    private val userDao: UserDAO,
    private val followersDao: FollowerDAO,
    private val userService: UserService,
    private val mapper: GithubUserHeaderToFollower,
) : RemoteMediator<Int, Follower>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Follower>
    ): MediatorResult {
        try {
            val user = userDao.getUser(forUserId)

            user?.let {
                val loadKey = when (loadType) {
                    LoadType.REFRESH -> null
                    LoadType.PREPEND -> null
                    LoadType.APPEND -> {
                        state.pages.size + 1
                    }
                }

                if(loadType == LoadType.PREPEND)
                    return MediatorResult.Success(endOfPaginationReached = true)

                val response = userService.getFollowers(
                    user.loginName,
                    page = loadKey ?: 1,
                    pageSize = state.config.pageSize
                )

                transactionRunner {
                    if (loadType == LoadType.REFRESH) {
                        followersDao.clear(forUserId)
                    }

                    followersDao.insert(*mapper.collection(response).toTypedArray())
                }

                return MediatorResult.Success(endOfPaginationReached = response.size < state.config.pageSize)
            } ?: throw NotFoundError()
        } catch (exception: Exception) {
            return MediatorResult.Error(exception)
        }
    }
}