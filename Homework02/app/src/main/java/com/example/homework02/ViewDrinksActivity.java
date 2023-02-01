package com.example.homework02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ViewDrinksActivity extends AppCompatActivity {
  ArrayList<Drink> drinks =new ArrayList<>();
  int currentInddex = 0;
  ImageView imageViewDelete, imageViewPrevious, imageViewNext;
  Button buttonclose;
  TextView textViewDrinkaccount, textViewDrinkSIze, textViewAlcoholPercentage, textViewAddON;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_drinks);
        textViewDrinkaccount = findViewById(R.id.textViewDrinkaccount);
        textViewDrinkSIze = findViewById(R.id.textViewDrinkSIze);
        textViewAlcoholPercentage = findViewById(R.id.textViewAlcoholPercentage);
        textViewAddON = findViewById(R.id.textViewAddON);
        imageViewDelete = findViewById(R.id.imageViewDelete);
        imageViewPrevious = findViewById(R.id.imageViewPrevious);
        imageViewNext = findViewById(R.id.imageViewNext);
        buttonclose = findViewById(R.id.buttonclose);

        if (getIntent() != null && getIntent().hasExtra(MainActivity.DRINKS_KEY)) {
            drinks = (ArrayList<Drink>) getIntent().getSerializableExtra(MainActivity.DRINKS_KEY);
            currentInddex = 0;
            displayCurrentDrink();
        }

        buttonclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra(MainActivity.DRINKS_KEY, drinks);
                setResult(RESULT_OK, intent);
                finish();

            }
        });

        imageViewNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentInddex ==drinks.size()-1){
                    currentInddex =0;
                }else {
                    currentInddex= currentInddex+1;
                }
                displayCurrentDrink();

            }
        });
        imageViewPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentInddex ==0){
                    currentInddex = drinks.size()-1;
                }else {
                    currentInddex= currentInddex -1;
                }
                displayCurrentDrink();

            }
        });
        imageViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Drink drink = drinks.get(currentInddex);
                drinks.remove(drink);
                if(drinks.size()==0){
                    Intent intent = new Intent();
                    intent.putExtra(MainActivity.DRINKS_KEY, drinks);
                    setResult(RESULT_OK, intent);
                    finish();
                }else {
                    if(currentInddex ==0){
                        currentInddex = drinks.size()-1;
                    }else {
                        currentInddex= currentInddex -1;
                    }
                    displayCurrentDrink();
                }
            }
        });
    }

    private void displayCurrentDrink(){
        Drink drink =drinks.get(currentInddex);
        textViewDrinkaccount.setText("Drins"+ (currentInddex +1) +"out of"+drinks.size());
        textViewDrinkSIze.setText(drink.getSize()+"oz");
        textViewAlcoholPercentage.setText(drink.getAlcohol()+"%alcohol");
        textViewAddON.setText(drink.getAddedON().toString());
    }

}

