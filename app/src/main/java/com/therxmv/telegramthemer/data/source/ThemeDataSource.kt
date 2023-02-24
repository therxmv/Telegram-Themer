package com.therxmv.telegramthemer.data.source

import com.therxmv.telegramthemer.data.models.ThemeModel

interface ThemeDataSource {
    fun load(): ThemeModel
    fun save(theme: ThemeModel)
}