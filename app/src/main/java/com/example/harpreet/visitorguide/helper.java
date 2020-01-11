package com.example.harpreet.visitorguide;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.example.harpreet.visitorguide.sampledata.Server;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class helper extends AppCompatActivity  {


//    ImageView v;

//    Bitmap addSpots(Bitmap img, double data[][]){
//        for(int i=0;i<data[0].length;i++){
//            double x = 392.5 + data[0][i]*Math.sin(Math.toRadians(data[1][i]))*((double)511/(double)2000);
//            double y = 383 + data[0][i]*Math.cos(Math.toRadians(data[1][i]))*((double)511/(double)2000);
//
//            img.setPixel((int)x,(int)y,Color.WHITE);
//            img.setPixel((int)x+1,(int)y,Color.WHITE);
//            img.setPixel((int)x,(int)y-1,Color.WHITE);
//            img.setPixel((int)x+1,(int)y-1,Color.WHITE);
//        }
//        return img;
//    }

    TextView v;ImageView vv;
    String string="";

    Bitmap addSpots(Bitmap img, double data[][]){
        for(int i=0;i<data[0].length;i++){
            double x = 392.5 + data[0][i]*Math.sin(Math.toRadians(data[1][i]))*((double)450/(double)2000);
            double y = 383 + data[0][i]*Math.cos(Math.toRadians(data[1][i]))*((double)450/(double)2000);
//            string +=x+","+y+" ";
            img.setPixel((int)x,(int)y,Color.WHITE);
            img.setPixel((int)x+1,(int)y,Color.WHITE);
            img.setPixel((int)x,(int)y-1,Color.WHITE);
            img.setPixel((int)x+1,(int)y-1,Color.WHITE);
        }
        return img;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helper);

        v = findViewById(R.id.textView2);

        AndroidNetworking.get("http://ec2-3-6-91-12.ap-south-1.compute.amazonaws.com:8080/checkaws")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(helper.this, response, Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onError(ANError error) {
                        Toast.makeText(helper.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

        }

    }



