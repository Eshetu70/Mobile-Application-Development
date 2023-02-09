package com.example.assessment3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements WelcomeFragment.WelcomeFragmentListener, RegistrationFragment.RegistrationFragmentListener, SetGenderFragment.SetGenderFragmentListener, ProfileFragment.ProfileFragmentListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView,new WelcomeFragment())
                .commit();
    }

    @Override
    public void gotoRegistration() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new RegistrationFragment(),"tag")
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
    public void totoProfile(Profile profile) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, ProfileFragment.newInstance(profile))
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void setgenderbacktoRegister(String gender) {

        RegistrationFragment fragment = (RegistrationFragment) getSupportFragmentManager().findFragmentByTag("tag");
        if(fragment !=null){
            fragment.setSetGender(gender);
        }
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void cancelgender() {
        getSupportFragmentManager().popBackStack();

    }

    @Override
    public void closeProfile() {
        getSupportFragmentManager().popBackStack();
    }
}