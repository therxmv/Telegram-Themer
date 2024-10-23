package com.therxmv.telegramthemer.ui.editor.options

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckBox
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.therxmv.telegramthemer.R
import com.therxmv.telegramthemer.databinding.FragmentMoreOptionsBinding
import com.therxmv.telegramthemer.ui.base.BaseBindingBottomSheetFragment
import com.therxmv.telegramthemer.ui.editor.data.Styles
import com.therxmv.telegramthemer.ui.editor.data.ThemeState
import com.therxmv.telegramthemer.utils.isMonetAvailable
import com.therxmv.telegramthemer.utils.toVisibility

class MoreOptionsFragment : BaseBindingBottomSheetFragment<FragmentMoreOptionsBinding>() {

    companion object {
        private const val DEFAULT_DIM = 0.15f
        private const val CURRENT_STATE = "CurrentState"

        fun createInstance(currentState: ThemeState) = MoreOptionsFragment().apply {
            arguments = Bundle().apply {
                putParcelable(CURRENT_STATE, currentState)
            }
        }
    }

    private var dialog: BottomSheetDialog? = null
    private var themeState: ThemeState? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ) = onCreateView(inflater, container, FragmentMoreOptionsBinding::inflate)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog

        dialog?.behavior?.isDraggable = false
        dialog?.window?.setDimAmount(DEFAULT_DIM)

        return dialog!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        themeState = arguments?.getParcelable(CURRENT_STATE) as ThemeState?
        themeState?.let {
            setUpDropDown(it)
            updateCheckBoxes(it)
            initCheckBoxListeners()
        }
    }

    private fun setUpDropDown(themeState: ThemeState) {
        binding.selectorItems.setText(themeState.style.label)
        val styles = Styles.entries.map { it.label }
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, styles)
        binding.selectorItems.setAdapter(arrayAdapter)

        binding.selectorItems.setOnDismissListener {
            binding.styleSelector.clearFocus()
        }
        binding.selectorItems.setOnItemClickListener { _, _, _, _ ->
            val label = binding.styleSelector.editText?.text.toString()
            val style = Styles.entries.firstOrNull { it.label == label }
            style?.let {
                this.themeState = this.themeState?.copy(
                    style = it,
                )
            }
            notifyAboutChanges()
        }
    }

    private fun updateCheckBoxes(themeState: ThemeState) {
        with(themeState) {
            binding.darkCheckBox.isChecked = isDark
            binding.amoledCheckBox.isChecked = isAmoled
            binding.gradientCheckBox.isChecked = isGradient

            binding.monetCheckBox.isChecked = isMonet
            binding.monetCheckBox.visibility = isMonetAvailable().toVisibility()
        }
    }

    private fun initCheckBoxListeners() {
        binding.monetCheckBox.setOnClickListener {
            it as CheckBox
            themeState = themeState?.copy(
                accent = ContextCompat.getColor(requireContext(), R.color.theme_accent1_200),
                isMonet = it.isChecked,
            )
            notifyAboutChanges()
        }

        binding.gradientCheckBox.setOnClickListener {
            it as CheckBox
            themeState = themeState?.copy(
                isGradient = it.isChecked,
            )
            notifyAboutChanges()
        }

        binding.amoledCheckBox.setOnClickListener {
            it as CheckBox

            val isAmoled = it.isChecked
            val isDark = binding.darkCheckBox.isChecked
                .takeIf { isAmoled.not() } ?: true

            themeState = themeState?.copy(
                isDark = isDark,
                isAmoled = isAmoled,
            )
            binding.darkCheckBox.isChecked = isDark

            notifyAboutChanges()
        }

        binding.darkCheckBox.setOnClickListener {
            it as CheckBox

            val isDark = it.isChecked
            val isAmoled = binding.amoledCheckBox.isChecked
                .takeIf { isDark } ?: false

            themeState = themeState?.copy(
                isDark = isDark,
                isAmoled = isAmoled,
            )
            binding.amoledCheckBox.isChecked = isAmoled

            notifyAboutChanges()
        }
    }

    private fun notifyAboutChanges() {
        themeState?.let {
            (requireActivity() as? MoreOptionsSubscriber)?.onPropertyChange(it)
        }
    }
}