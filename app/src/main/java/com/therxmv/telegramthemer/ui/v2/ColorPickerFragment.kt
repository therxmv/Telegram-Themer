package com.therxmv.telegramthemer.ui.v2

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.therxmv.telegramthemer.databinding.FragmentColorPickerBinding
import com.therxmv.telegramthemer.ui.base.BaseBindingBottomSheetFragment

class ColorPickerFragment : BaseBindingBottomSheetFragment<FragmentColorPickerBinding>() {
// TODO implement alpha slider and input fields
    companion object {
        fun createInstance() = ColorPickerFragment().apply {
            // TODO put default color as argument
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        binding.root.alpha = 0.2f // TODO make semi-transparent when selecting the color
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ) = onCreateView(inflater, container, FragmentColorPickerBinding::inflate)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog

        dialog.behavior.isDraggable = false
//        dialog.window?.setDimAmount(0f) // TODO remove dim on selecting the color

        return dialog
    }
}