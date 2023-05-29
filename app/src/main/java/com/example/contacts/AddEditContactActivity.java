package com.example.contacts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AddEditContactActivity extends AppCompatActivity {

    private ImageView profilePhoto;
    private EditText nameEt, phoneEt, emailEt;
    private Button addButton;

    private final static String EMAIL_PATTERN = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private final static int CAMERA_PERMISSION_CODE = 100;
    private final static int STORAGE_PERMISSION_CODE = 200;
    private final static int IMAGE_FROM_GALLERY_CODE = 300;
    private final static int IMAGE_FROM_CAMERA_CODE = 400;

    private String[] cameraPermission;
    private String[] storagePermission;

    String name,phone,email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_contact);
        cameraPermission = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermission = new String[]{ Manifest.permission.WRITE_EXTERNAL_STORAGE};


        profilePhoto = findViewById(R.id.profilePhoto);
        nameEt = findViewById(R.id.nameEditText);
        phoneEt = findViewById(R.id.phoneEditText);
        emailEt = findViewById(R.id.emailEditText);
        addButton = findViewById(R.id.addButton);


    }

    public void saveData(View view){
        name = nameEt.getText().toString();
        phone = phoneEt.getText().toString();
        email = emailEt.getText().toString().trim();
        if (name.isEmpty()){
            Toast.makeText(this, "Name field is not be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if (phone.isEmpty() && email.isEmpty()){
            Toast.makeText(this, "You have to add at least one contact information", Toast.LENGTH_SHORT).show();
            return;
        }
        if((!email.matches(EMAIL_PATTERN)) && !email.isEmpty()){
            Toast.makeText(this, "You have to add right email", Toast.LENGTH_SHORT).show();
            return;
        }
        Log.d("WorkerLog","saveData() is called");
        Log.d( email.isEmpty() ? "Phone: " : "E-mail: ", email.isEmpty() ? phone : email);

    }



    private boolean checkCameraPermission(){
        boolean resultCamera = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED;
        boolean resultStorage = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED;

        return resultCamera && resultStorage;
    }

    private boolean checkStoragePermission(){
        return ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED;
    }

    private void requestCameraPermission(){
        ActivityCompat.requestPermissions(this,cameraPermission, CAMERA_PERMISSION_CODE);
    }

    private void requestStoragePermission(){
        ActivityCompat.requestPermissions(this,storagePermission, STORAGE_PERMISSION_CODE);
    }


}