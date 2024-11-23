package blackorbs.dev.productexplorer.repository

import blackorbs.dev.productexplorer.data.entities.Product
import blackorbs.dev.productexplorer.data.entities.Response
import blackorbs.dev.productexplorer.data.local.ProductDao
import blackorbs.dev.productexplorer.data.remote.ApiService
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepository @Inject constructor(
    private val productDao: ProductDao,
    private  val apiService: ApiService
) : BaseRepository {

    override suspend fun get(id: Int) = productDao.get(id)

    override suspend fun getAll(): Response<List<Product>?> {
        val cachedData = productDao.getAll()
        if(cachedData.isEmpty()){
            try {
                val response = apiService.getAll()
                if(response.isSuccessful){
                    response.body()?.let {
                        productDao.addAll(it)
                        return Response.success(it)
                    }
                }
                return Response.error(response.message())
            }
            catch (e: IOException){
                return Response.error(e.message ?: e.toString())
            }
            catch (e: HttpException){
                return Response.error(e.message ?: e.toString())
            }
        }
        else{
            return Response.success(cachedData)
        }
    }
}

interface BaseRepository {
    suspend fun get(id: Int): Product
    suspend fun getAll(): Response<List<Product>?>
}