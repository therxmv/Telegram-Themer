package com.therxmv.telegramthemer.ui.base

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.ComponentDialog
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding

abstract class BaseBindingDialogFragment<B: ViewBinding> : DialogFragment() {

    companion object {
        private const val DIALOG_WIDTH_RATIO = 0.9
        const val DEFAULT_DIM = 0.2f
    }

    private var _binding: B? = null
    protected val binding: B get() = _binding!!

    private var _dialog: ComponentDialog? = null
    protected val dialog: ComponentDialog get() = _dialog!!

    protected fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> B,
    ): View {
        _binding = bindingInflater(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _dialog?.window?.setLayout(
            getDialogWidth(),
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _dialog = super.onCreateDialog(savedInstanceState) as ComponentDialog

        _dialog?.window?.setDimAmount(DEFAULT_DIM)

        return _dialog!!
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun getDialogWidth(): Int {
        val width = resources.displayMetrics.widthPixels
        return (width * DIALOG_WIDTH_RATIO).toInt()
    }
}