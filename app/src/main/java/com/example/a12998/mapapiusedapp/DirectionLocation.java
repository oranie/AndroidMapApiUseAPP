package com.example.a12998.mapapiusedapp;

/**
 * Created by a12998 on 2014/09/11.
 */

public class DirectionLocation {

    private double latitude;

    private double longitude;

    public DirectionLocation() {

    }

    public DirectionLocation(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * @return the latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * @return the longitude
     */
    public double getLongitude() {
        return longitude;
    }
}