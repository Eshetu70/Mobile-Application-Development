package com.example.classevaluation09;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class DataServices {

    private static final ArrayList<LatLng> path = new ArrayList<LatLng>() {{
        add(new LatLng(35.10418, -80.88821333333334));
        add(new LatLng(35.115415, -80.90126000000001));
        add(new LatLng(35.140125, -80.932845));
        add(new LatLng(35.155845, -80.949325));
        add(new LatLng(35.15809, -80.957565));
        add(new LatLng(35.17829833333334, -80.97198333333334));
        add(new LatLng(35.208038333333334, -80.97061000000001));
        add(new LatLng(35.236085, -80.97061000000001));
        add(new LatLng(35.261878333333335, -80.96786333333333));
        add(new LatLng(35.287665, -80.96511833333334));
        add(new LatLng(35.300555, -80.95413166666667));
        add(new LatLng(35.321286666666666, -80.94245833333333));
        add(new LatLng(35.32857, -80.92185833333333));
        add(new LatLng(35.33697333333334, -80.89988666666666));
        add(new LatLng(35.351535, -80.86624));
        add(new LatLng(35.358815, -80.85250833333333));
        add(new LatLng(35.362175, -80.81886166666666));
        add(new LatLng(35.366655, -80.79551666666666));
        add(new LatLng(35.358815, -80.74607833333333));
        add(new LatLng(35.354335, -80.74401833333333));
    }};

    public static ArrayList<LatLng> getPath() {
        return path;
    }

}
