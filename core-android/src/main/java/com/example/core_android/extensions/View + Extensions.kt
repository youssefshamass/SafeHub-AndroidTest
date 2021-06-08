package com.example.core_android.extensions

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton

fun View.gone() {
    this.visibility = View.GONE
}

fun View.visibile() {
    this.visibility = View.VISIBLE
}

fun ExtendedFloatingActionButton.bindToList(list: RecyclerView) {
    list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if (dy > 0) {
                this@bindToList.shrink()
            } else {
                this@bindToList.extend()
            }
        }
    })
}