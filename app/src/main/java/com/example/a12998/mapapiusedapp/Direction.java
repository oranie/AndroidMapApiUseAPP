package com.example.a12998.mapapiusedapp;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class Direction {
    JSONObject mJsonBody;
    RequestQueue mRequestQueue;
    Context mContext;
    ArrayList<Map<String,Double>> mStartList ;
    ArrayList<Map<String,Double>> mEndList ;


    public Direction(Context context){
        mContext = context;
        mJsonBody = new JSONObject();
    }


    public Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>(){
        @Override
        public void onResponse(JSONObject response){
            mJsonBody = response;
            Log.d("RESPONSE",mJsonBody.toString());
            DirectionJsonParser directionJsonParser = new DirectionJsonParser(mJsonBody);
            try {
                directionJsonParser.jsonParser();
            } catch (IOException e) {
                //e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    public Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.d("RESPONSE","ERROR!!" + error.toString());
        }
    };

    //public Document getDocument(LatLng frompos, LatLng topos) {
    public void getDocument() {
        try {
            /*
            LatLng frompos = new LatLng(35.656452, 139.694853);
            LatLng topos = new LatLng(34.656452, 138.694853);
            String url = "https://maps.googleapis.com/maps/api/directions/json?"
                + "origin=" + frompos.latitude + "," + frompos.longitude
                + "&destination=" + topos.latitude + "," + topos.longitude
                + "&sensor=false&units=metric&mode=walking";
            */
            String url = "https://maps.googleapis.com/maps/api/directions/json?"
                    + "origin=TOKYO"
                    + "&destination=SAPPORO"
                    + "&sensor=false&units=metric&regin=jp&mode=train";
            mRequestQueue = Volley.newRequestQueue(mContext);
            //GetRouteInfo volley = new JsonObjectRequest(Request.Method.GET, url, listener,errorListener);
            JsonObjectRequest volley = new JsonObjectRequest(Request.Method.GET,url, mJsonBody,listener,errorListener);
            mRequestQueue.add(volley);
            mRequestQueue.start();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public JSONObject getmJsonBody() {
        return mJsonBody;
    }
}