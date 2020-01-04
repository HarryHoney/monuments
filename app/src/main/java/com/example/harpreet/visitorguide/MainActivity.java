
package com.example.harpreet.visitorguide;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.media.CameraProfile;
import android.opengl.GLSurfaceView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

import com.example.harpreet.visitorguide.Account.login;
import com.example.harpreet.visitorguide.Account.setup;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;

import eu.livotov.labs.android.camview.CameraLiveView;
import eu.livotov.labs.android.camview.camera.PictureProcessingCallback;

public class MainActivity extends AppCompatActivity {

    //https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=-33.8670522,151.1957362&radius=1500&type=restaurant&keyword=cruise&key=YOUR_API_KEY
    FirebaseAuth mauth;
    FloatingActionButton floatingActionButton;
    CameraLiveView cameraLiveView;
    PictureProcessingCallback callback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sendtoLogin();

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
                BitmapUtils.storePhotoOnDisk(img);
                cameraLiveView.startCamera();
            }
        };

        floatingActionButton = findViewById(R.id.floatingActionButton2);
        cameraLiveView = findViewById(R.id.camera);
        cameraLiveView.startCamera();
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraLiveView.getController().takePicture(callback);
            }
        });


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


}

