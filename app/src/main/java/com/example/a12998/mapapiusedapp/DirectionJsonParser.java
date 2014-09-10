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
    ArrayList<Map<String,Double>> mStartList ;
    Map<String,Double> mStartValueMap ;

    ArrayList<Map<String,Double>> mEndList ;
    Map<String,Double> mEndValueMap ;


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

            mStartList = new ArrayList<Map<String,Double>>();
            mStartValueMap = new HashMap<String, Double>();

            mEndList = new ArrayList<Map<String,Double>>();
            mEndValueMap = new HashMap<String, Double>();


            for (int i = 0 ; i < steps.length(); i++){
                //Log.d("JSON PARSER 6",steps.getJSONObject(i).toString());
                mStartValueMap.put("lat",steps.getJSONObject(i).getJSONObject("start_location").getDouble("lat"));
                mStartValueMap.put("lng",steps.getJSONObject(i).getJSONObject("start_location").getDouble("lng"));
                mEndValueMap.put("lat",steps.getJSONObject(i).getJSONObject("start_location").getDouble("lat"));
                mEndValueMap.put("lng",steps.getJSONObject(i).getJSONObject("start_location").getDouble("lng"));
                mStartList.add(i, mStartValueMap);
                mEndList.add(i,mEndValueMap);
                Log.d("JSON PARSER 7",steps.getJSONObject(i).getJSONObject("start_location").toString());
                Log.d("JSON PARSER 7",steps.getJSONObject(i).getJSONObject("end_location").toString());
            }
            Log.d("JSON PARSER 8",mStartList.toString());


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    public JSONObject getmJson() {
        return mJson;
    }

    public ArrayList<Map<String, Double>> getStartList(){
        return  mStartList;
    }

    public ArrayList<Map<String, Double>> getEndList(){
        return  mEndList;
    }

}
