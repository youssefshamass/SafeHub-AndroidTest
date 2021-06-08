package com.youssefshamass.domain.users

import androidx.paging.*
import com.youssefshamass.core.interactors.PagingUseCase
import com.youssefshamass.data.datasources.mediators.FollowingsRemoteMediator
import com.youssefshamass.data.entities.local.Following
import com.youssefshamass.data.repositories.IUserRepository
import com.youssefshamass.domain.entities.UserHeader
import com.youssefshamass.domain.mapper.FollowingToUserHeader
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf

@KoinApiExtension
@ExperimentalPagingApi
@ExperimentalCoroutinesApi
class PaginateFollowings(
    private val userRepository: IUserRepository,
    private val followingToUserHeader: FollowingToUserHeader
) : PagingUseCase<PaginateFollowings.Parameters, UserHeader>(), KoinComponent {
    data class Parameters(
        override val config: PagingConfig,
        val forUserId: Int
    ) : PagingUseCase.Parameters

    override fun createObservable(parameters: Parameters?): Flow<PagingData<UserHeader>> {
        val pagingSourceFactory =
            { userRepository.getFollowingsPagingSource(parameters!!.forUserId) }
        val mediator: FollowingsRemoteMediator = get {
            parametersOf(parameters!!.forUserId)
        }

        return Pager(
            config = parameters!!.config,
            pagingSourceFactory = pagingSourceFactory,
            remoteMediator = mediator,
        ).flow.map { it.map { followingToUserHeader.map(it) } }
    }

}