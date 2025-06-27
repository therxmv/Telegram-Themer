package com.therxmv.telegramthemer.ui.editor.help

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.therxmv.telegramthemer.R
import com.therxmv.telegramthemer.databinding.FragmentHelpDialogBinding
import com.therxmv.telegramthemer.ui.base.BaseBindingDialogFragment

class HelpDialogFragment : BaseBindingDialogFragment<FragmentHelpDialogBinding>() {

    companion object {
        private const val IS_ADVANCED = "isAdvanced"

        fun createInstance(isAdvanced: Boolean) = HelpDialogFragment().apply {
            arguments = Bundle().apply {
                putBoolean(IS_ADVANCED, isAdvanced)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ) = onCreateView(inflater, container, FragmentHelpDialogBinding::inflate)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) // removes default background

        setUpDescription()

        binding.understandButton.setOnClickListener {
            dismiss()
        }
    }

    private fun setUpDescription() {
        val isAdvanced = arguments?.getBoolean(IS_ADVANCED)
        val text = if (isAdvanced == true) {
            R.string.help_dialog_advanced_instruction
        } else {
            R.string.help_dialog_export_instruction
        }
        binding.description.setText(text)
    }
}