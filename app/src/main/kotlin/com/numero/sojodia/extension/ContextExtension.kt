package com.numero.sojodia.extension

import android.content.Context

/**
 * @param fileName assetsに入っているファイルの名前
 *
 * @return String
 */
fun Context.readAssetsFile(fileName: String): String {
    return assets.open(fileName).reader(charset = Charsets.UTF_8).use { it.readText() }
}