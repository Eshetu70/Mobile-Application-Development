package com.example.activityinclass;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    TextView textView4NameID, textView9EmailID, textViewID, textViewDeptID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        setTitle("Profile");
        textView4NameID =findViewById(R.id.textView4NameID);
        textView9EmailID =findViewById(R.id.textView9EmailID);
        textViewID =findViewById(R.id.textViewID);
        textViewDeptID =findViewById(R.id.textViewDeptID);

        if(getIntent()!=null && getIntent().hasExtra(RegisterActivity.KEY_PROFILE));
        Profile profile = (Profile) getIntent().getSerializableExtra(RegisterActivity.KEY_PROFILE);
        textView4NameID.setText(profile.getName());
        textView9EmailID.setText(profile.getEmail());
        textViewID.setText(profile.getId());
        textViewDeptID.setText(profile.getDepertment());
    }
}