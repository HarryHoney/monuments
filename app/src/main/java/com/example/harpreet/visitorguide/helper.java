package com.example.harpreet.visitorguide;

import android.Manifest;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import edu.arbelkilani.compass.Compass;
import edu.arbelkilani.compass.CompassListener;

public class helper extends AppCompatActivity  {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helper);



        Compass compass = findViewById(R.id.compass_1);
        compass.setListener(new CompassListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                Log.d("Compass", "onAccuracyChanged : sensor : " + sensor);
                Log.d("Compass", "onAccuracyChanged : accuracy : " + accuracy);
            }

        });


    }


}
