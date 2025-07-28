package com.therxmv.telegramthemer.ui.base

import androidx.lifecycle.LifecycleCoroutineScope

abstract class BasePresenter<V> : Presenter<V> {

    private var _view: V? = null
    val view: V get() = requireNotNull(_view)

    private var _coroutineScope: LifecycleCoroutineScope? = null
    val coroutineScope: LifecycleCoroutineScope get() = requireNotNull(_coroutineScope)

    override fun attachView(view: V, coroutineScope: LifecycleCoroutineScope) {
        _view = view
        _coroutineScope = coroutineScope
    }

    override fun detachView() {
        _coroutineScope = null
        _view = null
    }
}

private interface Presenter<V> {
    fun attachView(view: V, coroutineScope: LifecycleCoroutineScope)
    fun detachView()
}