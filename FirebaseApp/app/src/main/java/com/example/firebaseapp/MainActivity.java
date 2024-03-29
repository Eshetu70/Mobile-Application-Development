package com.example.firebaseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth =FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser()==null){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.rootView, new LoginFragment())
                    .commit();
        }else{
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.rootView, new MainFragment())
                    .commit();
        }

    }
}