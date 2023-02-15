package com.example.scrollable;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements AppCategoriesFragment.AppCategoriesFragmentListener, AppsListFragment.AppsListFragmentListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new AppCategoriesFragment())
                .commit();
    }

    @Override
    public void sendSelectedAppCategory(String category) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, AppsListFragment.newInstance(category))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void sendAppsListSelected(DataServices.App app) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, AppDetailsFragment.newInstance(app))
                .addToBackStack(null)
                .commit();
    }
}