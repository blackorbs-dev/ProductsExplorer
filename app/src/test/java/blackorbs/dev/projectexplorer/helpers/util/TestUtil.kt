package blackorbs.dev.projectexplorer.helpers.util

import blackorbs.dev.productexplorer.data.entities.Product
import blackorbs.dev.productexplorer.data.entities.Rating

object TestUtil {
    private var _id: Int = 0

    fun testProduct(
        id: Int? = null,
        title: String = "ProductTest",
        description: String = "Just a product for testing",
        image: String = "https://dummyurl",
        category: String = "No Category",
        price: Double = 45.66,
        rating: Rating = Rating(
            rate = 4.5f,
            count = 124
        )
    ): Product {
        _id += 1
        return Product(
            id = id ?: _id,
            title = title,
            description = description,
            image = image,
            category = category,
            price = price,
            rating = rating
        )
    }
}