package com.example.assessment2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SetWeightActivity extends AppCompatActivity {

    EditText editTextNumberDecimal;
    Button buttonSet;
   public static final String KEY_WEIGHT ="profile";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_weight);
        setTitle("Set Weight");

        editTextNumberDecimal = findViewById(R.id.editTextNumberDecimal);

        buttonSet =findViewById(R.id.buttonSet);

        buttonSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    String gender ="";
                    Double weight = Double.valueOf(editTextNumberDecimal.getText().toString());
                    Profile profile = new Profile(weight,gender);
                    Intent intent = new Intent();
                    intent.putExtra(KEY_WEIGHT, profile);
                    setResult(RESULT_OK, intent);
                    finish();

                }catch (NumberFormatException ex){
                    Toast.makeText(SetWeightActivity.this, "Enter Weight", Toast.LENGTH_SHORT).show();
                }

            }
        });



    }
}