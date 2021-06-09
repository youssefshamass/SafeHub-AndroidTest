package com.youssefshamass.domain.users

import com.youssefshamass.core.interactors.UseCase
import com.youssefshamass.data.repositories.IUserRepository
import com.youssefshamass.domain.entities.User
import com.youssefshamass.domain.mapper.LocalUserToDomainUser

class SearchUser(
    private val repository: IUserRepository,
    private val mapper: LocalUserToDomainUser
) : UseCase<SearchUser.Parameters, User>() {
    data class Parameters(val userLoginName: String)

    override suspend fun doWork(parameters: Parameters): User =
        mapper.map(repository.searchUser(parameters.userLoginName))
}