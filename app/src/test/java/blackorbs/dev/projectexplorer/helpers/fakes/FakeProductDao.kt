package blackorbs.dev.projectexplorer.helpers.fakes

import blackorbs.dev.productexplorer.data.entities.Product
import blackorbs.dev.productexplorer.data.local.ProductDao

class FakeProductDao: ProductDao {
    private val _products = mutableListOf<Product>()

    override suspend fun addAll(products: List<Product>) {
        _products.clear()
        _products.addAll(products)
    }

    override suspend fun get(id: Int) =
        _products.first { product -> product.id == id }

    override suspend fun getAll() = _products
}