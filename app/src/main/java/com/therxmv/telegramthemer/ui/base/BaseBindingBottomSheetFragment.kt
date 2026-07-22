package com.therxmv.telegramthemer.ui.base

import android.app.Dialog
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.FragmentManager
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.therxmv.telegramthemer.ui.base.BaseBindingDialogFragment.Companion.DEFAULT_DIM

abstract class BaseBindingBottomSheetFragment<B: ViewBinding> : BottomSheetDialogFragment() {

    private var _binding: B? = null
    protected val binding: B get() = requireNotNull(_binding)

    private var _dialog: BottomSheetDialog? = null
    protected val dialog: BottomSheetDialog get() = requireNotNull(_dialog)

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

        return requireNotNull(_dialog)
    }

    override fun onStart() {
        super.onStart()
        forceFullScreenInLandscape()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun show(manager: FragmentManager, tag: String?) {
        if (manager.isStateSaved) return

        super.show(manager, tag)
    }

    private fun forceFullScreenInLandscape() {
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            val bottomSheet = dialog.findViewById<View>(
                com.google.android.material.R.id.design_bottom_sheet
            ) as? FrameLayout ?: return

            bottomSheet.layoutParams.height = FrameLayout.LayoutParams.MATCH_PARENT

            BottomSheetBehavior.from(bottomSheet).run {
                state = BottomSheetBehavior.STATE_EXPANDED
                skipCollapsed = true
                peekHeight = bottomSheet.height
            }
        }
    }
}