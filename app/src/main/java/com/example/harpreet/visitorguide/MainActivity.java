
package com.example.harpreet.visitorguide;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.harpreet.visitorguide.Account.login;
import com.example.harpreet.visitorguide.Account.setup;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth mauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sendtoLogin();




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

