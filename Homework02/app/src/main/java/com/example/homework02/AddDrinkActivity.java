package com.example.homework02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class AddDrinkActivity extends AppCompatActivity {
    RadioGroup radioGroupDrinks;
    SeekBar seekBar;
    Button button4Cancel, button5AddDrinks;
    TextView textView10Percentage;
    public static final String DRINK_KEY ="DRINK";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_drink);

        seekBar =findViewById(R.id.seekBar);
        textView10Percentage =findViewById(R.id.textView10Percentage);
        button5AddDrinks =findViewById(R.id.button5AddDrinks);
        button4Cancel =findViewById(R.id.button4Cancel);
        radioGroupDrinks =findViewById(R.id.radioGroupDrinks);

        setTitle("Add Drinks");

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                textView10Percentage.setText(String.valueOf(i));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        button5AddDrinks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int alcohol =seekBar.getProgress();
                double size =1;
                if(radioGroupDrinks.getCheckedRadioButtonId()==R.id.radioButton5oz){
                    size=5;
                } else if (radioGroupDrinks.getCheckedRadioButtonId()==R.id.radioButton12oz) {
                    size =12;
                }
                if(alcohol > 0){
                    Drink drink = new Drink(alcohol, size);
                    Intent intent =new Intent();
                    intent.putExtra(DRINK_KEY, drink);
                    setResult(RESULT_OK, intent);
                    finish();
                }else {
                    Toast.makeText(AddDrinkActivity.this, "Enter greater than zero", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}