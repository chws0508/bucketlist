package com.woosuk.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.woosuk.database.dao.BucketDao
import com.woosuk.database.dao.CompletedBucketDao
import com.woosuk.database.entity.BucketEntity
import com.woosuk.database.entity.CompletedBucketEntity
import com.woosuk.domain.model.AgeRange
import com.woosuk.domain.model.BucketCategory
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.time.LocalDateTime

class BucketDaoTest {

    private lateinit var bucketDao: BucketDao
    private lateinit var completedBucketDao: CompletedBucketDao
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

    @Test
    fun BUCKET을_Insert하고_Load할_수_있다() = runTest {
        // given
        val testBucketEntity = testBucketEntity(1, "test")
        // when
        bucketDao.insertBucket(testBucketEntity)
        val actual = bucketDao.loadAllBucket().first()
        // then
        assertEquals(1, actual.first().id)
        assertEquals(testBucketEntity.title, actual.first().title)
    }

    @Test
    fun Bucket를_Delete할_수_있다() = runTest {
        // given
        val testBucketEntity = testBucketEntity(1, "test")
        // when
        bucketDao.insertBucket(testBucketEntity)
        bucketDao.deleteBucket(testBucketEntity)
        val actual = bucketDao.loadAllBucket().first()
        // then
        assertEquals(true, actual.isEmpty())
    }

    @Test
    fun `Bucket을_Update할_수_있다`() = runTest {
        // given
        val testBucketEntity = testBucketEntity(1, "test")
        val updateBucketEntity = testBucketEntity(1, "updated")
        // when
        bucketDao.insertBucket(testBucketEntity)
        bucketDao.updateBucket(updateBucketEntity)
        val actual = bucketDao.loadAllBucket().first().first()
        // then
        assertEquals("updated", actual.title)
    }

    @Test
    fun 완료하지_않은_BUCKET만_조회가_가능하다() = runTest {
        // given
        val testBucketEntities = listOf(
            testBucketEntity(1, "test1"),
            testBucketEntity(2, "test2"),
            testBucketEntity(3, "test3"),
        )
        val testCompletedBucketEntities = listOf(
            testCompletedBucketEntity(1),
            testCompletedBucketEntity(2),
        )
        // when
        testBucketEntities.forEach { bucketDao.insertBucket(it) }
        testCompletedBucketEntities.forEach { completedBucketDao.insertCompletedBucket(it) }
        val actual = bucketDao.loadUnCompletedBuckets().first().first()
        // then
        assertEquals(3, actual.id)
    }
}
