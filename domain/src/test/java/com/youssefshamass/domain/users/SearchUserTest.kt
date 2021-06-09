package com.youssefshamass.domain.users

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.youssefshamass.core.data.base.InvokeStatus
import com.youssefshamass.core.errors.NotFoundError
import com.youssefshamass.core_test.CoroutineTestRule
import com.youssefshamass.data.entities.local.User
import com.youssefshamass.data.repositories.IUserRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.lang.IllegalArgumentException

const val LOGIN_NAME = "chrisbanes"

@ExperimentalCoroutinesApi
class SearchUserTest {
    private val repository: IUserRepository = mock()

    private lateinit var searchUser: SearchUser
    private lateinit var user: User

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    @Before
    fun setup() {
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

        searchUser = SearchUser(repository, mock())
    }

    @Test
    fun `when suspended a loading must be emitted`() = runBlockingTest {
        whenever(repository.searchUser(LOGIN_NAME)).thenReturn(user)

        val result = searchUser.invoke(SearchUser.Parameters(LOGIN_NAME)).first()

        Assert.assertTrue(result is InvokeStatus.Loading<*>)
    }

    @Test
    fun `when failed an error must be emitted`() = runBlocking {
        whenever(repository.searchUser(LOGIN_NAME)).thenThrow(IllegalArgumentException())

        val result = searchUser.invoke(SearchUser.Parameters(LOGIN_NAME)).drop(1).first()

        Assert.assertTrue(result is InvokeStatus.Error<*>)
    }

    @Test
    fun `when user found it should be emitted`() = runBlockingTest {
        whenever(repository.searchUser(LOGIN_NAME)).thenReturn(user)

        val result = searchUser.invoke(SearchUser.Parameters(LOGIN_NAME)).toList()

        Assert.assertEquals(2, result.size)
        Assert.assertTrue(result.get(1) is InvokeStatus.Success<com.youssefshamass.domain.entities.User>)
    }

    @Test
    fun `when user not found it should emit not found error`() = runBlocking {
        whenever(repository.searchUser(LOGIN_NAME)).thenThrow(NotFoundError())

        val result = searchUser.invoke(SearchUser.Parameters(LOGIN_NAME)).toList()

        Assert.assertEquals(2, result.size)
        Assert.assertTrue(result.get(1) is InvokeStatus.Error)
        Assert.assertTrue((result.get(1) as? InvokeStatus.Error)?.error is NotFoundError)
    }

}