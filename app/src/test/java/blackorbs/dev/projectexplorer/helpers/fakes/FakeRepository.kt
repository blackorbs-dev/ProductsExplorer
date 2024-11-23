package blackorbs.dev.projectexplorer.helpers.fakes

import blackorbs.dev.productexplorer.data.entities.Product
import blackorbs.dev.productexplorer.data.entities.Response
import blackorbs.dev.productexplorer.repository.BaseRepository

class FakeRepository(
    private val products: List<Product>
): BaseRepository {

    override suspend fun get(id: Int) =
        products.first { product -> product.id == id }

    override suspend fun getAll() =
        Response.success(products)
}