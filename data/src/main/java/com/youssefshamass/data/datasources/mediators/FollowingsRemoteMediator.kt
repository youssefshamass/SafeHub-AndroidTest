package com.youssefshamass.data.datasources.mediators

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.youssefshamass.core.database.DatabaseTransactionRunner
import com.youssefshamass.core.errors.NotFoundError
import com.youssefshamass.data.datasources.local.FollowingDAO
import com.youssefshamass.data.datasources.local.UserDAO
import com.youssefshamass.data.datasources.remote.UserService
import com.youssefshamass.data.entities.local.Following
import com.youssefshamass.data.entities.mappers.GithubUserHeaderToFollowing

@ExperimentalPagingApi
class FollowingsRemoteMediator(
    private val forUserId: Int,
    private val transactionRunner: DatabaseTransactionRunner,
    private val userDao: UserDAO,
    private val followingsDao: FollowingDAO,
    private val userService: UserService,
    private val mapper: GithubUserHeaderToFollowing,
) : RemoteMediator<Int, Following>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Following>
    ): MediatorResult {
        try {
            val user = userDao.getUser(forUserId)

            user?.let {
                val loadKey = when (loadType) {
                    LoadType.REFRESH -> null
                    LoadType.PREPEND -> null
                    LoadType.APPEND -> {
                        val lastItem = state.anchorPosition ?: 0

                        if (lastItem > 0) (lastItem / state.config.pageSize) + 1 else null
                    }
                }

                if (loadType == LoadType.PREPEND)
                    return MediatorResult.Success(endOfPaginationReached = true)

                val response = userService.getFollowings(
                    user.loginName,
                    page = loadKey ?: 1,
                    pageSize = state.config.pageSize
                )

                transactionRunner {
                    if (loadType == LoadType.REFRESH) {
                        followingsDao.clear(forUserId)
                    }

                    followingsDao.insert(*mapper.collection(response).toTypedArray())
                }

                return MediatorResult.Success(endOfPaginationReached = response.size < state.config.pageSize)
            } ?: throw NotFoundError()
        } catch (exception: Exception) {
            return MediatorResult.Error(exception)
        }
    }
}