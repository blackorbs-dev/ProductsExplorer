package blackorbs.dev.projectexplorer.helpers.fakes

import blackorbs.dev.productexplorer.data.entities.Product
import blackorbs.dev.productexplorer.data.remote.ApiService
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class FakeApiService(
    private val products: List<Product>
): ApiService {

    var error = ""

    override suspend fun getAll(): Response<List<Product>> =
        when(error){
            IO_EXCEPTION -> throw IOException()
            HTTP_EXCEPTION -> throw HttpException(
                Response.error<Product>(
                    404,
                    error.toResponseBody()
                )
            )
            else -> Response.success(products)
        }

    companion object{
        const val IO_EXCEPTION = "IO_EXCEPTION"
        const val HTTP_EXCEPTION = "HTTP_EXCEPTION"
    }
}