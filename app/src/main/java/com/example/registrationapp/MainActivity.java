package com.example.registrationapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private ImageView eyeIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText passwordEditText = findViewById(R.id.rp_signinPassword);
        EditText emailEditText = findViewById(R.id.login_email);

        eyeIcon = findViewById(R.id.eye_icon);
        eyeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toggle password visibility
                if (passwordEditText.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                    passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    eyeIcon.setImageResource(R.drawable.ic_eye);
                } else {
                    passwordEditText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    eyeIcon.setImageResource(R.drawable.ic_eye_off);
                }
                // Move cursor to the end of the input
                passwordEditText.setSelection(passwordEditText.getText().length());
            }
        });

        Button login=findViewById(R.id.login_btn);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String password= passwordEditText.getText().toString().trim();
               String email= emailEditText.getText().toString().trim();
               if (email.isEmpty()){
                   emailEditText.setError("Requyired!");
                   emailEditText.requestFocus();
               }
               else if (password.isEmpty()){
                   passwordEditText.setError("required!");
                   passwordEditText.requestFocus();
               }else{
                   if (email.equals("staff@gmail.com")&& password.equals("password")){
                       Intent intent=new Intent(MainActivity.this, Dashboard.class);
                       startActivity(intent);
                   }else{
                       Toast.makeText(MainActivity.this, "Incorrect information!", Toast.LENGTH_SHORT).show();
                   }
               }

            }
        });
    }
}