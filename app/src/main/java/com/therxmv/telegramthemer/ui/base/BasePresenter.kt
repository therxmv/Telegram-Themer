package com.therxmv.telegramthemer.ui.base

abstract class BasePresenter<V> : Presenter<V> {

    private var _view: V? = null
    val view: V get() = _view!!

    override fun attachView(view: V) {
        _view = view
    }

    override fun detachView() {
        _view = null
    }
}

private interface Presenter<V> {
    fun attachView(view: V)
    fun detachView()
}