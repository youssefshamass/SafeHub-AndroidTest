package com.example.core_android.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


@KoinApiExtension
@BindingAdapter("imageUrl", requireAll = true)
fun ImageView.imageUrl(url: String?) {
    url?.let {
        BindingAdapterManager.imageLoader.load(url)
            .into(this)
    }
}


@KoinApiExtension
object BindingAdapterManager : KoinComponent {
    val imageLoader: Picasso by inject()
}