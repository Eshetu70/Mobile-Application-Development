package com.example.homework02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class SetProfileActivity extends AppCompatActivity {
   EditText enteredWeight;
   RadioGroup radioGroupGender;
   Button buttonCancel, buttonSET;
   public static final String PROFILE_KEY ="profile";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_profile);

        setTitle("Set Weight/Gender");
        buttonSET =findViewById(R.id.buttonSET);
        radioGroupGender=findViewById(R.id.radioGroupGender);
        enteredWeight = findViewById(R.id.enteredWeight);
        buttonCancel =findViewById(R.id.buttonCancel);

        buttonSET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String gender ="Female";
               if(radioGroupGender.getCheckedRadioButtonId() ==R.id.radioButtonmale){
                   gender ="Male";
               }
               try {
                   double weight = Double.valueOf(enteredWeight.getText().toString());
                   if(weight >0){
                       Profile profile = new Profile(weight, gender);
                       Intent intent =new Intent();
                       intent.putExtra(PROFILE_KEY, profile);
                       setResult(RESULT_OK,intent);
                       finish();
                   }else {
                       Toast.makeText(SetProfileActivity.this, "Enter Valid weight!", Toast.LENGTH_SHORT).show();
                   }

               }catch(NumberFormatException ex){
                   Toast.makeText(SetProfileActivity.this, "Enter valid weight!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}