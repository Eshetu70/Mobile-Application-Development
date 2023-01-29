package com.example.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

public class DepartmentActivity extends AppCompatActivity {
   RadioGroup radiogroupID;
   public static final String KEY_DEPT ="DEPT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department);
        setTitle("Department");
        radiogroupID= findViewById(R.id.radiogroupID);

        findViewById(R.id.buttonselectDept).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedid =radiogroupID.getCheckedRadioButtonId();
                String dpt= getString(R.string.computerScience);
                if(selectedid ==R.id.radioButtonCS)
                {
                    dpt= getString(R.string.computerScience);
                } else if (selectedid ==R.id.radioButtonSIS) {

                    dpt= getString(R.string.softare);
                }
                else if (selectedid ==R.id.radioButtonBI) {

                    dpt= getString(R.string.bio);
                }
                else if (selectedid ==R.id.radioButtonDS) {

                    dpt= getString(R.string.dataSicence);
                }
                Intent intent =new Intent();
                intent.putExtra(KEY_DEPT, dpt);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        findViewById(R.id.buttonCalcel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });
    }
}