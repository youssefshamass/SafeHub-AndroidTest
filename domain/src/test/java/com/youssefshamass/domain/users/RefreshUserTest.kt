package com.youssefshamass.domain.users

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.youssefshamass.core.data.base.InvokeStatus
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

@ExperimentalCoroutinesApi
class RefreshUserTest {
    private val repository: IUserRepository = mock()

    private lateinit var refreshUser: RefreshUser
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

        refreshUser = RefreshUser(repository)
    }

    @Test
    fun `when suspended a loading must be emitted`() = runBlockingTest {
        whenever(repository.refreshUser(user.id)).thenReturn(Unit)

        val result = refreshUser.invoke(RefreshUser.Parameters(user.id)).first()

        Assert.assertTrue(result is InvokeStatus.Loading)
    }

    @Test
    fun `when failed an error must be emitted`() = runBlocking {
        whenever(repository.refreshUser(user.id)).thenThrow(IllegalArgumentException())

        val result = refreshUser.invoke(RefreshUser.Parameters(user.id)).drop(1).first()

        Assert.assertTrue(result is InvokeStatus.Error<Unit>)
    }

    @Test
    fun `when succeeded a unit should be emitted`() = runBlocking {
        whenever(repository.refreshUser(user.id)).thenReturn(Unit)

        val result = refreshUser.invoke(RefreshUser.Parameters(user.id)).toList()

        Assert.assertEquals(2, result.size)
        Assert.assertTrue(result.get(1) is InvokeStatus.Success<Unit>)
    }
}