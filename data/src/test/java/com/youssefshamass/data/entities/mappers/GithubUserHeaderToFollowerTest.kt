package com.youssefshamass.data.entities.mappers

import com.youssefshamass.data.entities.remote.UserHeader
import org.junit.Assert
import org.junit.Test


class GithubUserHeaderToFollowerTest {
    @Test
    fun `mapping translates correctly`() {
        var subject = UserHeader(
            "chrisbanes",
            "http://example.com")

        val mapper = GithubUserHeaderToFollower(1)
        val result = mapper.map(subject)

        Assert.assertEquals(1, result.userId)
        Assert.assertEquals(subject.loginName, result.loginName)
        Assert.assertEquals(subject.avatarImageUrl, result.avatarImageUrl)
    }
}