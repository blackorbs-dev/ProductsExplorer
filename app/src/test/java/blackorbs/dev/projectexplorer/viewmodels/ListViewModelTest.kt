package blackorbs.dev.projectexplorer.viewmodels

import androidx.test.filters.SmallTest
import blackorbs.dev.productexplorer.data.entities.Product
import blackorbs.dev.productexplorer.data.entities.Response
import blackorbs.dev.productexplorer.ui.list.ListViewModel
import blackorbs.dev.projectexplorer.helpers.fakes.FakeRepository
import blackorbs.dev.projectexplorer.helpers.util.MainCoroutineRule
import blackorbs.dev.projectexplorer.helpers.util.TestUtil.testProduct
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@SmallTest
class ListViewModelTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var listViewModel: ListViewModel
    private val products = (1..6).map {
        testProduct(id = it)
    }

    @Before
    fun setUp(){
        listViewModel = ListViewModel(
            FakeRepository(products),
            mainCoroutineRule.testDispatcher
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `observe products list changes`() = runTest {
        assertEquals(
            Response.loading<List<Product>?>(),
            listViewModel.products.value
        )
        val values = mutableListOf<Response<List<Product>?>>()
        backgroundScope.launch {
            listViewModel.products.toList(values)
        }
        listViewModel.getProducts()
        advanceUntilIdle()
        assertEquals(
            listOf(
                Response.loading<List<Product>?>()
            ),
            values
        )
        listViewModel.getProducts()
        advanceUntilIdle()
        assertEquals(
            listOf(
                Response.loading<List<Product>?>(),
                Response.success(products)
            ),
            values
        )
    }
}