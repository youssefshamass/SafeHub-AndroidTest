package com.youssefshamass.safehub.presentation.details

import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.core_android.ReduxViewModel
import com.youssefshamass.core.utils.ViewStateBox
import com.youssefshamass.data.entities.local.User
import com.youssefshamass.domain.entities.UserHeader
import com.youssefshamass.domain.users.ObserveUser
import com.youssefshamass.domain.users.PaginateFollowers
import com.youssefshamass.domain.users.PaginateFollowings
import com.youssefshamass.domain.users.RefreshUser
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import org.koin.core.component.KoinApiExtension

@KoinApiExtension
@ExperimentalCoroutinesApi
@ExperimentalPagingApi
class UserDetailsViewModel(
    private val userId: Int,
    private val observeUser: ObserveUser,
    private val refreshUser: RefreshUser,
    private val paginateFollowings: PaginateFollowings,
    private val paginateFollowers: PaginateFollowers
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

        paginateFollowers(
            PaginateFollowers.Parameters(
                forUserId = userId,
                config = PagingConfig(
                    10,
                    1,
                    false,
                    initialLoadSize = 10
                )
            )
        )
        viewModelScope.launch {
            paginateFollowers.observe().cachedIn(viewModelScope).collectAndSetState {
                copy(followers = it)
            }
        }

        paginateFollowings(
            PaginateFollowings.Parameters(
                forUserId = userId,
                config = PagingConfig(
                    10,
                    1,
                    false,
                    initialLoadSize = 10,

                )
            )
        )
        viewModelScope.launch {
            paginateFollowings.observe().cachedIn(viewModelScope).collectAndSetState {
                copy(following = it)
            }
        }
    }

    fun updateDisplayState(displayState: DisplayState) {
        viewModelScope.launchSetState {
            copy(displayState = ViewStateBox(displayState))
        }
    }
}

data class UserDetailsViewState(
    val user: User? = null,
    val followers: PagingData<UserHeader> = PagingData.empty(),
    val following: PagingData<UserHeader> = PagingData.empty(),
    val displayState: ViewStateBox<DisplayState> = ViewStateBox(DisplayState.Followers)
)

enum class DisplayState {
    Followers,
    Following
}