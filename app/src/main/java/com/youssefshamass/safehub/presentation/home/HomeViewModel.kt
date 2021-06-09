package com.youssefshamass.safehub.presentation.home

import androidx.lifecycle.viewModelScope
import com.youssefshamass.core_android.ReduxViewModel
import com.youssefshamass.core.data.base.InvokeStatus
import com.youssefshamass.core.errors.NotFoundError
import com.youssefshamass.core.errors.RateLimitedError
import com.youssefshamass.core.extensions.exhaustive
import com.youssefshamass.core.utils.ViewStateBox
import com.youssefshamass.data.entities.local.User
import com.youssefshamass.domain.users.ObservePreviousMatches
import com.youssefshamass.domain.users.SearchUser
import kotlinx.coroutines.launch

class HomeViewModel(
    private val observePreviousMatches: ObservePreviousMatches,
    private val searchUser: SearchUser
) : ReduxViewModel<HomeViewState>(
    HomeViewState()
) {
    init {
        viewModelScope.launch {
            observePreviousMatches.observe().collectAndSetState {
                copy(recentMatches = it)
            }
        }
    }

    fun search(loginName: String) {
        viewModelScope.launch {
            searchUser(SearchUser.Parameters(loginName)).collectAndSetState {
                when (it) {
                    is InvokeStatus.Error -> {
                        if (it.error is NotFoundError)
                            copy(
                                errorMessage = ViewStateBox("Not found"),
                                isLoading = false
                            )
                        else if (it.error is RateLimitedError)
                            copy(
                                errorMessage = ViewStateBox("Oops, We've been rate limited," +
                                        " Please wait an hour before trying the app again"),
                                isLoading = false
                            )
                        else
                            copy(
                                errorMessage = ViewStateBox(it.error.message),
                                isLoading = false
                            )
                    }
                    is InvokeStatus.Loading -> copy(
                        isLoading = true,
                        errorMessage = null
                    )
                    is InvokeStatus.Success -> copy(
                        match = ViewStateBox(it.data),
                        isLoading = false
                    )
                }.exhaustive
            }
        }
    }
}

data class HomeViewState(
    val recentMatches: List<User>? = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: ViewStateBox<String?>? = null,
    val match: ViewStateBox<User?>? = null
)