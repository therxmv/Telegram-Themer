package com.therxmv.telegramthemer.ui.base

import androidx.lifecycle.LifecycleOwner

abstract class BasePresenter<V> : Presenter<V> {

    private var _view: V? = null
    val view: V get() = requireNotNull(_view)

    private var _lifecycleOwner: LifecycleOwner? = null
    val lifecycleOwner: LifecycleOwner get() = requireNotNull(_lifecycleOwner)

    override fun attachView(view: V) {
        _view = view
        _lifecycleOwner = view as? LifecycleOwner
    }

    override fun detachView() {
        _lifecycleOwner = null
        _view = null
    }
}

private interface Presenter<V> {
    fun attachView(view: V)
    fun detachView()
}