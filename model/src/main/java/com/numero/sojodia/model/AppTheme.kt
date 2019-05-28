package com.numero.sojodia.model

enum class AppTheme(val key: String) {

    LIGHT("LIGHT"),
    DARK("DARK"),
    SYSTEM_DEFAULT("SYSTEM_DEFAULT");

    companion object {
        fun from(key: String): AppTheme {
            return requireNotNull(values().find { it.key == key }) {
                "Not allowed key"
            }
        }
    }
}