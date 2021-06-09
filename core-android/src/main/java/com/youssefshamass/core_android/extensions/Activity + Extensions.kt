package com.youssefshamass.core_android.extensions

import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.viewbinding.ViewBinding

inline fun <reified T : ViewDataBinding> AppCompatActivity.dataBinding(@LayoutRes layoutRes: Int): Lazy<T> = lazy {
    DataBindingUtil.setContentView(this, layoutRes) as T
}

inline fun <reified T : ViewBinding> AppCompatActivity.binding(@LayoutRes layoutRes: Int): Lazy<T> = lazy {
    DataBindingUtil.setContentView(this, layoutRes) as T
}