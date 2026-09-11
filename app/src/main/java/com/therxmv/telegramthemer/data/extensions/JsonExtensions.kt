package com.therxmv.telegramthemer.data.extensions

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.Reader

/**
 * Parses a flat JSON object (template) into a Map<String, String>. Gson reads
 * every JSON value (string, boolean, ...) as its string form here, which is
 * what lets a template mix role-name strings with literal values (e.g. iOS's
 * `"dark": false`) under one map type.
 */
fun Reader.jsonToMap(): Map<String, String> {
    val type = object : TypeToken<Map<String, String>>() {}.type
    return Gson().fromJson(this, type)
}
