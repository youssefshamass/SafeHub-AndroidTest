package com.youssefshamass.domain.mapper

import com.youssefshamass.data.entities.local.Following
import org.junit.Assert
import org.junit.Test

class FollowingToUserHeaderTest {
    @Test
    fun `mapping translated correctly`() {
        val subject = Following(
            1,
            "chrisbanes",
            "http://example.com"
        )

        val mapper = FollowingToUserHeader()
        val result = mapper.map(subject)

        Assert.assertEquals(subject.loginName, result.loginName)
        Assert.assertEquals(subject.avatarImageUrl, result.avatarUrl)
    }
}