package com.numero.sojodia.model

import com.numero.sojodia.R

enum class Reciprocate(val titleStringRes: Int) {

    GOING(
            R.string.going_to_school
    ),

    RETURN(
            R.string.coming_home
    );

    companion object {
        fun getReciprocate(ordinal: Int): Reciprocate = if (Reciprocate.values().size <= ordinal) {
            GOING
        } else Reciprocate.values()[ordinal]
    }
}
