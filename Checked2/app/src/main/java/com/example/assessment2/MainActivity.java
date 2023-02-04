package com.example.assessment2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button buttonSetWeight, buttonSetGender;
    TextView textViewWeight, textViewGender;
    String Values;
    final String TAG ="demo";
    Profile profile;
     private ActivityResultLauncher<Intent> startGetSetWeight = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
         @Override
         public void onActivityResult(ActivityResult result) {

          if(result.getResultCode()==RESULT_OK){
              profile = (Profile) result.getData().getSerializableExtra(SetWeightActivity.KEY_WEIGHT);
              textViewWeight.setText( profile.getWeight()+""+ profile.getGender());
          }else{
              Toast.makeText(MainActivity.this, "", Toast.LENGTH_SHORT).show();
              
          }
         }
     });
    private ActivityResultLauncher<Intent> startGetSetGender = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {

            if(result.getResultCode()==RESULT_OK){
                profile = (Profile) result.getData().getSerializableExtra(SetGenderActivity.GENDER_KEY);
                textViewGender.setText( profile.getGender());
            }else {
                Toast.makeText(MainActivity.this, "", Toast.LENGTH_SHORT).show();
            }
        }
    });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Main");

        buttonSetWeight =findViewById(R.id.buttonSetWeight);
        textViewWeight =findViewById(R.id.textViewWeight);
        buttonSetGender =findViewById(R.id.buttonSetGender);
        textViewGender =findViewById(R.id.textViewGender);

        buttonSetWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SetWeightActivity.class);
                startGetSetWeight.launch(intent);
            }
        });

        buttonSetGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SetGenderActivity.class);
                startGetSetWeight.launch(intent);
            }
        });
    }
}