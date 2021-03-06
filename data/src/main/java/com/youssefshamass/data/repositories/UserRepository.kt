package com.youssefshamass.data.repositories

import androidx.paging.PagingSource
import com.youssefshamass.core.database.DatabaseTransactionRunner
import com.youssefshamass.core.errors.NotFoundError
import com.youssefshamass.core.errors.RateLimitedError
import com.youssefshamass.data.datasources.local.FollowerDAO
import com.youssefshamass.data.datasources.local.FollowingDAO
import com.youssefshamass.data.datasources.local.UserDAO
import com.youssefshamass.data.datasources.remote.UserService
import com.youssefshamass.data.entities.local.Follower
import com.youssefshamass.data.entities.local.Following
import com.youssefshamass.data.entities.local.User
import com.youssefshamass.data.entities.mappers.GithubUserToUser
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException

interface IUserRepository {
    fun observeUser(userId: Int): Flow<User?>

    fun observePreviousMatches(): Flow<List<User>>

    @Throws(NotFoundError::class, RateLimitedError::class)
    suspend fun searchUser(loginName: String): User

    suspend fun refreshUser(userId: Int)

    fun getFollowersPagingSource(userId: Int): PagingSource<Int, Follower>

    fun getFollowingsPagingSource(userId: Int): PagingSource<Int, Following>
}

class UserRepository(
    private val transactionRunner: DatabaseTransactionRunner,
    private val userService: UserService,
    private val userDao: UserDAO,
    private val followerDAO: FollowerDAO,
    private val followingDAO: FollowingDAO,
    private val userMapper: GithubUserToUser,
) : IUserRepository {
    override fun observeUser(userId: Int): Flow<User?> =
        userDao.observeUser(userId)

    override fun observePreviousMatches(): Flow<List<User>> =
        userDao.getMatches()

    override suspend fun searchUser(loginName: String): User {
        if (loginName.isEmpty())
            throw IllegalArgumentException()

        var persistedUser = userDao.getUser(loginName)

        if (persistedUser == null) {
            try {
                val remoteUser = userService.getUserDetails(loginName)

                transactionRunner {
                    userDao.insert(userMapper.map(remoteUser))
                    persistedUser = userDao.getUser(loginName)
                }
            } catch (exception: Exception) {
                if (exception is HttpException)
                    when (exception.code()) {
                        404 -> throw NotFoundError()
                        403 -> throw RateLimitedError()
                    }
                throw exception
            }
        }

        return persistedUser!!
    }

    override suspend fun refreshUser(userId: Int) {
        userDao.getUser(userId)?.let {
            val githubUser = userService.getUserDetails(it.loginName)

            transactionRunner {
                userDao.insert(userMapper.map(githubUser))
            }
        }
    }

    override fun getFollowersPagingSource(userId: Int): PagingSource<Int, Follower> =
        followerDAO.paging(userId)

    override fun getFollowingsPagingSource(userId: Int): PagingSource<Int, Following> =
        followingDAO.paging(userId)
}