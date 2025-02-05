package com.example.fragmentpractice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements MainFragment.MainFragmentListener, WeightFragment.WeightFragmentListener, GenderFragment.GenderFragmentListener, ProfileFragment.ProfileFragmentListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new MainFragment(),"teg")
                .commit();
    }

    @Override
    public void setWeight() {
       getSupportFragmentManager().beginTransaction()
               .replace(R.id.rootView, new WeightFragment())
               .addToBackStack(null)
               .commit();
    }

    @Override
    public void setGender() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new GenderFragment())
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void setReset() {
        getSupportFragmentManager().beginTransaction()
                .commit();

    }

    @Override
    public void setSubmit(Profile profile) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, ProfileFragment.newInstance(profile))
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void setweightBackToMain(double weight) {

        MainFragment fragment = (MainFragment) getSupportFragmentManager().findFragmentByTag("teg");
          if(fragment != null)
          {
              fragment.setSetWeightvalues(weight);
          }
        getSupportFragmentManager().popBackStack();

    }
    @Override
    public void sendCancel() {
        getSupportFragmentManager().popBackStack();

    }
    @Override
    public void setGender(String gender) {
        MainFragment fragment = (MainFragment) getSupportFragmentManager().findFragmentByTag("teg");
        if(fragment != null)
        {
            fragment.setSetGender(gender);
        }
        getSupportFragmentManager().popBackStack();

    }
    @Override
    public void setCancelGender() {
   getSupportFragmentManager().popBackStack();
    }

    @Override
    public void closeProfile() {
        getSupportFragmentManager().popBackStack();

    }
}