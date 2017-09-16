package com.numero.sojodia.model;

import com.numero.sojodia.R;

public enum Route {

    TK(
            R.string.station_tk
    ),

    TND(
            R.string.station_tnd
    );

    private int stationStringRes;

    Route(int stationStringRes) {
        this.stationStringRes = stationStringRes;
    }

    public int getStationStringRes() {
        return stationStringRes;
    }

    public static Route getRoute(int ordinal) {
        if (Route.values().length <= ordinal) {
            return TK;
        }
        return Route.values()[ordinal];
    }
}
