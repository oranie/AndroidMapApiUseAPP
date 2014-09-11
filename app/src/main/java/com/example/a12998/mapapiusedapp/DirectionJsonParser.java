package com.example.a12998.mapapiusedapp;


import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DirectionJsonParser {
    JSONObject mJson;
    String copyrights;
    ArrayList<Map<String,Double>> mStartList = new ArrayList<Map<String,Double>>();
    Map<String,Double> mStartValueMap = new HashMap<String, Double>();

    ArrayList<Map<String,Double>> mEndList = new ArrayList<Map<String,Double>>();
    Map<String,Double> mEndValueMap = new HashMap<String, Double>();

    ArrayList<String> mEncodedList = new ArrayList<String>();
    String mStatus;


    DirectionJsonParser(JSONObject json){
        mJson = json;
        copyrights = "copyrights";
    }
    public Void jsonParser() throws IOException, JSONException {
        try {

            JSONObject json = getmJson();
            JSONArray routesArray = json.getJSONArray("routes");
            JSONObject route = routesArray.getJSONObject(0);
            Log.d("JSON PARSER 0", route.toString());
            JSONArray legs = route.getJSONArray("legs");
            Log.d("JSON PARSER 1", legs.toString());
            JSONObject leg = legs.getJSONObject(0);
            Log.d("JSON PARSER 3", leg.toString());
            JSONObject durationObject = leg.getJSONObject("duration");
            Log.d("JSON PARSER 4", durationObject.toString());

            JSONArray steps = leg.getJSONArray("steps");
            Log.d("JSON PARSER 5", steps.toString());

            //mStartList = new ArrayList<Map<String,Double>>();
            //mStartValueMap = new HashMap<String, Double>();

            //mEndList = new ArrayList<Map<String,Double>>();
            //mEndValueMap = new HashMap<String, Double>();

            //mEncodedList = new ArrayList<String>();

            String encodeString = "";
            for (int i = 0 ; i < steps.length(); i++){
                //mStartValueMap = new HashMap<String, Double>();
                //mEndValueMap = new HashMap<String, Double>();
                String encodeStr = new String();
                //Log.d("JSON PARSER 6",steps.getJSONObject(i).toString());
                mStartValueMap.put("lat",steps.getJSONObject(i).getJSONObject("start_location").getDouble("lat"));
                mStartValueMap.put("lng",steps.getJSONObject(i).getJSONObject("start_location").getDouble("lng"));
                mEndValueMap.put("lat",steps.getJSONObject(i).getJSONObject("start_location").getDouble("lat"));
                mEndValueMap.put("lng",steps.getJSONObject(i).getJSONObject("start_location").getDouble("lng"));
                mStartList.add(i, mStartValueMap);
                mEndList.add(i, mEndValueMap);

                encodeStr = steps.getJSONObject(i).getJSONObject("polyline").getString("points");
                Log.d("Points get",encodeStr);
                mEncodedList.add(i, encodeStr);

                Log.d("JSON PARSER 7 START",steps.getJSONObject(i).getJSONObject("start_location").toString());
                Log.d("JSON PARSER 7 END  ",steps.getJSONObject(i).getJSONObject("end_location").toString());
            }
            Log.d("JSON PARSER 8",mStartList.toString());
            return null;


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  null;

    }

    public JSONObject getmJson() {
        return mJson;
    }

    public ArrayList<Map<String, Double>> getStartList(){
        Log.d("ParserGetStartList",mStartList.toString());

        return  mStartList;
    }

    public ArrayList<Map<String, Double>> getEndList(){
        Log.d("ParserGetEndList",mStartList.toString());

        return  mEndList;
    }

    public ArrayList<String> getmEncodedList(){
        Log.d("ParserGetEndList",mEncodedList.toString());

        return  mEncodedList;
    }

}
