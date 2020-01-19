package com.example.harpreet.visitorguide;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.example.harpreet.visitorguide.Account.login;
import com.example.harpreet.visitorguide.UtilsFolder.Server;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;

import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;

public class MLCamera extends AppCompatActivity {

    private ProgressDialog dialog;
    private FirebaseAuth mauth;
    private StorageReference storageReference;
    private String ModelURL;
    private ProgressBar bar;
    private TextView desc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mlcamera);

        bar = findViewById(R.id.progressBarML);
        bar.setVisibility(View.INVISIBLE);
        desc = findViewById(R.id.desc);
        mauth=FirebaseAuth.getInstance();
        if(mauth!=null)
        takePic();
        else
        {
            startActivity(new Intent(this,login.class));
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                doSomethingWithCroppedImage(resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }


    }

    private void doSomethingWithCroppedImage(Uri outputUri) {

        CircleImageView view = findViewById(R.id.placeImage);
        view.setImageURI(outputUri);

        final StorageReference image_path=storageReference.child("ML_Image").child("scan.jpg");//storing the image to storage on specified path

        image_path.putFile(outputUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                image_path.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) { ;
                        workOndetails(uri);
                    }

                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {

                    }
                });
            }

        });

    }


    private void workOndetails(final Uri image) {

        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Server server = dataSnapshot.getValue(Server.class);
                if(server==null)
                    Toast.makeText(MLCamera.this, "empty", Toast.LENGTH_SHORT).show();
                else
                {
                    ModelURL = server.model;
                    callToModel(image);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(MLCamera.this, "Unable to read", Toast.LENGTH_SHORT).show();
            }
        };
        mDatabase.addValueEventListener(postListener);




    }

    private void callToModel(Uri image) {

        dialog.setTitle("Image Processing");
        JSONObject obj = new JSONObject();
        try {
//            Toast.makeText(this, image.toString(), Toast.LENGTH_SHORT).show();
            obj.put("downloadUrl", image.toString());
        }catch (Exception e)
        {

        }
//        Toast.makeText(this, ModelURL, Toast.LENGTH_SHORT).show();
        AndroidNetworking.post("http://"+ModelURL)
                .addJSONObjectBody(obj)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {


                        String data[] = response.split("'");
                        String name = data[3];
                        double prob = Double.parseDouble(data[6].substring(2,data[6].length()-3))*100;
                        Toast.makeText(MLCamera.this, ""+prob, Toast.LENGTH_SHORT).show();
                        if(prob<45){
                            dialog.dismiss();bar.setVisibility(View.INVISIBLE);
                            desc.setText("Sorry Data related to this image is not available!");
                        }
                        else {
                            dialog.setTitle("Fetching Data");
                            callAtWikipage(name);

                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        Toast.makeText(MLCamera.this, "Dinesh:"+error.toString(), Toast.LENGTH_SHORT).show();
                        dialog.dismiss();bar.setVisibility(View.INVISIBLE);
                    }
                });

    }

    private void callAtWikipage(final String name) {

        AndroidNetworking.get("https://wikifun.herokuapp.com/info")
                .setPriority(Priority.MEDIUM)
                .addQueryParameter("location",name+" goa,India")
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {

                        desc.setText(name+" : "+response);
                        //inc count of res place
                        dialog.dismiss();
                        bar.setVisibility(View.INVISIBLE);

                    }
                    @Override
                    public void onError(ANError error) {
                        Toast.makeText(MLCamera.this, "Ashu:"+error.toString(), Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        bar.setVisibility(View.INVISIBLE);
                    }
                });

    }


    private void takePic()
    {

        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading Data...");
        dialog.setTitle("Image Uploading");
        dialog.show();
        bar.setVisibility(View.VISIBLE);

        storageReference=FirebaseStorage.getInstance().getReference(); //getting the reference of the storage
        //that is the path to storage on the server is saved here

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Please Try Again!", Toast.LENGTH_LONG).show();
                //the toast message will appear for the deny for the first time but in below line the connection is established
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            } else {
                CropImage.activity()
                        .setGuidelines(com.theartofdev.edmodo.cropper.CropImageView.Guidelines.ON)
                        .start(this);
            }
        }
    }

    public void checkimage(View view) {

        takePic();

    }
}
