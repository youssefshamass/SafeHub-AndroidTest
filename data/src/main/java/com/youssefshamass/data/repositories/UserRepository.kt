package com.youssefshamass.data.repositories

import com.youssefshamass.core.database.DatabaseTransactionRunner
import com.youssefshamass.core.errors.NotFoundError
import com.youssefshamass.data.datasources.local.UserDAO
import com.youssefshamass.data.datasources.remote.UserService
import com.youssefshamass.data.entities.local.User
import com.youssefshamass.data.entities.mappers.GithubUserToUser
import retrofit2.HttpException

interface IUserRepository {
    suspend fun searchUser(loginName: String): User

    suspend fun refreshUser(loginName: String)
}

class UserRepository(
    private val transactionRunner: DatabaseTransactionRunner,
    private val userService: UserService,
    private val userDao: UserDAO,
    private val userMapper: GithubUserToUser,
) : IUserRepository {
    override suspend fun searchUser(loginName: String): User {
        var persistedUser = userDao.getUser(loginName)

        if (persistedUser == null) {
            try {
                val remoteUser = userService.getUserDetails(loginName)

                transactionRunner{
                    userDao.insert(userMapper.map(remoteUser))
                    persistedUser = userDao.getUser(loginName)
                }
            } catch (exception: Exception) {
                if (exception is HttpException && exception.code() == 404)
                    throw NotFoundError()
            }
        }

        return persistedUser!!
    }

    override suspend fun refreshUser(loginName: String) {
        val githubUser = userService.getUserDetails(loginName)

        transactionRunner.invoke {
            userDao.insert(userMapper.map(githubUser))
        }
    }
}