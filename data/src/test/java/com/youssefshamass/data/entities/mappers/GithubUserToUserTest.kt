package com.youssefshamass.data.entities.mappers

import com.youssefshamass.data.entities.remote.User
import org.junit.Assert
import org.junit.Test

class GithubUserToUserTest {
    @Test
    fun `mapping translates correctly`() {
        var subject = User(
            1,
            "chrisbanes",
            "http://example.com",
            "Chris Banes",
            "Company name",
            null,
            "bio",
            1,
            23)

        val mapper = GithubUserToUser()
        val result = mapper.map(subject)

        Assert.assertEquals(subject.id, result.id)
        Assert.assertEquals(subject.loginName, result.loginName)
        Assert.assertEquals(subject.avatarImageUrl, result.avatarImageUrl)
        Assert.assertEquals(subject.displayName, result.displayName)
        Assert.assertEquals(subject.companyName, result.companyName)
        Assert.assertEquals(subject.location, result.location)
        Assert.assertEquals(subject.bio, result.bio)
        Assert.assertEquals(subject.numberOfFollowers, result.numberOfFollowers)
        Assert.assertEquals(subject.numberOfFollowings, result.numberOfFollowings)
    }
}