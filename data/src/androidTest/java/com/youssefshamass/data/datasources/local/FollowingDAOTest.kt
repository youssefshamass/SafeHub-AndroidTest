package com.youssefshamass.data.datasources.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.youssefshamass.core.utils.CoroutineTestRule
import com.youssefshamass.data.db.ApplicationDatabase
import com.youssefshamass.data.entities.local.Follower
import com.youssefshamass.data.entities.local.Following
import com.youssefshamass.data.entities.local.User
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class FollowingDAOTest {
    private lateinit var followingDao: FollowingDAO
    private lateinit var userDao: UserDAO
    private lateinit var db: ApplicationDatabase
    private lateinit var user: User

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    @Before
    fun setupDatabase() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context,
            ApplicationDatabase::class.java,
        ).build()

        followingDao = db.followings()
        userDao = db.users()

        user = User(
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
    }

    @After
    @Throws(IOException::class)
    fun closeDatabase() {
        db.close()
    }

    @Test
    fun `when clear should remove all related following`() = runBlocking {
        val anotherUser = user.copy(id = 2)

        userDao.insert(user)
        userDao.insert(anotherUser)

        (1..5).forEach {
            followingDao.insert(Following(user.id, "Test", "http://example.com"))
            followingDao.insert(Following(anotherUser.id, "Test", "http://example.com"))
        }

        val numberOfDeletedRows = followingDao.clear(user.id)

        Assert.assertEquals(numberOfDeletedRows, 5)
    }

}