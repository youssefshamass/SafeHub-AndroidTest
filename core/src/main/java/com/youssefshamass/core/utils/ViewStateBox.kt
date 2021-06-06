package com.youssefshamass.core.utils

class ViewStateBox<T>(private val value: T) {
    private var _beenConsumed: Boolean = false

    fun onlyIfChanged(override: Boolean = false, block: (T) -> Unit) {
        if ((_beenConsumed || value == null) && !override)
            return

        block(value)

        _beenConsumed = true
    }

    fun peek() = value
}