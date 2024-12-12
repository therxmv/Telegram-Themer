package com.therxmv.telegramthemer.ui.base

import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import dagger.android.support.DaggerAppCompatActivity

abstract class BaseBindingActivity<B: ViewBinding> : DaggerAppCompatActivity() {

    private var _binding: B? = null
    protected val binding: B get() = requireNotNull(_binding)

    protected fun setContentView(bindingInflater: (LayoutInflater) -> B) {
        _binding = bindingInflater(layoutInflater)
        setContentView(binding.root)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}