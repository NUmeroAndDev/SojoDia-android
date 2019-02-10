package com.numero.sojodia.model

enum class Reciprocate(private val shortcutValue: String) {

    GOING("going_to_school"),

    RETURN("coming_home");

    companion object {

        fun findReciprocate(position: Int): Reciprocate = if (Reciprocate.values().size <= position) {
            GOING
        } else Reciprocate.values()[position]

        fun findReciprocate(shortCut: String?): Reciprocate {
            return Reciprocate.values().find { it.shortcutValue == shortCut } ?: GOING
        }
    }
}
