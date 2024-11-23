package blackorbs.dev.projectexplorer.data

import androidx.test.filters.SmallTest
import blackorbs.dev.productexplorer.data.entities.Response
import blackorbs.dev.productexplorer.repository.ProductRepository
import blackorbs.dev.projectexplorer.helpers.fakes.FakeApiService
import blackorbs.dev.projectexplorer.helpers.fakes.FakeProductDao
import blackorbs.dev.projectexplorer.helpers.util.TestUtil.testProduct
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@SmallTest
class ProductRepositoryTest {

    private lateinit var repository: ProductRepository
    private val productDao = FakeProductDao()
    private val products = (1..6).map {
        testProduct()
    }
    private val apiService = FakeApiService(products)

    @Before
    fun setUp(){
        repository = ProductRepository(productDao, apiService)
    }

    @Test
    fun `get product from repository`() = runTest{
        productDao.addAll(products)
        assertEquals(
            products.first(),
            repository.get(products.first().id)
        )
    }

    @Test
    fun `get all products from remote success`() = runTest {
        val response = repository.getAll()
        assertEquals(
            Response.Status.SUCCESS,
            response.status
        )
        assertEquals(
            products,
            response.data
        )
    }

    @Test
    fun `get all products from remote failed`() = runTest{
        apiService.error = FakeApiService.IO_EXCEPTION
        assertEquals(
            Response.Status.ERROR,
            repository.getAll().status
        )
    }

    @Test
    fun `get all products from local success`() = runTest {
        productDao.addAll(products)
        apiService.error = FakeApiService.IO_EXCEPTION
        val response = repository.getAll()
        assertEquals(
            Response.Status.SUCCESS,
            response.status
        )
        assertEquals(
            products,
            response.data
        )
    }


}