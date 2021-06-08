package com.example.core_android.decorators

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration


class HorizontalSpaceDecorator(private val horizontalSpaceWidth: Int) : ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.left = horizontalSpaceWidth
        outRect.right = horizontalSpaceWidth
    }
}