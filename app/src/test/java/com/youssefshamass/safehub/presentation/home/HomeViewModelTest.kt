package com.youssefshamass.safehub.presentation.home

import com.nhaarman.mockitokotlin2.*
import com.youssefshamass.core.data.base.InvokeStatus
import com.youssefshamass.core_test.CoroutineTestRule
import com.youssefshamass.data.entities.local.User
import com.youssefshamass.domain.users.ObservePreviousMatches
import com.youssefshamass.domain.users.SearchUser
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.lang.IllegalArgumentException

const val LOGIN_NAME = "chrisbanes"

@ExperimentalCoroutinesApi
class HomeViewModelTest {
    private val observePreviousMatchesUseCase = mock<ObservePreviousMatches>()
    private val searchUserUseCase = mock<SearchUser>()

    private lateinit var viewModel: HomeViewModel
    private lateinit var user: User
    private val searchParameters = SearchUser.Parameters(LOGIN_NAME)

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    @Before
    fun setUp() {
        whenever(observePreviousMatchesUseCase.observe()).thenReturn(flow {
            emit(listOf(user))
        })

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

    @Test
    fun `on initialize should be observing matches`() {
        viewModel = HomeViewModel(observePreviousMatchesUseCase, searchUserUseCase)

        verify(observePreviousMatchesUseCase, times(1)).observe()
    }

    @Test
    fun `observing matches should update view state`() {
        val expectation = listOf(user)
        whenever(observePreviousMatchesUseCase.observe()).thenReturn(flow {
            emit(expectation)
        })
        viewModel = HomeViewModel(observePreviousMatchesUseCase, searchUserUseCase)

        Assert.assertEquals(expectation, viewModel.currentState().recentMatches)
    }

    @Test
    fun `on search error then a message should be appended to view state`() {
        mockErrorCase()

        viewModel.search(LOGIN_NAME)

        Assert.assertNotNull(viewModel.currentState().errorMessage)
    }

    @Test
    fun `on receiving error then loading state should be false`() {
        mockErrorCase()

        viewModel.search(LOGIN_NAME)

        Assert.assertFalse(viewModel.currentState().isLoading)
    }

    @Test
    fun `on search with valid input loading state must be set`() {
        mockLoadingCase()

        viewModel.search(LOGIN_NAME)

        Assert.assertEquals(true, viewModel.currentState().isLoading)
    }

    @Test
    fun `on loading state is true then error message should be null`() {
        mockLoadingCase(true)

        viewModel.search(LOGIN_NAME)

        Assert.assertNull(viewModel.currentState().errorMessage)
    }

    @Test
    fun `when receiving user result loading state should be false and user must be set`() {
        mockSuccessCase()

        viewModel.search(LOGIN_NAME)

        Assert.assertEquals(user, viewModel.currentState().match?.peek())
        Assert.assertEquals(false, viewModel.currentState().isLoading)
    }

    private fun mockErrorCase() {
        whenever(searchUserUseCase.invoke(searchParameters)).thenReturn(flow {
            emit(InvokeStatus.Error<User>(IllegalArgumentException()))
        })

        viewModel = HomeViewModel(observePreviousMatchesUseCase, searchUserUseCase)
    }

    private fun mockSuccessCase() {
        whenever(searchUserUseCase.invoke(searchParameters)).thenReturn(flow {
            emit(InvokeStatus.Loading<User>())
            emit(InvokeStatus.Success(user))
        })

        viewModel = HomeViewModel(observePreviousMatchesUseCase, searchUserUseCase)
    }

    private fun mockLoadingCase(subsequentToError: Boolean = false) {
        whenever(searchUserUseCase.invoke(searchParameters)).thenReturn(flow {
            if (subsequentToError)
                emit(InvokeStatus.Error<User>(IllegalArgumentException()))

            emit(InvokeStatus.Loading<User>())
        })

        viewModel = HomeViewModel(observePreviousMatchesUseCase, searchUserUseCase)
    }
}