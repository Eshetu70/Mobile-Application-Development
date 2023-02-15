package com.example.scrobablelistview;

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
    public void sendSelectedCaategotry(String category) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, AppsListFragment.newInstance(category))
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void sendSelectedApp(DataServices.App app) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, DetailsFragment.newInstance(app))
                .addToBackStack(null)
                .commit();
    }
}