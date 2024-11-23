package blackorbs.dev.projectexplorer.data

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import blackorbs.dev.productexplorer.data.local.Database
import blackorbs.dev.productexplorer.data.local.ProductDao
import blackorbs.dev.projectexplorer.helpers.util.TestUtil.testProduct
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
@SmallTest
class ProductDaoTest {
    private lateinit var database: Database
    private lateinit var productDao: ProductDao

    @Before
    fun setUp(){
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            Database::class.java
        ).build()
        productDao = database.productDao()
    }

    @Test
    fun `add and get products from local database`() = runTest{
        assertTrue(productDao.getAll().isEmpty())
        val products = (1..6).map { testProduct() }
        productDao.addAll(products)
        assertEquals(
            products,
            productDao.getAll()
        )
        assertEquals(
            products.first(),
            productDao.get(products.first().id)
        )
    }

    @After
    fun cleanUp(){
        database.close()
    }
}