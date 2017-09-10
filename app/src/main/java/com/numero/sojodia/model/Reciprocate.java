package com.numero.sojodia.model;

import com.numero.sojodia.R;

public enum Reciprocate {
    
    GOING(
            R.string.going_to_school
    ),

    RETURN(
            R.string.coming_home
    );

    private int titleStringRes;

    Reciprocate(int titleStringRes) {
        this.titleStringRes = titleStringRes;
    }

    public int getTitleStringRes() {
        return titleStringRes;
    }

    public static Reciprocate getReciprocate(int ordinal) {
        if (Reciprocate.values().length <= ordinal) {
            return GOING;
        }
        return Reciprocate.values()[ordinal];
    }
}
