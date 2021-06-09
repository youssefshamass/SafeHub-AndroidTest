/*
 * Designed and developed by 2020 Youssef Shamass
 */
package com.youssefshamass.core_android

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.*
import kotlin.reflect.KProperty1

@ExperimentalCoroutinesApi
abstract class ReduxViewModel<S> : ViewModel {
    private val _state: MutableStateFlow<S>
    private val _stateMutex = Mutex()
    private val _queue: LinkedList<S>

    constructor(initialState: S) : super() {
        _state = MutableStateFlow(initialState)
        _queue = LinkedList<S>().apply {
            add(initialState)
        }
    }

    val state: LiveData<S>
        get() = _state.asLiveData()

    fun currentState() = _state.value

    protected suspend fun setState(reducer: S.() -> S) =
            _stateMutex.withLock {
                val newState = reducer(_state.value)
                _queue.add(newState)
                _state.value = newState
            }

    protected fun CoroutineScope.launchSetState(reducer: S.() -> S) =
            launch { setState(reducer) }

    protected suspend fun withState(block: (S) -> Unit) =
            _stateMutex.withLock {
                block(_state.value)
            }

    protected suspend fun <T> Flow<T>.collectAndSetState(stateReducer: S.(T) -> S) =
            collect {
                setState {
                    stateReducer(this, it)
                }
            }

    protected fun subscribeToState(block: (S) -> Unit) =
            viewModelScope.launch {
                _state.collect {
                    block(it)
                }
            }

    protected fun <P> observeProperty(property: KProperty1<S, P>): LiveData<P> =
            subscribeToProperty(property).asLiveData()

    protected suspend fun <P> subscribeToProperty(property: KProperty1<S, P>, block: (P) -> Unit) =
            subscribeToProperty(property).collect {
                block(it)
            }

    protected fun <P> subscribeToProperty(property: KProperty1<S, P>): Flow<P> =
            _state.map { property.get(it) }.distinctUntilChanged()
}
