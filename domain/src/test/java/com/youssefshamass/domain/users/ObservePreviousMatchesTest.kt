package com.youssefshamass.domain.users

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.youssefshamass.core_test.CoroutineTestRule
import com.youssefshamass.data.entities.local.User
import com.youssefshamass.data.repositories.IUserRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ObservePreviousMatchesTest {
    private val repository: IUserRepository = mock()

    private lateinit var observePreviousMatches: ObservePreviousMatches
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

        whenever(repository.observePreviousMatches()).thenReturn(flow {
            listOf(user)
        })

        observePreviousMatches = ObservePreviousMatches(repository)
    }

    @Test
    fun `on invoke returns emits a list of user`() : Unit = runBlockingTest {
        val job = launch {
            val value = observePreviousMatches.observe().first()
            Assert.assertEquals(user, value.first())
        }

        job.cancel()
    }

    @Test
    fun `on invoke calls repository`() : Unit = runBlockingTest{
        val job = launch {
            observePreviousMatches.observe().toList()
        }

        verify(repository).observePreviousMatches()
        job.cancel()
    }
}