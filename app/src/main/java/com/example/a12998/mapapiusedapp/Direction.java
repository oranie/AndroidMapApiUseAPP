package com.example.a12998.mapapiusedapp;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Direction extends ActionBarActivity{
    JSONObject mJsonBody;
    RequestQueue mRequestQueue;
    Context mContext;
    String mToEnd;
    ArrayList<Map<String,Double>> mStartList ;
    ArrayList<Map<String,Double>> mEndList ;
    ArrayList<String> mEncodedList;
    GoogleMap mMap;
    String mStatus;


    public Direction(Context context,GoogleMap map,String toEnd){
        mContext = context;
        mJsonBody = new JSONObject();
        mMap =  map;
        mToEnd = toEnd;
    }


    public Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>(){
        @Override
        public void onResponse(JSONObject response){

            mJsonBody = response;
            Log.d("Direction Response",mJsonBody.toString());

            DirectionJsonParser directionJsonParser = new DirectionJsonParser(mJsonBody);

            try {
                directionJsonParser.jsonParser();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(mContext,"探索に失敗しました。もう一度行き先を言って下さい",Toast.LENGTH_LONG).show();
                return;
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(mContext,"探索に失敗しました。もう一度行き先を言って下さい",Toast.LENGTH_LONG).show();
                return;
            }


            mStartList = directionJsonParser.getStartList();
            if(mStartList.isEmpty()){
                Toast.makeText(mContext,"探索に失敗しました。もう一度行き先を言って下さい",Toast.LENGTH_LONG).show();
            }
            mEndList = directionJsonParser.getStartList();
            mEncodedList = directionJsonParser.getmEncodedList();
            Log.d("TEST",mStartList.toString());
            Log.d("TEST",mEndList.toString());
            DirectionDecode directionDecode = new DirectionDecode();
            String encoded = "";

            PolylineOptions rectLine = new PolylineOptions();
            for (int i = 0; i < mStartList.size();i++){
                Log.d("Direction Line START",mStartList.get(i).get("lat").toString() + " : " + mStartList.get(i).get("lng").toString());
                Log.d("Direction Line END", mEndList.get(i).get("lat").toString() + " : " + mEndList.get(i).get("lng").toString());

                String encodeString = mEncodedList.get(i);
                Log.d("encode",encodeString);

                List<LatLng> decodeList = directionDecode.decodePoly(encodeString);
                Log.d("encode 2",decodeList.toString());

                for (LatLng location : decodeList){
                    rectLine.add(location)
                            .width(10)
                            .zIndex(1)
                            .color(Color.BLUE);
                }
                Log.d("MainActivity","mMap :" + mMap.toString());
                Log.d("MainActivity","PolylineOptions : " + rectLine.toString());
            }
            mMap.addPolyline(rectLine);

        }
    };

    public Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.d("RESPONSE","ERROR!!" + error.toString());
            Toast.makeText(mContext,"失敗しました。もう一度試して下さい",Toast.LENGTH_LONG).show();
        }
    };

    public void getDocument(Double latitude,Double longitude) {
        try {
            /*
            String url = "https://maps.googleapis.com/maps/api/directions/json?"
                    + "origin=" + String.valueOf(latitude) + "," + String.valueOf(longitude)
                    + "&destination=" + mToEnd
                    + "&sensor=false&units=metric&regin=jp&mode=train";
            Log.d("getDocument","url : " + url);
            */
            Uri.Builder builder = new Uri.Builder();
            builder.scheme("http")
                    .authority("maps.googleapis.com")
                    .path("/maps/api/directions/json")
                    .appendQueryParameter("origin",
                            String.valueOf(latitude) + "," + String.valueOf(longitude))
                    .appendQueryParameter("destination",
                            String.valueOf(mToEnd))
                    .appendQueryParameter("sensor", "false")
                    .appendQueryParameter("walking", "walking");
            String url = builder.toString();
            Log.d("getDocument","url : " + url);
            mRequestQueue = Volley.newRequestQueue(mContext);
            JsonObjectRequest volley = new JsonObjectRequest(Request.Method.GET,url, mJsonBody,listener,errorListener);
            mRequestQueue.add(volley);
            mRequestQueue.start();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}