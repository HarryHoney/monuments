package com.example.harpreet.visitorguide;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import org.json.JSONArray;

import eu.livotov.labs.android.camview.CameraLiveView;
import eu.livotov.labs.android.camview.camera.PictureProcessingCallback;

public class Camera extends AppCompatActivity implements SensorEventListener {

    private ImageView image;
    GPStracker gps;
    Location l;
    ProgressDialog dialog;
    private float currentDegree = 0f;
    private SensorManager mSensorManager;
    CameraLiveView cameraLiveView;
    PictureProcessingCallback callback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        cameraLiveView = findViewById(R.id.cam_camera);
        cameraLiveView.startCamera();

        //For compass setting
        image =  findViewById(R.id.compass);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        //For taking picture callback function
        callback = new PictureProcessingCallback() {
            @Override
            public void onShutterTriggered() {
//                Toast.makeText(MainActivity.this,"A",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onRawPictureTaken(byte[] rawData) {
//                Toast.makeText(MainActivity.this,"B",Toast.LENGTH_LONG).show();cameraLiveView.resumeDisplay();
            }

            @Override
            public void onPictureTaken(byte[] jpegData) {
                Bitmap img = BitmapUtils.convertCompressedByteArrayToBitmap(jpegData);

                cameraLiveView.startCamera();
            }
        };

    }



    public void SearchPlace(View view) {
        if(cameraLiveView.getController()!=null)
        cameraLiveView.getController().takePicture(callback);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        float degree = Math.round(event.values[0]);
        RotateAnimation ra = new RotateAnimation(

                degree,currentDegree,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f);
        ra.setDuration(210);
        ra.setFillAfter(true);
        image.startAnimation(ra);
        currentDegree = -degree;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
        SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        gps = new GPStracker(this);
        if(gps!=null) {
            dialog = ProgressDialog.show(this, "Hi User",
                    "Loading. Please wait...", true);
            l = gps.getLocation();
            String key = getIntent().getStringExtra("key");
            AndroidNetworking.get("heroku api")
                    .setPriority(Priority.MEDIUM)
                    .addQueryParameter("key",key)
                    .addQueryParameter("lat",l.getLatitude()+"")
                    .addQueryParameter("long",l.getLongitude()+"")
                    .build()
                    .getAsJSONArray(new JSONArrayRequestListener() {

                        @Override
                        public void onResponse(JSONArray response) {

                            Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.direction);

//                            Bitmap finalImage = addSpots(bm,);
                            dialog.dismiss();
                        }

                        @Override
                        public void onError(ANError anError) {
                        Toast.makeText(Camera.this, "Sorry some error occurred", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    });
        }
        else
        {
            Toast.makeText(this, "Unable to fetch location", Toast.LENGTH_SHORT).show();
            finish();
        }
    }


}
