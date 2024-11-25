package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class PhoneLoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText phoneInput;
    private Button verifyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_login);

        mAuth = FirebaseAuth.getInstance();
        phoneInput = findViewById(R.id.phoneInput);
        verifyButton = findViewById(R.id.verifyButton);

        verifyButton.setOnClickListener(v -> {
            String phoneNumber = phoneInput.getText().toString();
            if (!phoneNumber.isEmpty()) {
                // Add phone number verification logic
                startActivity(new Intent(PhoneLoginActivity.this, HomeActivity.class));
            } else {
                Toast.makeText(this, "Enter a valid phone number.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
