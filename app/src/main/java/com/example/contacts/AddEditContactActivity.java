package com.example.contacts;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class AddEditContactActivity extends AppCompatActivity {

    private ImageView profilePhoto;
    private EditText nameEt, phoneEt, emailEt;
    private Button addButton;

    private final static String EMAIL_PATTERN = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private final static int CAMERA_PERMISSION_CODE = 100;
    private final static int STORAGE_PERMISSION_CODE = 200;
    private final static int IMAGE_FROM_GALLERY_CODE = 300;
    private final static int IMAGE_FROM_CAMERA_CODE = 400;

    private ActivityResultLauncher<Intent> galleryLauncher;

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

    public void clickImage(View view){
        showImagePickerDialog();
    }

    public void showImagePickerDialog(){
        String[] options = {"Camera", "Gallery"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Choose an optiom");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == 0){
                    Log.d("INFO: ","Camera selected");
                    if(!checkCameraPermission()){
                        requestCameraPermission();
                    }else{
                        pickFromCamera();
                    }
                }
                else if (i == 1){
                    Log.d("INFO", "Gallery selected");
                    if(!checkStoragePermission()){
                        requestStoragePermission();
                    }else{
                        pickFromGallery();
                    }
                }

            }
        }).create().show();

    }

    private void pickFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, IMAGE_FROM_GALLERY_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null){
            Uri selectedImage = data.getData();
            profilePhoto.setImageURI(selectedImage);
        }
    }

    private void pickFromCamera() {
        Log.d("INFO","pickFromCamera()");

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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case CAMERA_PERMISSION_CODE:
                if (grantResults.length > 0){
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean storageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (cameraAccepted && storageAccepted){
                        pickFromCamera();
                    }else {
                        Toast.makeText(this, "Camera and Storage permission needed", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case STORAGE_PERMISSION_CODE:
                if (grantResults.length > 0){
                    boolean storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                    if(storageAccepted){
                        pickFromGallery();
                    }else{
                        Toast.makeText(this, "Storage permission needed", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }
}