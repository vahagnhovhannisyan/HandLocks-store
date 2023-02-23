package com.example.myhandlocksstore.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.myhandlocksstore.ui.home.HomeActivity;
import com.example.myhandlocksstore.R;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    ProgressBar progressBar;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        if(auth.getCurrentUser() != null){
            startActivity(new Intent(MainActivity.this, HomeActivity.class));
            progressBar.setVisibility(View.VISIBLE);
            Toast.makeText(this,"Խնդրում ենք սպասել, դուք արդեն մուտք եք գործել", Toast.LENGTH_SHORT).show();
            finish();
        }


    }

    public void login(View view) {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    public void register(View view) {
        Intent intent = new Intent(MainActivity.this, RegistrationActivity.class);
        startActivity(intent);
    }
}