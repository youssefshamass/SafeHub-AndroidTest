package com.youssefshamass.domain.users

import androidx.paging.*
import com.youssefshamass.core.interactors.PagingUseCase
import com.youssefshamass.data.datasources.mediators.FollowersRemoteMediator
import com.youssefshamass.data.entities.local.Follower
import com.youssefshamass.data.repositories.IUserRepository
import com.youssefshamass.domain.entities.UserHeader
import com.youssefshamass.domain.mapper.FollowerToUserHeader
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.component.inject
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.parameter.parametersOf

@KoinApiExtension
@ExperimentalPagingApi
@ExperimentalCoroutinesApi
class PaginateFollowers(
    private val userRepository: IUserRepository,
    private val followerToUserHeader: FollowerToUserHeader
) : PagingUseCase<PaginateFollowers.Parameters, UserHeader>(), KoinComponent {
    data class Parameters(
        override val config: PagingConfig,
        val forUserId: Int
    ) : PagingUseCase.Parameters

    override fun createObservable(parameters: Parameters?): Flow<PagingData<UserHeader>> {
        val pagingSourceFactory =
            { userRepository.getFollowersPagingSource(parameters!!.forUserId) }
        val mediator: FollowersRemoteMediator = get {
            parametersOf(parameters!!.forUserId)
        }

        return Pager(
            config = parameters!!.config,
            pagingSourceFactory = pagingSourceFactory,
            remoteMediator = mediator,
        ).flow.map { pagingData ->
            pagingData.map { user -> followerToUserHeader.map(user) }
        }
    }

}