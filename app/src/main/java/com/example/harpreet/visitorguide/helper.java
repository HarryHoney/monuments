package com.example.harpreet.visitorguide;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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


    TextView v;ImageView vv;
    String string="";

    void addSpots(double data[][]){

        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.direction);
        Canvas canvas = new Canvas();
        Bitmap copy = bm.copy(bm.getConfig(),true);
        int a = copy.getWidth();
        int b = copy.getHeight();
        int aa = canvas.getHeight();
        int bb = canvas.getWidth();
        v = findViewById(R.id.textView2);
        v.setText((a)+"  "+(b)+"    "+(aa)+"  "+(bb));
        canvas.setBitmap(copy);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.YELLOW);
        for(int i=0;i<data[0].length;i++){
            float x = (float) (a/2 + data[0][i]*Math.sin(Math.toRadians(data[1][i]))*((0.303*a)/(double)1500));
            float y = (float) (b/2 - data[0][i]*Math.cos(Math.toRadians(data[1][i]))*((0.303*a)/(double)1500));
            canvas.drawCircle(x,y,25,paint);
        }
        ImageView v = findViewById(R.id.testing);
        v.setImageBitmap(copy);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helper);

        v = findViewById(R.id.textView2);
        double arr[][]=new double[2][6];
        arr[0][0]=1500;arr[1][0]=285;
        arr[0][1]=1200;arr[1][1]=80;
        arr[0][2]=0;arr[1][2]=70;
        arr[0][3]=900;arr[1][3]=290;
        arr[0][4]=223;arr[1][4]=15;
        arr[0][5]=169;arr[1][5]=170;
        addSpots(arr);

        }

    }



