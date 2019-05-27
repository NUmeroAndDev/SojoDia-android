package com.numero.sojodia.model

enum class AppTheme {
    LIGHT,
    DARK,
    SYSTEM_DEFAULT;

    companion object {
        fun from(entry: String): AppTheme {
            return when (entry) {
                "0" -> LIGHT
                "1" -> DARK
                "2" -> SYSTEM_DEFAULT
                else -> throw Exception("Not defined theme entry")
            }
        }
    }
}