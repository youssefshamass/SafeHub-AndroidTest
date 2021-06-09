package com.youssefshamass.domain.users

import com.youssefshamass.core.interactors.SubjectUseCase
import com.youssefshamass.data.repositories.IUserRepository
import com.youssefshamass.domain.entities.User
import com.youssefshamass.domain.mapper.LocalUserToDomainUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ObservePreviousMatches(
    private val repository: IUserRepository,
    private val mapper: LocalUserToDomainUser
) :
    SubjectUseCase<Unit, List<User>>() {
    override fun createObservable(parameters: Unit?): Flow<List<User>> =
        repository.observePreviousMatches().map {
            mapper.collection(it)
        }
}