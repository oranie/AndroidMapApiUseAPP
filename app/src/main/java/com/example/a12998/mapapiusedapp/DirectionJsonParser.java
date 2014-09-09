package com.example.a12998.mapapiusedapp;


import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

public class DirectionJsonParser {
    JSONObject mJson;
    String copyrights;
    DirectionJsonParser(JSONObject json){
        mJson = json;
        copyrights = "copyrights";
    }

    public String jsonParser() throws IOException {
        String json = getmJson().toString();
        ObjectMapper mapper = new ObjectMapper();
        Map<String,Integer> result = mapper.readValue(json, Map.class);
        System.out.println("result1.toString() : " + result.toString());
        return copyrights.toString();
    }


    public JSONObject getmJson() {
        return mJson;
    }
}
