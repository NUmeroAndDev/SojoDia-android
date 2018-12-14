package com.numero.sojodia.model

import com.numero.sojodia.R

enum class Reciprocate(
        val titleStringRes: Int,
        private val shortcutValue: String
) {

    GOING(
            R.string.going_to_school,
            "going_to_school"
    ),

    RETURN(
            R.string.coming_home,
            "coming_home"
    );

    companion object {

        fun findReciprocate(position: Int): Reciprocate = if (Reciprocate.values().size <= position) {
            GOING
        } else Reciprocate.values()[position]

        fun findReciprocate(shortCut: String?): Reciprocate {
            return Reciprocate.values().find { it.shortcutValue == shortCut } ?: GOING
        }
    }
}
