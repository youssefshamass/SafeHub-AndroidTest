package com.youssefshamass.domain.users

import com.youssefshamass.core.interactors.SubjectUseCase
import com.youssefshamass.data.entities.local.User
import com.youssefshamass.data.repositories.IUserRepository
import kotlinx.coroutines.flow.Flow

class ObserveUser(
    private val repository: IUserRepository
) : SubjectUseCase<ObserveUser.Parameters, User?>() {
    data class Parameters(val userId: Int)

    override fun createObservable(parameters: Parameters?): Flow<User?> =
        repository.observeUser(parameters!!.userId)
}