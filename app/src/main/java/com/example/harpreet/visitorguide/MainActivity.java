
package com.example.harpreet.visitorguide;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.example.harpreet.visitorguide.Account.login;
import com.example.harpreet.visitorguide.Account.setup;
import com.example.harpreet.visitorguide.sampledata.GPStracker;
import com.google.firebase.auth.FirebaseAuth;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    //https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=-33.8670522,151.1957362&radius=1500&type=restaurant&keyword=cruise&key=AIzaSyB1ZHj8YPs4xB1x6uq3jWn8NXarGqJTyU8
    FirebaseAuth mauth;
    CircleImageView circle1;
    CircleImageView circle2;
    CircleImageView circle3;
    CircleImageView circle4;
    CircleImageView circle5;
    CircleImageView circle6;
    Intent intent;
    Location l;
    GPStracker gps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sendtoLogin();

        circle1 = findViewById(R.id.option1);
        circle2 = findViewById(R.id.option2);
        circle3 = findViewById(R.id.option3);
        circle4 = findViewById(R.id.option4);
        circle5 = findViewById(R.id.option5);
        circle6 = findViewById(R.id.option6);

        gps = new GPStracker(this);

        circle1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                l = gps.getLocation();
                if(l!=null){
                intent = new Intent(getBaseContext(),Camera.class);
                intent.putExtra("key","temples");
                startActivity(intent);}
                else{makeaToast();}

            }
        });

        circle2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                l = gps.getLocation();
                if(l!=null){
                intent = new Intent(getBaseContext(),Camera.class);
                intent.putExtra("key","");
                startActivity(intent);}else{makeaToast();}
            }
        });

        circle3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                l = gps.getLocation();
                if(l!=null){
                intent = new Intent(getBaseContext(),Camera.class);
                intent.putExtra("key","");
                startActivity(intent);}
                else{makeaToast();}
            }
        });

        circle4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                l = gps.getLocation();
                if(l!=null){
                intent = new Intent(getBaseContext(),Camera.class);
                intent.putExtra("key","");
                startActivity(intent);}
                else
                {
                    makeaToast();
                }
            }
        });

        circle5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this,MLCamera.class));

            }
        });

        circle6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                l = gps.getLocation();
                if(l!=null){
                intent = new Intent(getBaseContext(),Camera.class);
                intent.putExtra("key","");
                startActivity(intent);}else{makeaToast();}
            }
        });



    }

    private void makeaToast() {
        if(!gps.isGPSenable()){
            Toast.makeText(this, "Please enable you GPS", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case(R.id.settings):
                startActivity(new Intent(this,setup.class));
                finish();
                return true;
            case (R.id.about_us):
                //do something
                Toast.makeText(MainActivity.this,"About us Selected",Toast.LENGTH_LONG).show();

                return true;
            case (R.id.sign_out):
            {
                mauth.signOut();
                mauth=null;
                startActivity(new Intent(this,login.class));
                finish();
            }
            default:
                return false;
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.CAMERA},123);
        sendtoLogin();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        sendtoLogin();
    }

    private void sendtoLogin() {

        mauth=FirebaseAuth.getInstance();//in if conditon ... deatils are null as well then it it to setup activity
        if(mauth.getCurrentUser()==null) {
            Intent intent=new Intent(MainActivity.this,login.class);
            startActivity(intent);
            finish();
        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 123: {
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                }
            }
        }
    }//function ends here

}

