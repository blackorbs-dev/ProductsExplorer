package blackorbs.dev.productexplorer.data.remote

import blackorbs.dev.productexplorer.data.entities.Product
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("products")
    suspend fun getAll(): Response<List<Product>>
}