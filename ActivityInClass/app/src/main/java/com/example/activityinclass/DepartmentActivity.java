package com.example.activityinclass;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class DepartmentActivity extends AppCompatActivity {
    RadioGroup radioGroupInfo;
    Button button3Select, buttonCancelID;
    RadioButton radioButtonCS;
    public static final String KEY_DEPRT ="DEPRT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department);

        radioGroupInfo =findViewById(R.id.radioGroupInfo);
        button3Select =findViewById(R.id.button3Select);
        radioButtonCS =findViewById(R.id.radioButtonCS);
        buttonCancelID =findViewById(R.id.buttonCancelID);

        button3Select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dept = getString(R.string.label_CS);
                int selected = radioGroupInfo.getCheckedRadioButtonId();
                if(selected== R.id.radioButtonCS){
                    dept = getString(R.string.label_CS);

                }else if(selected== R.id.radioButtonSIS){
                    dept = getString(R.string.label_SIS);

                }else if(selected== R.id.radioButtonBINF){
                    dept = getString(R.string.label_BINF);

                }else if(selected== R.id.radioButtonDS){
                    dept = getString(R.string.label_DS);
                }
                Intent intent = new Intent();
                intent.putExtra( KEY_DEPRT, dept);
                setResult(RESULT_OK, intent);
                finish();
            }
        });


        buttonCancelID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }
}