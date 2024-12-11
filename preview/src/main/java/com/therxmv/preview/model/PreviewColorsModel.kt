package com.therxmv.preview.model

data class PreviewColorsModel(
    val accent: Int,
    val background: Int,
    val previewBackground: Int,
    val appbarColors: AppbarColors,
    val listColors: ListColors,
    val chatColors: ChatColors,
)

data class AppbarColors(
    val appbarIcon: Int,
    val appbarTitle: Int,
    val appbarSubtitle: Int,
    val appbarAvatar: Int,
)