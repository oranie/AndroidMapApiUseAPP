package com.example.a12998.mapapiusedapp;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;


public class MyActivity extends ActionBarActivity implements View.OnClickListener, TextToSpeech.OnInitListener{

    LocationManager manager;
    LocationListener listener;
    public double mLongitude;
    public double mOldLongitude = 0.0;
    public int mDiffLongitude;

    public double mLatitude;
    public double mOldLatitude = 0.0;
    public int mDiffLatitude;
    private static final int REQUEST_CODE = 0;
    TextToSpeech tts = null;
    GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        Log.d("TEST","onCreate START");

        mMap =  ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();

        Button button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener( this );

        tts = new TextToSpeech(this, this);

        manager = (LocationManager)this.getSystemService(LOCATION_SERVICE);
        Log.d("TEST","manager : " + manager.toString());
        listener = new LocationListener() {
            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                Log.d("TEST","onStatusChanged");
            }
            @Override
            public void onProviderEnabled(String provider) {
                Log.d("TEST","onProviderEnabled");
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
                //Log.d("TEST","diff Longitude : " + mDiffLongitude + " diff Latitude : " + mDiffLatitude);

                if ( mDiffLongitude!= 0 || mDiffLatitude != 0 ){
                    mLongitude = location.getLongitude();
                    mLatitude = location.getLatitude();
                    mMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(mLatitude,mLongitude)));
                    Log.d("onLocationChanged update","Longitude : " + mLongitude + " Latitude : " + mLatitude);
                }
                mOldLongitude = mLongitude;
                mOldLatitude = mLatitude;
                Log.d("onLocationChanged","Longitude : " + mLongitude + " Latitude : " + mLatitude);
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

    @Override
    public void onClick(View v) {
        try {
            Intent intent = new Intent(
                    RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "音声認識中です");

            startActivityForResult(intent, REQUEST_CODE);
        } catch (ActivityNotFoundException e) {
            // 非対応の場合
            Toast.makeText(this, "音声入力に非対応です。", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            String resultsString = "";
            mMap.clear();

            ArrayList<String> results = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);

            //for (int i = 0; i < results.size(); i++) {
            for (int i = 0; i < 1; i++) {
                resultsString += results.get(i);
            }
            Direction direction = new Direction(this,mMap,resultsString);
            direction.getDocument(mLatitude,mLongitude);

            Toast.makeText(this, resultsString + "に向かいます", Toast.LENGTH_LONG).show();

        } else{
            Intent intent = new Intent(
                    RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "音声認識中です");
            startActivityForResult(intent, REQUEST_CODE);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onInit(int status) {

    }
}
