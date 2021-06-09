package com.youssefshamass.domain.users

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.youssefshamass.core_test.CoroutineTestRule
import com.youssefshamass.data.entities.local.User
import com.youssefshamass.data.repositories.IUserRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt


@ExperimentalCoroutinesApi
class ObserveUserTest {
    private val repository: IUserRepository = mock()

    private lateinit var observeUser: ObserveUser
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

        whenever(repository.observeUser(user.id)).thenReturn(flow {
            user
        })
        whenever(repository.observeUser(anyInt())).thenReturn(flow {

        })

        observeUser = ObserveUser(repository, mock())
    }

    @Test
    fun `on invoke with persisted user then should returns user`() : Unit = runBlockingTest {
        val job = launch {
            observeUser.invoke(ObserveUser.Parameters(user.id))
            val value = observeUser.observe().first()
            Assert.assertEquals(user, value)
        }

        job.cancel()
    }

    @Test
    fun `on invoke with invalid id then should returns null`() : Unit = runBlockingTest {
        val job = launch {
            observeUser.invoke(ObserveUser.Parameters(3))
            val value = observeUser.observe().first()
            Assert.assertNull(value)
        }

        job.cancel()
    }

    @Test
    fun `on invoke calls repository`() : Unit = runBlockingTest{
        val job = launch {
            observeUser.invoke(ObserveUser.Parameters(user.id))
            observeUser.observe().single()
        }

        verify(repository).observeUser(user.id)
        job.cancel()
    }
}