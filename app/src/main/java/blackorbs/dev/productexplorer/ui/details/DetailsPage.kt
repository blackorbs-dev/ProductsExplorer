package blackorbs.dev.productexplorer.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import blackorbs.dev.productexplorer.R
import blackorbs.dev.productexplorer.databinding.DetailsPageBinding
import blackorbs.dev.productexplorer.helpers.formatPrice
import blackorbs.dev.productexplorer.helpers.loadWithPlaceholder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailsPage: Fragment() {
    private var binding: DetailsPageBinding ?= null
    private val viewModel by viewModels<DetailsViewModel>()
    private val args by navArgs<DetailsPageArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if(binding == null){
            binding = DetailsPageBinding.inflate(inflater)
            viewModel.getProduct(args.productID)
            with(activity as AppCompatActivity){
                setSupportActionBar(binding!!.toolbar.toolbar)
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                supportActionBar?.title = getString(R.string.details)
                binding!!.toolbar.toolbar.setNavigationOnClickListener {
                    binding!!.root.findNavController().popBackStack()
                }
            }
        }
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.product.collectLatest { product ->
                with(binding!!){
                    title.text = product.title
                    image.loadWithPlaceholder(product.image)
                    price.text = product.price.formatPrice()
                    rating.rating = product.rating.rate
                    ratingCount.text = getString(
                        R.string.rating_count,
                        product.rating.count
                    )
                    description.text = product.description
                }
            }
        }
    }
}