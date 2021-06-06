package com.youssefshamass.domain.users

import com.youssefshamass.core.interactors.UseCase
import com.youssefshamass.data.entities.local.User
import com.youssefshamass.data.repositories.UserRepository

class SearchUser(private val repository: UserRepository) : UseCase<SearchUser.Parameters, User>() {
    data class Parameters(val userLoginName: String)

    override suspend fun doWork(parameters: Parameters?): User =
        repository.searchUser(parameters!!.userLoginName)
}