package com.example.core_android.binding

import android.view.View
import androidx.databinding.BindingConversion

@BindingConversion
fun convertBooleanToVisibility(state: Boolean) = if (state) View.VISIBLE else View.GONE