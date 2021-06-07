package com.youssefshamass.safehub.presentation.details

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.core_android.ReduxViewModel
import com.youssefshamass.data.entities.local.Follower
import com.youssefshamass.data.entities.local.Following
import com.youssefshamass.data.entities.local.User
import com.youssefshamass.domain.users.ObserveUser
import com.youssefshamass.domain.users.RefreshUser
import kotlinx.coroutines.launch

class UserDetailsViewModel(
    private val userId: Int,
    private val observeUser: ObserveUser,
    private val refreshUser: RefreshUser,
) : ReduxViewModel<UserDetailsViewState>(
    UserDetailsViewState()
) {
    init {
        observeUser(ObserveUser.Parameters(userId))
        viewModelScope.launch {
            observeUser.observe().collectAndSetState {
                copy(user = it)
            }
        }

        viewModelScope.launch {
            refreshUser(RefreshUser.Parameters(userId))
        }
    }
}

data class UserDetailsViewState(
    val user: User? = null,
    val followers: PagingData<Follower>? = null,
    val following: PagingData<Following>? = null
)