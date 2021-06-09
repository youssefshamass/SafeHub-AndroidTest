package com.youssefshamass.domain.users

import com.youssefshamass.core.interactors.UseCase
import com.youssefshamass.data.repositories.IUserRepository

class RefreshUser(private val repository: IUserRepository) : UseCase<RefreshUser.Parameters, Unit>() {
    data class Parameters(val userId: Int)

    override suspend fun doWork(parameters: Parameters): Unit =
        repository.refreshUser(parameters.userId)
}