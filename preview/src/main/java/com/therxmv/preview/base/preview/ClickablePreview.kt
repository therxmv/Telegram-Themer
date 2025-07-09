package com.therxmv.preview.base.preview

import android.view.View
import com.therxmv.preview.utils.AtthemePreviewKeys

interface ClickablePreview {
    fun setColorPickerAction(openColorPicker: View.(AtthemePreviewKeys) -> Unit)
}