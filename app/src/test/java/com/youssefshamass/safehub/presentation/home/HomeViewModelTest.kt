package com.youssefshamass.safehub.presentation.home

import com.nhaarman.mockitokotlin2.mock
import com.youssefshamass.data.entities.local.User
import com.youssefshamass.domain.users.ObservePreviousMatches
import com.youssefshamass.domain.users.SearchUser
import org.junit.Before

class HomeViewModelTest {
    private val observePreviousMatchesUseCase = mock<ObservePreviousMatches>()
    private val searchUserUseCase = mock<SearchUser>()

    private lateinit var viewModel: HomeViewModel
    private lateinit var user: User

    @Before
    fun setUp() {
        viewModel = HomeViewModel(observePreviousMatchesUseCase, searchUserUseCase)
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


}