package blackorbs.dev.productexplorer.helpers

import android.widget.ImageView
import blackorbs.dev.productexplorer.R
import coil.load
import java.text.NumberFormat

fun Double.formatPrice(): String =
    NumberFormat.getCurrencyInstance().apply {
        maximumFractionDigits = 2
    }.format(this)

fun ImageView.loadWithPlaceholder(url: String) =
    load(url){
        placeholder(R.drawable.placeholder)
        error(R.drawable.placeholder)
    }