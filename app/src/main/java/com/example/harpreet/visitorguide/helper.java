package com.example.harpreet.visitorguide;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class helper extends AppCompatActivity  {


    ImageView v;

    Bitmap addSpots(Bitmap img, double data[][]){
        for(int i=0;i<data[0].length;i++){
            double x = data[0][i]*Math.sin(Math.toRadians(data[1][i]))*((double)38/(double)2000);
            double y = data[0][i]*Math.cos(Math.toRadians(data[1][i]))*((double)38/(double)2000);
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
        v = findViewById(R.id.imageView2);

        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.direction);
        double arr[][]=new double[2][3];
        arr[0][0]=125;
        arr[0][1]=500;
        arr[0][2]=1700;
        arr[1][0]=251;
        arr[1][1]=350;
        arr[1][2]=191.43;
        Bitmap ans= addSpots(bm,arr);

        v.setImageBitmap(ans);

    }


}
