package blackorbs.dev.projectexplorer.viewmodels

import androidx.test.filters.SmallTest
import blackorbs.dev.productexplorer.ui.details.DetailsViewModel
import blackorbs.dev.projectexplorer.helpers.fakes.FakeRepository
import blackorbs.dev.projectexplorer.helpers.util.TestUtil.testProduct
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@SmallTest
class DetailsViewModelTest {

    private lateinit var detailsViewModel: DetailsViewModel
    private val products = (1..6).map {
        testProduct(id = it)
    }

    @Before
    fun setUp(){
        detailsViewModel = DetailsViewModel(
            FakeRepository(products)
        )
    }

    @Test
    fun `observe product data changes`() = runTest{
        detailsViewModel.getProduct(1)
        assertEquals(
            products.first(),
            detailsViewModel.product.first()
        )
        detailsViewModel.getProduct(2)
        assertEquals(
            products[1],
            detailsViewModel.product.first()
        )
        detailsViewModel.getProduct(3)
        assertEquals(
            products[2],
            detailsViewModel.product.first()
        )
    }
}