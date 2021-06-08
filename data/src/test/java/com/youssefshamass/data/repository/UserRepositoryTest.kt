package com.youssefshamass.data.repository

import com.nhaarman.mockitokotlin2.*
import com.youssefshamass.core.database.TestTransactionRunner
import com.youssefshamass.core.errors.NotFoundError
import com.youssefshamass.core.errors.RateLimitedError
import com.youssefshamass.data.datasources.local.UserDAO
import com.youssefshamass.data.datasources.remote.UserService
import com.youssefshamass.data.entities.local.User
import com.youssefshamass.data.entities.mappers.GithubUserToUser
import com.youssefshamass.data.repositories.IUserRepository
import com.youssefshamass.data.repositories.UserRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import retrofit2.HttpException

const val LOGIN_NAME = "test"

class UserRepositoryTest {
    private val userService = mock<UserService>()
    private val userDao = mock<UserDAO>()
    private val mapper = mock<GithubUserToUser>()

    private lateinit var repository: IUserRepository
    private lateinit var user: User

    @Before
    fun setup() {
        repository = UserRepository(
            TestTransactionRunner, userService, userDao, mock(), mock(), mapper
        )

        user = User(
            1,
            "youssefshamass",
            "http://example.com",
            "Youssef shamass",
            "-",
            "Damascus - Syria",
            "Bio",
            0,
            0
        )
    }

    @Test(expected = IllegalArgumentException::class)
    fun `when searching with empty query exception should be thrown`(): Unit = runBlocking {
        repository.searchUser("")
    }

    @Test
    fun `when searching for cached user then the persisted version should be returned`(): Unit =
        runBlocking {
            whenever(userDao.getUser(loginName = anyString())).thenReturn(user)

            val returnValue = repository.searchUser(LOGIN_NAME)

            Assert.assertEquals(returnValue, user)
            verify(userService, times(0)).getUserDetails(any())
            verify(userDao, times(1)).getUser(loginName = any())
        }

    @Test(expected = NotFoundError::class)
    fun `when searching with incorrect username then not found exception should be thrown`(): Unit =
        runBlocking {
            whenever(userDao.getUser(loginName = anyString())).thenReturn(null)
            val httpException = mock<HttpException>()
            whenever(httpException.code()).thenReturn(404)
            whenever(userService.getUserDetails(anyString())).thenThrow(httpException)

            repository.searchUser(LOGIN_NAME)
        }

    @Test(expected = RateLimitedError::class)
    fun `when throttled then rate limit exception should be thrown`(): Unit = runBlocking {
        val httpException = mock<HttpException>()
        whenever(httpException.code()).thenReturn(403)
        whenever(userService.getUserDetails(anyString())).thenThrow(httpException)

        repository.searchUser(LOGIN_NAME)
    }

    @Test
    fun `when searching for new user then its persisted`(): Unit = runBlocking {
        whenever(userDao.getUser(anyString()))
            .thenReturn(null)
            .thenReturn(user)

        val returnValue = repository.searchUser(LOGIN_NAME)

        Assert.assertEquals(returnValue, user)
        verify(userDao, times(2)).getUser(anyString())
        verify(userService, times(1)).getUserDetails(anyString())
    }

    @Test
    fun `when refresh user new user should be persisted`(): Unit = runBlocking {
        whenever(userDao.getUser(userId = any())).thenReturn(user)
        whenever(userDao.insert(any())).thenReturn(0)
        whenever(userService.getUserDetails(any())).thenReturn(
            com.youssefshamass.data.entities.remote.User(
                0,
                "youssef",
                "http://example.com",
                null,
                null,
                null,
                null,
                0,
                0
            )
        )
        whenever(mapper.map(any())).thenReturn(user)

        repository.refreshUser(1)

        verify(userDao, times(1)).insert(any())
    }
}