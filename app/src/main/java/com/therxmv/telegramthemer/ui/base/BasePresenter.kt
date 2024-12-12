package com.therxmv.telegramthemer.ui.base

import kotlinx.coroutines.CoroutineScope

abstract class BasePresenter<V> : Presenter<V> {

    private var _view: V? = null
    val view: V get() = requireNotNull(_view)

    private var _coroutineScope: CoroutineScope? = null
    val coroutineScope: CoroutineScope get() = requireNotNull(_coroutineScope)

    override fun attachView(view: V, coroutineScope: CoroutineScope) {
        _view = view
        _coroutineScope = coroutineScope
    }

    override fun detachView() {
        _coroutineScope = null
        _view = null
    }
}

private interface Presenter<V> {
    fun attachView(view: V, coroutineScope: CoroutineScope)
    fun detachView()
}