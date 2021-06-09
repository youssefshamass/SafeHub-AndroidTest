package com.youssefshamass.domain.mapper

import com.youssefshamass.data.entities.local.User
import junit.framework.TestCase
import org.junit.Assert
import org.junit.Test

class LocalUserToDomainUserTest {
    @Test
    fun `mapping translate correctly`() {
        val subject = User(
            1,
            "youssefshamass",
            "http://example.com",
            "Youssef shamass",
            "-",
            "Damascus - Syria",
            "Bio",
            0,
            0
        )

        val mapper = LocalUserToDomainUser()
        val result = mapper.map(subject)

        Assert.assertEquals(subject.id, result.id)
        Assert.assertEquals(subject.loginName, result.loginName)
        Assert.assertEquals(subject.displayName, result.displayName)
        Assert.assertEquals(subject.bio, result.bio)
        Assert.assertEquals(subject.avatarImageUrl, result.avatarImageUrl)
        Assert.assertEquals(subject.numberOfFollowers, result.numberOfFollowers)
        Assert.assertEquals(subject.numberOfFollowings, result.numberOfFollowings)
    }
}