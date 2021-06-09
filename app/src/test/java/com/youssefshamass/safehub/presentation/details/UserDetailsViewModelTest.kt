package com.youssefshamass.safehub.presentation.details

import androidx.paging.ExperimentalPagingApi
import com.nhaarman.mockitokotlin2.*
import com.youssefshamass.core_test.CoroutineTestRule
import com.youssefshamass.data.entities.local.User
import com.youssefshamass.domain.users.ObserveUser
import com.youssefshamass.domain.users.RefreshUser
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.component.KoinApiExtension

@KoinApiExtension
@ExperimentalCoroutinesApi
@ExperimentalPagingApi
class UserDetailsViewModelTest {
    private val refreshUser : RefreshUser = mock()
    private val observeUser: ObserveUser = mock()

    private lateinit var viewModel: UserDetailsViewModel
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

        whenever(observeUser.observe()).thenReturn(flow {
            emit(user)
        })

        viewModel = UserDetailsViewModel(1, observeUser, refreshUser, mock(), mock())
    }

    @Test
    fun `on initialize should invoke user observation`() {
        verify(observeUser).invoke(ObserveUser.Parameters(user.id))
    }

    @Test
    fun `on initialize should observe user`() {
        verify(observeUser).observe()
    }

    @Test
    fun `on observe user view state should be updated with user value`() {
        Assert.assertEquals(user, viewModel.currentState().user)
    }

    @Test
    fun `refresh user should never be called`() {
        verify(refreshUser, times(0)).invoke(RefreshUser.Parameters(user.id))
    }
}