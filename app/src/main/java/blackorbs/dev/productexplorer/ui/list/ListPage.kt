package blackorbs.dev.productexplorer.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import blackorbs.dev.productexplorer.R
import blackorbs.dev.productexplorer.data.entities.Response
import blackorbs.dev.productexplorer.databinding.ListPageBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListPage: Fragment() {
    private var binding: ListPageBinding ?= null
    private val viewModel by viewModels<ListViewModel>()
    private val _adapter = ListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if(binding == null){
            binding = ListPageBinding.inflate(inflater)
            with(binding!!.productsList) {
                setHasFixedSize(true)
                adapter = _adapter
            }
            viewModel.getProducts()
        }
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.products.collectLatest { response ->
                when(response.status){
                    Response.Status.SUCCESS -> {
                        _adapter.updateList(response.data!!)
                        binding!!.loadingIndicator.hide()
                    }
                    Response.Status.ERROR -> {
                        binding!!.loadingIndicator.hide()
                        Snackbar.make(
                            binding!!.root,
                            getString(R.string.error_try_again),
                            Snackbar.LENGTH_INDEFINITE
                        )
                            .setAction(R.string.retry){
                                viewModel.getProducts()
                            }
                            .show()
                    }
                    Response.Status.LOADING ->
                        binding!!.loadingIndicator.show()
                }
            }
        }
    }
}