package com.youssefshamass.domain.mapper

import com.youssefshamass.data.entities.local.Follower
import org.junit.Assert
import org.junit.Test


class FollowerToUserHeaderTest {
    @Test
    fun `mapping translated correctly`() {
        val subject = Follower(
            1,
            "chrisbanes",
            "http://example.com"
        )

        val mapper = FollowerToUserHeader()
        val result = mapper.map(subject)

        Assert.assertEquals(subject.loginName, result.loginName)
        Assert.assertEquals(subject.avatarImageUrl, result.avatarUrl)
    }
}