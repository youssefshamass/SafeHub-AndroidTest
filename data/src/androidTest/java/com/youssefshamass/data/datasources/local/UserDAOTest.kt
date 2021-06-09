package com.youssefshamass.data.datasources.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.youssefshamass.data.db.ApplicationDatabase
import com.youssefshamass.data.entities.local.User
import com.youssefshamass.data.utils.InstrumentationTestCoroutineScopeRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class UserDAOTest {
    private lateinit var userDao: UserDAO
    private lateinit var db: ApplicationDatabase
    private lateinit var user: User

    @get:Rule
    var coroutinesTestRule = InstrumentationTestCoroutineScopeRule()

    @Before
    fun setupDatabase() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context,
            ApplicationDatabase::class.java,
        ).build()

        userDao = db.users()

        user = User(
            0,
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
    fun `writing a user should be read from list`() = runBlocking {
        userDao.insert(user)

        val firstUser = userDao.getMatches().first().first()

        Assert.assertEquals(user.loginName, firstUser.loginName)
    }

    @Test
    fun `writing a user should be accessible via id`() = runBlocking {
        val id = userDao.insert(user)

        val firstUser = userDao.getUser(id.toInt())

        Assert.assertEquals(user, firstUser)
    }

    @Test
    fun `writing a user should be accessible via login name`() = runBlocking {
        userDao.insert(user)

        val firstUser = userDao.getUser(user.loginName)

        Assert.assertEquals(user, firstUser)
    }
}