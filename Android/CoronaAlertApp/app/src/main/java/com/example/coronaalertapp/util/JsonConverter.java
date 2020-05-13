package com.example.coronaalertapp.util;

import android.util.Log;

import com.example.coronaalertapp.vo.CoronaLocationVO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.coronaalertapp.NearbyActivity.maplist;

public class JsonConverter {

    public static ArrayList<CoronaLocationVO> convertToCoronaLocation(String s) {
        Log.d("===", s.trim());

        maplist = new ArrayList<>();


        JSONArray ja = null;
        try {
            ja = new JSONArray(s);
            JSONArray ja_location = null;
            for (int i = 0; i < ja.length(); i++) {
                JSONObject jo = ja.getJSONObject(i);
                String id = jo.getString("id");
                String name = jo.getString("name");
                String sex = jo.getString("sex");
                int age = jo.getInt("age");
                String nationality = jo.getString("nationality");
                int meet = jo.getInt("meet");
                int risk = jo.getInt("risk");
                String img = jo.getString("img");
                ja_location = jo.getJSONArray("location");

                for (int j = 0; j < ja_location.length(); j++) {
                    JSONObject jo_location = ja_location.getJSONObject(j);
                    int no = jo_location.getInt("no");
                    String name_location = jo_location.getString("name");
                    Double lat = jo_location.getDouble("lat");
                    Double lon = jo_location.getDouble("lon");
                    CoronaLocationVO map = new CoronaLocationVO(no, name_location, lat, lon, name);
                    maplist.add(map);
//                    Log.d("====", "SecondActivity : maplist.toString() : " + maplist.toString());
                }
//                Item item = new Item(id, name, sex, age, nationality, meet, risk, img, maplist);
//                Log.d("====", "SecondActivity : item.getArrayList().toString() : " + item.getArrayList().toString());
//                list.add(item);
//                        Log.d("====", "SecondActivity : list.get(i).getArrayList().toString() : " + list.get(i).getArrayList().toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return maplist;
    }

}
