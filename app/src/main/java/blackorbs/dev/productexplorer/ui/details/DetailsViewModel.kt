package blackorbs.dev.productexplorer.ui.details

import androidx.lifecycle.ViewModel
import blackorbs.dev.productexplorer.repository.BaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: BaseRepository
) : ViewModel() {
    private val _id = MutableStateFlow(0)
    @OptIn(ExperimentalCoroutinesApi::class)
    val product = _id.mapLatest {
        repository.get(it)
    }

    fun getProduct(id: Int){
        _id.value = id
    }
}