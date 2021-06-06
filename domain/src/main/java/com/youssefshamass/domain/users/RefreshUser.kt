package com.youssefshamass.domain.users

import com.youssefshamass.core.interactors.UseCase
import com.youssefshamass.data.repositories.UserRepository

class RefreshUser(private val repository: UserRepository) : UseCase<RefreshUser.Parameters, Unit>() {
    data class Parameters(val userLoginName: String)

    override suspend fun doWork(parameters: Parameters?): Unit =
        repository.refreshUser(parameters!!.userLoginName)
}