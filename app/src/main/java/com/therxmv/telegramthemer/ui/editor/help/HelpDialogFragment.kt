package com.therxmv.telegramthemer.ui.editor.help

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.therxmv.telegramthemer.databinding.FragmentHelpDialogBinding
import com.therxmv.telegramthemer.ui.base.BaseBindingDialogFragment

class HelpDialogFragment : BaseBindingDialogFragment<FragmentHelpDialogBinding>() {

    companion object {
        fun createInstance() = HelpDialogFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ) = onCreateView(inflater, container, FragmentHelpDialogBinding::inflate)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) // removes default background

        binding.understandButton.setOnClickListener {
            dismiss()
        }
    }
}