package com.example.evaluation10;

import org.json.JSONException;
import org.json.JSONObject;

public class Location {
    String lat, lng;
/*
 "geometry" : {
            "location" : {
               "lat" : -33.8587323,
               "lng" : 151.2100055
            },
 */
    public Location() {
    }

    public Location(JSONObject jsonObject) throws JSONException {
    JSONObject mainJson = jsonObject.getJSONObject("geometry")
            .getJSONObject("location");
    this.lat =mainJson.getString("lat");
    this.lng = mainJson.getString("lng");

    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }
}
