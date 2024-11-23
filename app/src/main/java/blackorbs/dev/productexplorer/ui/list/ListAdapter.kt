package blackorbs.dev.productexplorer.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.RecyclerView
import blackorbs.dev.productexplorer.data.entities.Product
import blackorbs.dev.productexplorer.databinding.ProductItemBinding
import blackorbs.dev.productexplorer.helpers.loadWithPlaceholder

class ListAdapter: RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    fun updateList(products: List<Product>){
        asyncListDiffer.submitList(products)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder (
            ProductItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(asyncListDiffer.currentList[position])

    override fun getItemCount() = asyncListDiffer.currentList.size

    private val diffCallback = object : ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Product, newItem: Product) =
            oldItem.title == newItem.title
    }

    private val asyncListDiffer = AsyncListDiffer(this, diffCallback)

    inner class ViewHolder(
        private val binding: ProductItemBinding
    ): RecyclerView.ViewHolder(binding.root){
        fun bind(product: Product){
            with(binding){
                title.text = product.title
                image.loadWithPlaceholder(product.image)
                root.setOnClickListener {
                    root.findNavController().navigate(
                        ListPageDirections.toDetailsPage(product.id)
                    )
                }
            }
        }
    }
}