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
        vv = findViewById(R.id.testing);
        v=  findViewById(R.id.textView2);

        File file = new File("/storage/emulated/0/MyFolder/forML.jpg");
        if(file.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            vv.setImageBitmap(myBitmap);
            v.setText("dfsasc");

        }

    }


}
