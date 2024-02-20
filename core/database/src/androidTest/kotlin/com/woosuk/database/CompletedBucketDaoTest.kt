package com.woosuk.database

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.woosuk.database.dao.BucketDao
import com.woosuk.database.dao.CompletedBucketDao
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.io.IOException

class CompletedBucketDaoTest {

    private lateinit var completedBucketDao: CompletedBucketDao
    private lateinit var bucketDao: BucketDao
    private lateinit var db: BucketDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context,
            BucketDatabase::class.java,
        ).build()
        bucketDao = db.bucketDao()
        completedBucketDao = db.completedBucketDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test(expected = SQLiteConstraintException::class)
    fun Bucket이_없으면_CompletedBucket_Insert할_때_에러를_던진다() = runTest {
        // given
        val testCompletedBucketEntity = testCompletedBucketEntity(1)

        // when
        completedBucketDao.insertCompletedBucket(testCompletedBucketEntity)
    }

    @Test
    fun 존재하는_Bucket_id로_CompletedBucket을_Insert하고_조회가_가능하다() = runTest {
        // given
        val testBucketEntity = testBucketEntity(1, "test")
        val testCompletedBucketEntity = testCompletedBucketEntity(1)
        // when
        bucketDao.insertBucket(testBucketEntity)
        completedBucketDao.insertCompletedBucket(testCompletedBucketEntity)
        val actual = completedBucketDao.loadCompletedBuckets().first()[0]
        // then
        assertEquals(testCompletedBucketEntity, actual)
    }

    @Test
    fun CompletedBucket_삭제가_가능하다() = runTest {
        // given
        val testBucketEntity = testBucketEntity(1, "test")
        val testCompletedBucketEntity = testCompletedBucketEntity(1)
        // when
        bucketDao.insertBucket(testBucketEntity)
        completedBucketDao.insertCompletedBucket(testCompletedBucketEntity)
        completedBucketDao.deleteCompletedBucket(testCompletedBucketEntity)
        val actual = completedBucketDao.loadCompletedBuckets().first()
        // then
        assertTrue(actual.isEmpty())
    }

    @Test
    fun CompletedBucket_업데이트가_가능하다() = runTest {
        // given
        val testBucketEntity = testBucketEntity(1, "test")
        val testCompletedBucketEntity = testCompletedBucketEntity(1)
        val updateCompletedBucketEntity = testCompletedBucketEntity(1, "updated")
        // when
        bucketDao.insertBucket(testBucketEntity)
        completedBucketDao.insertCompletedBucket(testCompletedBucketEntity)
        completedBucketDao.updateCompletedBucket(updateCompletedBucketEntity)
        val actual = completedBucketDao.loadCompletedBuckets().first()[0]
        // then
        assertEquals("updated", actual.description)
    }
}
