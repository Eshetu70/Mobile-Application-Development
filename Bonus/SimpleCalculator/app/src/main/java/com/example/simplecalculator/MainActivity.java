package com.example.simplecalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
   EditText EnterAID, EnterBid;
   TextView textviewNAid;
    Button CalcualteID, resetID;
    RadioGroup radioGroupID;
    private double inputA =0,  inputB=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EnterAID =findViewById(R.id.EnterAID);
        EnterBid =findViewById(R.id.EnterBid);
        textviewNAid =findViewById(R.id.textviewNAid);
        CalcualteID=findViewById(R.id.CalcualteID);
        resetID =findViewById(R.id.resetID);
        textviewNAid =findViewById(R.id.textviewNAid);
        radioGroupID=findViewById(R.id.radioGroupID);


        CalcualteID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    inputA =Double.parseDouble(EnterAID.getText().toString());
                    inputB =Double.parseDouble(EnterBid.getText().toString());

                    if(inputB>0){
                        if(radioGroupID.getCheckedRadioButtonId()==R.id.radioButtonDivided) {
                            textviewNAid.setText(String.format("= %.2f", inputA / inputB));
                        }
                       else if(radioGroupID.getCheckedRadioButtonId()==R.id.radioButtonadd) {
                            textviewNAid.setText(String.format("= %.2f", inputA + inputB));
                        } else if (radioGroupID.getCheckedRadioButtonId()==R.id.radioButtonSub) {
                            textviewNAid.setText(String.format("= %.2f", inputA - inputB));

                        } else if (radioGroupID.getCheckedRadioButtonId()==R.id.radioButtonMultiple) {
                            textviewNAid.setText(String.format("= %.2f", inputA * inputB));
                        }

                    }else {
                        Toast.makeText(MainActivity.this, "Input valid values", Toast.LENGTH_SHORT).show();
                    }

                }catch (NumberFormatException ex){
                    Toast.makeText(MainActivity.this, "Enter valid number", Toast.LENGTH_SHORT).show();
                }


            }
        });
     resetID.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             EnterAID.setText("");
             EnterBid.setText("");
             textviewNAid.setText("");

         }
     });






    }
}