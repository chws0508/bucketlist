package com.woosuk.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.woosuk.database.dao.BucketDao
import com.woosuk.database.dao.CompletedBucketDao
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

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
    fun ID가_0인_BUCKET을_Insert하면_자동으로_Id를_늘려서_저장한다() = runTest {
        // given
        val testBucketEntity = testBucketEntity(0, "test")
        val testBucketEntity2 = testBucketEntity(0, "test2")
        // when
        bucketDao.insertBucket(testBucketEntity)
        bucketDao.insertBucket(testBucketEntity2)
        val actual = bucketDao.loadAllBucket().first()
        // then
        assertEquals(2, actual[1].id)
        assertEquals(testBucketEntity2.title, actual[1].title)
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

    @Test
    fun ID로_버킷을_조회할_수_있다() = runTest {
        // given
        val testBucketEntities = listOf(
            testBucketEntity(1, "test1"),
            testBucketEntity(2, "test2"),
            testBucketEntity(3, "test3"),
        )
        // when
        testBucketEntities.forEach { bucketDao.insertBucket(it) }
        val actual = bucketDao.getBucketById(1)?.id
        // then
        assertEquals(1, actual)
    }

    @Test
    fun 일치하는_아이디가_없으면_emptylist를_반환한다() = runTest {
        // given
        val testBucketEntities = listOf(
            testBucketEntity(1, "test1"),
            testBucketEntity(2, "test2"),
            testBucketEntity(3, "test3"),
        )
        // when
        testBucketEntities.forEach { bucketDao.insertBucket(it) }
        val actual = bucketDao.getBucketById(4)
        // then
        assertNull(actual)
    }
}
