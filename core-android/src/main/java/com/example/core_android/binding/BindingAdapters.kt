package com.example.core_android.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import org.koin.core.component.KoinApiExtension


@KoinApiExtension
@BindingAdapter("imageUrl", requireAll = true)
fun ImageView.imageUrl(url: String?) {
    url?.let {
        Glide.with(context)
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(this)
    }
}


