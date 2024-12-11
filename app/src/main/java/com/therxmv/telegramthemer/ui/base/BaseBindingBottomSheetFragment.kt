package com.therxmv.telegramthemer.ui.base

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.therxmv.telegramthemer.ui.base.BaseBindingDialogFragment.Companion.DEFAULT_DIM

abstract class BaseBindingBottomSheetFragment<B: ViewBinding> : BottomSheetDialogFragment() {

    private var _binding: B? = null
    protected val binding: B get() = _binding!!

    private var _dialog: BottomSheetDialog? = null
    protected val dialog: BottomSheetDialog get() = _dialog!!

    protected fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> B,
    ): View {
        _binding = bindingInflater(inflater, container, false)
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog

        _dialog?.behavior?.isDraggable = false
        _dialog?.window?.setDimAmount(DEFAULT_DIM)

        return _dialog!!
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}