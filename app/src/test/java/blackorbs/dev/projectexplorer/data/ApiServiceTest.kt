package blackorbs.dev.projectexplorer.data

import androidx.test.filters.SmallTest
import blackorbs.dev.productexplorer.data.entities.Rating
import blackorbs.dev.productexplorer.data.remote.ApiService
import blackorbs.dev.projectexplorer.helpers.util.TestExtensions.enqueueResponse
import blackorbs.dev.projectexplorer.helpers.util.TestUtil.testProduct
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@SmallTest
class ApiServiceTest {
    private lateinit var apiService: ApiService
    private val mockWebServer = MockWebServer()

    @Before
    fun setUp(){
        apiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ApiService::class.java)
    }

    @Test
    fun `get products from remote success`() = runTest{
        mockWebServer.enqueueResponse("products-response.json", 200)
        val response = apiService.getAll()
        assertTrue(response.isSuccessful)
        assertEquals(
            listOf(
                testProduct(
                    id = 1,
                    title = "Fjallraven - Foldsack No. 1 Backpack, Fits 15 Laptops",
                    price = 109.95,
                    description = "Your perfect pack for everyday use and walks in the forest. Stash your laptop (up to 15 inches) in the padded sleeve, your everyday",
                    category = "men's clothing",
                    image = "https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg",
                    rating = Rating(
                        rate = 3.9f,
                        count = 120
                    )
                )
            ),
            response.body()
        )
    }

    @Test
    fun `get products from remote failed`() = runTest {
        mockWebServer.enqueueResponse("products-response.json", 400)
        val response = apiService.getAll()
        assertFalse(response.isSuccessful)
        assertNull(response.body())
    }

    @After
    fun cleanUp(){
        mockWebServer.shutdown()
    }
}