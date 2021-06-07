package com.youssefshamass.domain.users

import com.youssefshamass.core.interactors.SubjectUseCase
import com.youssefshamass.data.entities.local.User
import com.youssefshamass.data.repositories.UserRepository
import kotlinx.coroutines.flow.Flow

class ObservePreviousMatches(private val repository: UserRepository) :
    SubjectUseCase<Unit, List<User>>() {
    override fun createObservable(parameters: Unit?): Flow<List<User>> =
        repository.observePreviousMatches()
}