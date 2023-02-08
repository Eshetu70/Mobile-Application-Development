package com.example.assessmentfragment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements MainFragment.MainFragmentListener, SetWeightFragment.SetWeightFragmentListener, SetGenderFragment.SetGenderFragmentListener, ProfileFragment.ProfileFragmentListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new MainFragment(),"tag")
                .commit();
    }

    @Override
    public void gotoWeight() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new SetWeightFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoGender() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new SetGenderFragment())
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void submitToProfile(Profile profile) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, ProfileFragment.newInstance(profile))
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void reset() {
       getSupportFragmentManager().popBackStack();

    }

    @Override
    public void setBackWeight(double weight) {
        MainFragment fragment = (MainFragment) getSupportFragmentManager().findFragmentByTag("tag");
        if(fragment!=null){
         fragment.setSelectedWeight(weight);
        }
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void cancelWeight() {
        getSupportFragmentManager().popBackStack();

    }

    @Override
    public void setGenderback(String gender) {
    MainFragment fragment = (MainFragment) getSupportFragmentManager().findFragmentByTag("tag");
    if(fragment !=null){
        fragment.setSelectedGender(gender);
    }
    getSupportFragmentManager().popBackStack();
    }

    @Override
    public void cancelGender() {
        getSupportFragmentManager().popBackStack();

    }

    @Override
    public void closeProfile() {
        getSupportFragmentManager().popBackStack();
    }
}