package com.youssefshamass.domain.users

import com.youssefshamass.core.interactors.SubjectUseCase
import com.youssefshamass.data.repositories.IUserRepository
import com.youssefshamass.domain.entities.User
import com.youssefshamass.domain.mapper.LocalUserToDomainUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ObserveUser(
    private val repository: IUserRepository,
    private val mapper: LocalUserToDomainUser
) : SubjectUseCase<ObserveUser.Parameters, User?>() {
    data class Parameters(val userId: Int)

    override fun createObservable(parameters: Parameters?): Flow<User?> =
        repository.observeUser(parameters!!.userId)?.map {
            it?.let {
                mapper.map(it)
            }
        }
}