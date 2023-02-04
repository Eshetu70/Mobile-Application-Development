package com.example.assessment2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

public class SetGenderActivity extends AppCompatActivity {
  Button buttonSet;
  public static final String GENDER_KEY ="PROFILE";
  RadioGroup radioGroupGender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_gender);
        setTitle("Set Gender");

        buttonSet = findViewById(R.id.buttonSet);
        radioGroupGender =findViewById(R.id.radioGroupGender);

        buttonSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              int entered =radioGroupGender.getCheckedRadioButtonId();
                String gender = "Female";
                if(entered == R.id.radioButtonMale) {
                    gender = "Male";
                    double weight = 0;
                    Profile profile = new Profile(weight, gender);
                    Intent intent = new Intent();
                    intent.putExtra(GENDER_KEY, profile);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }
}