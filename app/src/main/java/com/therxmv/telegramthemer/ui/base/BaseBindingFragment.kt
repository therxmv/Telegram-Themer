package com.therxmv.telegramthemer.ui.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import dagger.android.support.DaggerFragment

abstract class BaseBindingFragment<B: ViewBinding> : DaggerFragment() {

    private var _binding: B? = null
    protected val binding: B get() = requireNotNull(_binding)

    protected fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> B,
    ): View {
        _binding = bindingInflater(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}