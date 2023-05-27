package com.example.contacts;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class AddEditContactActivity extends AppCompatActivity {

    private ImageView profilePhoto;
    private EditText nameEt, phoneEt, emailEt;

    private Button addButton;

    final static String EMAIL_PATTERN = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    String name,phone,email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_contact);
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
        Log.d("WorkerLog","It works");
        Log.d( email.isEmpty() ? "Phone: " : "E-mail: ", email.isEmpty() ? phone : email);
    }


}