package blackorbs.dev.productexplorer.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import blackorbs.dev.productexplorer.data.entities.Product
import blackorbs.dev.productexplorer.data.entities.Response
import blackorbs.dev.productexplorer.repository.BaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val repository: BaseRepository,
    private val dispatcher: CoroutineDispatcher
): ViewModel() {

    private val _products =
        MutableStateFlow<Response<List<Product>?>>(Response.loading())
    val products = _products.asStateFlow()

    fun getProducts(){
        viewModelScope.launch(dispatcher) {
            _products.emit(Response.loading())
            _products.emit(repository.getAll())
        }
    }
}