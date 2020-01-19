package com.example.harpreet.visitorguide;


import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.harpreet.visitorguide.UtilsFolder.MyListAdapter;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class places extends AppCompatActivity {

//    private double currentDegree=70.12;
    ListView l;
    JSONArray list1 ;
    JSONArray list2 ;
      List<String> names ;
      List<String> counts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);
        l = findViewById(R.id.placeList);

        final JSONArray[] sortedJsonArray = {new JSONArray()};
        JSONArray jsonArray=new JSONArray();
        AndroidNetworking.get("https://us-central1-monuments-5eabc.cloudfunctions.net/app/trending")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("resp :", (response.get("name").toString()));
                            list1= (JSONArray) response.get("name");
                            list2= (JSONArray) response.get("count");
                            names = new ArrayList<String>();
                            counts=new ArrayList<String>();
                            for(int i = 0; i < list1.length(); i++) {
                                names.add((String) list1.get(i));
                                counts.add(String.valueOf(list2.get(i)));
                            }
                            Log.d("list1",names.toString());
                            display();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

//                        for (int i = 0; i < response.length(); i++) {
//                            try {
////                                jsonValues.add(response.getJSONObject(i));
//
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
////                        Collections.sort( jsonValues, new Comparator<JSONObject>() {
////                            @Override
////                            public int compare(JSONObject a, JSONObject b) {
////                                Double valA = new Double(0);
////                                Double valB = new Double(0);
////                                try {
////                                    valA =  a.getDouble("distance");
////                                    valB =  b.getDouble("distance");
////                                }
////                                catch (JSONException e) {
////                                    //do something
////                                }
////
////                                return valA.compareTo(valB);
////                                //if you want to change the sort order, simply use the following:
////                                //return -valA.compareTo(valB);
////                            }
////                        });
//
////                        for (int i = 0; i < response.length(); i++) {
////                            sortedJsonArray.put(jsonValues.get(i));
////                        }
////                        Log.d("list1", jsonValues.toString());
////                        int maxItemsToDisplay;
////                        if(sortedJsonArray.length() > 3)
////                            maxItemsToDisplay = 3;
////                        else
////                            maxItemsToDisplay =sortedJsonArray.length() ;
//
//
////                        for(int i=0;i<=maxItemsToDisplay;i++)
////                        {
////                            try {
////                                jo = sortedJsonArray.getJSONObject(i);
////                                Log.d("nitj", String.valueOf(jo));
////                            } catch (JSONException e) {
////                                e.printStackTrace();
////                            }
////                            try {
////                                Log.d("nitj", String.valueOf(jo.getDouble("degrees")));
////                                if (jo.getDouble("degrees")>=currentDegree-20 && jo.getDouble("degrees")<=currentDegree+20)
////                                {
////                                    list1.add(jo.getString("name"));
////                                    list2.add(jo.getString("distance")+" visits");
////                                }
////                            } catch (JSONException e) {
////                                e.printStackTrace();
////                            }
////                        }
//                        try {
//                            list1= (String[]) response.get("name");
//                            list2= (int[]) response.get("count");
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                        Log.d("list", list1.toString());
//                        display();
//
//                    }
//                    @Override
//                    public void onError(ANError error) {
//                        Toast.makeText(places.this, "Dinesh:"+error.toString(), Toast.LENGTH_SHORT).show();
//                    }
//                });

                    //        try {
//                            list1= (JSONArray) jsonObject.get("name");
//                            list2= (JSONArray) jsonObject.get("count");
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                        Log.d("list", list1.toString());
//                        display();
                    @Override
                    public void onError(ANError anError) {

                    }

                });
    }

        private void display() {
        MyListAdapter adapter=new MyListAdapter(this, names, counts);
        l.setAdapter(adapter);
    }

//    @Override
//    public void onSensorChanged(SensorEvent event) {
//        float degree = Math.round(event.values[0]);
//        RotateAnimation ra = new RotateAnimation(
//
//                degree, (float) currentDegree,
//                Animation.RELATIVE_TO_SELF, 0.5f,
//                Animation.RELATIVE_TO_SELF,
//                0.5f);
//        ra.setDuration(210);
//        ra.setFillAfter(true);
////        image.startAnimation(ra);
//        currentDegree = -degree;
////        Log.d("nitj",Float.toString(currentDegree));
//    }
//
//    @Override
//    public void onAccuracyChanged(Sensor sensor, int i) {
//
//    }
}
