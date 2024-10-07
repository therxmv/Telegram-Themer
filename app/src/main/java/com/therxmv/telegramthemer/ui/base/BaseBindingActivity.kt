package com.therxmv.telegramthemer.ui.base

import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseBindingActivity<B: ViewBinding> : AppCompatActivity() {

    private var _binding: B? = null
    protected val binding: B get() = _binding!!

    protected fun setContentView(bindingInflater: (LayoutInflater) -> B) {
        _binding = bindingInflater(layoutInflater)
        setContentView(binding.root)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}