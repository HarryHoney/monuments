package com.example.harpreet.visitorguide;

import android.Manifest;
import android.accounts.Account;
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
import android.widget.Toast;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.harpreet.visitorguide.Account.login;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;

import org.json.JSONObject;

public class MLCamera extends AppCompatActivity {

    private FirebaseAuth mauth;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mlcamera);

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

        Toast.makeText(this, "here", Toast.LENGTH_SHORT).show();
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

    private void workOndetails(Uri image) {

        JSONObject obj = new JSONObject();
        try {
            obj.put("downloadUrl", image.toString());
        }catch (Exception e)
        {

        }
        AndroidNetworking.post("Image upload done for Machine Learning")
                .addJSONObjectBody(obj)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {


                        // I will get Image names here





                    }
                    @Override
                    public void onError(ANError error) {
                        Toast.makeText(MLCamera.this, "Sorry some error occurred", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void takePic()
    {
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
