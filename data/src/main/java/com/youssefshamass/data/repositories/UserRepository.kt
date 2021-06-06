package com.youssefshamass.data.repositories

import com.youssefshamass.core.database.DatabaseTransactionRunner
import com.youssefshamass.data.datasources.local.UserDAO
import com.youssefshamass.data.datasources.remote.UserService
import com.youssefshamass.data.entities.mappers.GithubUserToUser

interface IUserRepository {
    suspend fun refreshUser(loginName: String)
}

class UserRepository(
    private val transactionRunner: DatabaseTransactionRunner,
    private val userService: UserService,
    private val userDao: UserDAO,
    private val userMapper: GithubUserToUser,
) : IUserRepository {
    override suspend fun refreshUser(loginName: String) {
        val githubUser = userService.getUserDetails(loginName)

        transactionRunner.invoke {
            userDao.insert(userMapper.map(githubUser))
        }
    }
}