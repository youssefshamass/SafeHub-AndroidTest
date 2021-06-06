/*
 * Designed and developed by 2020 Youssef Shamass
 */
package com.youssefshamass.core.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun <T> LiveData<T>.observe(owner: LifecycleOwner, observer: (T) -> Unit) {
    observe(owner, Observer {
        observer(it)
    })
}
