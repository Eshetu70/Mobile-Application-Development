package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements CitiesFragment.CitiesFragmentListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new CitiesFragment())
                .commit();

    }

    @Override
    public void OncitySelected(DataService.City city) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView,WeatherFragment.newInstance(city))
                .addToBackStack(null)
                .commit();
    }
}