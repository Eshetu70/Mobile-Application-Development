package com.example.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {


     TextView textViewName,textViewEmail,textViewID, textViewDept;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setTitle("Profile");

        textViewName =findViewById(R.id.textViewName);
        textViewEmail =findViewById(R.id.textViewEmail);
        textViewID =findViewById(R.id.textViewID);
        textViewDept =findViewById(R.id.textViewDept);

        if(getIntent()!=null && getIntent().hasExtra(SecondActivity.KEY_PROFILE)){
            Profile profile = (Profile) getIntent().getSerializableExtra(SecondActivity.KEY_PROFILE);
            textViewName.setText(profile.getName());
            textViewEmail.setText(profile.getEmail());
            textViewID.setText(profile.getId());
            textViewDept.setText(profile.getDepartment());

        }


    }
}