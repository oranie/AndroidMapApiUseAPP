package com.example.a12998.mapapiusedapp;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;


public class MyActivity extends ActionBarActivity {
    LocationManager manager;
    LocationListener listener;
    public double mLongitude;
    public double mOldLongitude;
    public int mDiffLongitude;

    public double mLatitude;
    public double mOldLatitude;
    public int mDiffLatitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        Log.d("TEST","onCreate START");

        manager = (LocationManager)this.getSystemService(LOCATION_SERVICE);
        Log.d("TEST","manager : " + manager.toString());
        listener = new LocationListener() {
            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }
            @Override
            public void onProviderEnabled(String provider) {
            }
            @Override
            public void onProviderDisabled(String provider) {
            }
            @Override
            public void onLocationChanged(Location location) {
                mLongitude = location.getLongitude();
                mLatitude = location.getLatitude();
                mDiffLongitude = (int)(mOldLongitude - mLongitude);
                mDiffLatitude = (int)(mOldLatitude - mLatitude);
                Log.d("TEST","diff Longitude : " + mDiffLongitude + " diff Latitude : " + mDiffLatitude);

                if ( mDiffLongitude!= 0 || mDiffLatitude != 0 ){
                    mLongitude = location.getLongitude();
                    mLatitude = location.getLatitude();
                    //Log.d("TEST","Longitude : " + mLongitude + " Latitude : " + mLatitude);
                }

                mOldLongitude = mLongitude;
                mOldLatitude = mLatitude;

            }
        };
    }
    @Override
    protected void onResume() {
        super.onResume();
        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, listener);
    }
    @Override
    protected void onPause(){
        super.onPause();
        manager.removeUpdates(listener);
    }



}