package com.therxmv.telegramthemer.data.source

import com.therxmv.telegramthemer.domain.model.ThemeModel

interface ThemeDataSource {
    fun load(): ThemeModel
    fun save(theme: ThemeModel)
}