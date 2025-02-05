package com.example.assessment8;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.assessment8.db.Drink;
import com.example.assessment8.fragments.AddDrinkFragment;
import com.example.assessment8.fragments.BACFragment;
import com.example.assessment8.fragments.SetProfileFragment;
import com.example.assessment8.fragments.UpdateDrinkFragment;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity implements BACFragment.BACFragmentListener,
        SetProfileFragment.SetProfileFragmentListener, AddDrinkFragment.AddDrinkFragmentListener, UpdateDrinkFragment.UpdateDrinkListener {
    Profile mProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        Gson gson = new Gson();
        if(sharedPref.contains("profile")){
            mProfile = gson.fromJson(sharedPref.getString("profile", ""), Profile.class);
        }

        getSupportFragmentManager().beginTransaction()
                .add(R.id.rootView, new BACFragment(), "bac-fragment")
                .commit();
    }

    @Override
    public void gotoSetProfile() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new SetProfileFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public Profile getProfile() {
        return mProfile;
    }

    @Override
    public void clearProfile() {
        mProfile = null;
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.remove("profile");
        editor.apply();
    }

    @Override
    public void gotoAddDrink() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new AddDrinkFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoUpdateDrink(Drink drink) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, UpdateDrinkFragment.newInstance(drink))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void cancelProfile() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void sendProfile(Profile profile) {
        mProfile = profile;
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        Gson gson = new Gson();
        editor.putString("profile", gson.toJson(profile));
        editor.apply();
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void doneAddDrink() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void cancelAddDrink() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void doneUpdateDrink() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void cancelUpdateDrink() {
        getSupportFragmentManager().popBackStack();
    }
}