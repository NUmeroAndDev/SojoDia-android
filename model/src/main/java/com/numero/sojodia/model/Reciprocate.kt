package com.numero.sojodia.model

enum class Reciprocate(
        private val shortcutValue: String
) {

    GOING("going_to_school"),

    RETURN("coming_home");

    companion object {
        // FIXME
        fun from(shortcut: String?): Reciprocate {
            return Reciprocate.values().find { it.shortcutValue == shortcut } ?: GOING
        }
    }
}
