package com.example.a12998.mapapiusedapp;

import android.content.Intent;
import android.graphics.Color;
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

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;


public class MyActivity extends ActionBarActivity implements View.OnClickListener, TextToSpeech.OnInitListener {
    LocationManager manager;
    LocationListener listener;
    public double mLongitude;
    public double mOldLongitude;
    public int mDiffLongitude;

    public double mLatitude;
    public double mOldLatitude;
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
                //Log.d("TEST","diff Longitude : " + mDiffLongitude + " diff Latitude : " + mDiffLatitude);

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

    @Override
    public void onClick(View v) {
        Direction direction = new Direction(this);
        direction.getDocument();
        PolylineOptions rectLine = new PolylineOptions()
                .add(new LatLng(35.656452, 139.694853), new LatLng(34.656452, 138.694853))
                .width(10)
                .zIndex(1)
                .color(Color.BLUE);
        Log.d("MainActivity","mMap :" + mMap.toString());
        Log.d("MainActivity","PolylineOptions : " + rectLine.toString());

        mMap.addPolyline(rectLine);


        /*
        try {
            // "android.speech.action.RECOGNIZE_SPEECH" を引数にインテント作成
            Intent intent = new Intent(
                    RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

            // 「お話しください」の画面で表示される文字列
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "音声認識中です");

            // 音声入力開始
            startActivityForResult(intent, REQUEST_CODE);
        } catch (ActivityNotFoundException e) {
            // 非対応の場合
            Toast.makeText(this, "音声入力に非対応です。", Toast.LENGTH_LONG).show();
        }
        */
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 自分が投げたインテントであれば応答する
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            String resultsString = "";

            // 結果文字列リスト
            ArrayList<String> results = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);

            for (int i = 0; i< results.size(); i++) {
                // ここでは、文字列が複数あった場合に結合しています
                resultsString += results.get(i);
            }

            // トーストを使って結果を表示
            Toast.makeText(this, resultsString, Toast.LENGTH_LONG).show();

        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onInit(int status) {

    }
}
