package com.example.homework02;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
public class MainActivity extends AppCompatActivity {
    Button buttonviewDrinks, buttonAddDrinks, buttonReset, buttonSetWeight;
    Profile profile;
    View viewID;
    TextView textViewWeightView, textView4BACLEVEL, textViewNumDrinks, textViewstatusID;
    ArrayList<Drink> drinks = new ArrayList<>();
    public static final String DRINKS_KEY ="DRINKS";
    private ActivityResultLauncher<Intent> startGetProfileResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == RESULT_OK) {
                profile = (Profile) result.getData().getSerializableExtra(SetProfileActivity.PROFILE_KEY);
                textViewWeightView.setText(profile.getWeight() + " lbs " + profile.getGender());
                buttonAddDrinks.setEnabled(true);
            } else {
                profile = null;
                textViewWeightView.setText("");
                drinks.clear();
                setupBACInfo();
                buttonAddDrinks.setEnabled(false);
            }
        }
    });
    private ActivityResultLauncher<Intent> startGetDrinkResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == RESULT_OK) {
                Drink drink = (Drink) result.getData().getSerializableExtra(AddDrinkActivity.DRINK_KEY);
                drinks.add(drink);
                setupBACInfo();

            } else {
            }
        }
    });

    private ActivityResultLauncher<Intent> starViewDrinkResult =registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result.getResultCode() == RESULT_OK){
                drinks = (ArrayList<Drink>) result.getData().getSerializableExtra(DRINKS_KEY);
                //Intent intent =result.getData();
                setupBACInfo();

            }

        }
    });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("BAC Calculator");
        buttonviewDrinks = findViewById(R.id.buttonviewDrinks);
        buttonAddDrinks = findViewById(R.id.buttonAddDrinks);
        buttonReset = findViewById(R.id.buttonReset);
        buttonSetWeight = findViewById(R.id.buttonSetWeight);
        textViewWeightView = findViewById(R.id.textViewWeightView);
        textView4BACLEVEL = findViewById(R.id.textView4BACLEVEL);
        textViewNumDrinks = findViewById(R.id.textViewNumDrinks);
        viewID = findViewById(R.id.viewID);
        textViewstatusID = findViewById(R.id.textViewstatusID);
        buttonSetWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SetProfileActivity.class
                );
                startGetProfileResult.launch(intent);
            }
        });
        buttonviewDrinks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(drinks.size()>0){
                    Intent intent = new Intent(MainActivity.this, ViewDrinksActivity.class);
                    intent.putExtra(DRINKS_KEY, drinks);
                    starViewDrinkResult.launch(intent);
                }else {
                    Toast.makeText(MainActivity.this, "Enter Valid Drink size", Toast.LENGTH_SHORT).show();
                }

            }
        });
        buttonAddDrinks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddDrinkActivity.class);
                startGetDrinkResult.launch(intent);
            }
        });
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drinks.clear();
                profile = null;
                textViewWeightView.setText("");
                setupBACInfo();
                buttonAddDrinks.setEnabled(false);

            }
        });
    }

    private void setupBACInfo() {
        textViewNumDrinks.setText("# Drinks:" + drinks.size());

        if (drinks.size() == 0) {
            viewID.setBackgroundResource(R.color.safe_color);
            textViewstatusID.setText("You're Safe");
            textView4BACLEVEL.setText("BAC Level:0.000");
        } else {
            double valueA = 0.0;
            double valueR = 0.66;
            if (profile.getGender() == "Male") {
                valueR = 0.73;
            }
            for (Drink drink : drinks) {
                valueA = valueA + drink.getAlcohol() * drink.getSize() / 100.0;
            }
            double bac = valueA * 5.14 / (profile.getWeight() * valueR);
            textView4BACLEVEL.setText("BAC Level:" + bac);
            if (bac <= 0.08) {
                viewID.setBackgroundResource(R.color.safe_color);
                textViewstatusID.setText("You're Safe");
            } else if (bac <= 0.2) {
                textViewstatusID.setText("Be careful");
                viewID.setBackgroundResource(R.color.careful_color);
            } else {
                textViewstatusID.setText("Over Limit");
                viewID.setBackgroundResource(R.color.Over_color);

            }
        }
    }
}


