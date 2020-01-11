package com.example.harpreet.visitorguide.Account;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.harpreet.visitorguide.MainActivity;
import com.example.harpreet.visitorguide.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.JsonObject;
import com.jacksonandroidnetworking.JacksonParserFactory;
import com.ybs.countrypicker.CountryPicker;
import com.ybs.countrypicker.CountryPickerListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class setup extends AppCompatActivity {

    private FirebaseAuth mauth;
    private ProgressBar setup_progressbar;
    private CountryPicker picker;
    private String user_id="";
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        final TextView countrySelector = findViewById(R.id.country);

        picker = CountryPicker.newInstance("Select Country");  // dialog title
        picker.setListener(new CountryPickerListener() {
            @Override
            public void onSelectCountry(String name, String code, String dialCode, int flagDrawableResID) {
                countrySelector.setText(name);
                picker.dismiss();
            }
        });

        countrySelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picker.show(getSupportFragmentManager(), "COUNTRY_PICKER");
            }
        });

        AndroidNetworking.setParserFactory(new JacksonParserFactory());

        setup_progressbar=findViewById(R.id.progressBar3);
        setup_progressbar.setVisibility(View.INVISIBLE);

    }

    @Override
    protected void onStart() {
        super.onStart();
        mauth=FirebaseAuth.getInstance();
        if(mauth.getCurrentUser()==null)
        {
            startActivity(new Intent(this,login.class));
            finish();
        }

        user_id = mauth.getCurrentUser().getUid();

        final EditText Name = findViewById(R.id.setup_name);
        final EditText Age = findViewById(R.id.setup_age);
        final EditText Mail = findViewById(R.id.setup_mail);
        final TextView country = findViewById(R.id.country);

        JSONObject obj = new JSONObject();
        try{
            obj.put("id",user_id);
        }catch (Exception e)
        {

        }

        dialog = new ProgressDialog(this);
        dialog.setTitle("Checking Data");
        dialog.setMessage("Please Wait..");
        dialog.show();
        AndroidNetworking.post("https://us-central1-monuments-5eabc.cloudfunctions.net/app/check")
                .addJSONObjectBody(obj)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject res) {

                        try {
                            JSONObject obj = res;
                            String id,name,age,mail,place;

                            id = obj.get("id").toString();

                            if(!id.equals("null"))
                            {
                                name = obj.get("name").toString();
                                Name.setText(name);

                                age = (String) obj.get("age").toString();
                                Age.setText(age);

                                place = (String) obj.get("country").toString();
                                country.setText(place);

                                mail = (String) obj.get("mail").toString();
                                Mail.setText(mail);
                            }
                            dialog.dismiss();

                        } catch (JSONException e) {
                            dialog.dismiss();
                        }

                    }
                    @Override
                    public void onError(ANError error) {
                        dialog.dismiss();
                    }
                });
    }

    public void submitdetails(View view) {

        setup_progressbar.setVisibility(View.VISIBLE);

        mauth=FirebaseAuth.getInstance();
        if(mauth.getCurrentUser()==null)
        {
            startActivity(new Intent(this,login.class));
            finish();
        }

        user_id = mauth.getCurrentUser().getUid();

        EditText Name = findViewById(R.id.setup_name);
        String name = Name.getText().toString();

        EditText Age = findViewById(R.id.setup_age);
        String age = Age.getText().toString();
        try{
            int testing_age = Integer.parseInt(age);
        }catch(Exception e)
        {
            Toast.makeText(setup.this,"Wrong Format for Number Field",Toast.LENGTH_SHORT).show();
            setup_progressbar.setVisibility(View.INVISIBLE);
            return;
        }

        EditText Mail = findViewById(R.id.setup_mail);
        String mail = Mail.getText().toString();

        TextView country = findViewById(R.id.country);
        String place = country.getText().toString();
        if(place.equals("Select Country"))
        {
            Toast.makeText(setup.this,"Please select country",Toast.LENGTH_SHORT).show();

            setup_progressbar.setVisibility(View.INVISIBLE);
            return;
        }

        if(user_id.isEmpty()||name.isEmpty()||age.isEmpty()||mail.isEmpty())
        {
            Toast.makeText(setup.this,"Please Enter all Fields",Toast.LENGTH_SHORT).show();
            setup_progressbar.setVisibility(View.INVISIBLE);
            return;
        }

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", user_id);
            jsonObject.put("name", name);
            jsonObject.put("age", age);
            jsonObject.put("mail", mail);
            jsonObject.put("country", place);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AndroidNetworking.post("https://us-central1-monuments-5eabc.cloudfunctions.net/app/upload")
                .addJSONObjectBody(jsonObject) // posting json
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(setup.this,"Done",Toast.LENGTH_SHORT).show();
                        setup_progressbar.setVisibility(View.INVISIBLE);
                        startActivity(new Intent(setup.this,MainActivity.class));
                        finish();
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(setup.this,"Error:" +anError.toString(),Toast.LENGTH_SHORT).show();
                        setup_progressbar.setVisibility(View.INVISIBLE);
                    }
                });



    }
}
