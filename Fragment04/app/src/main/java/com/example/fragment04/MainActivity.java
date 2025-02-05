package com.example.fragment04;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements MainFragment.MainFragmentListener, RegistrationFragment.RegistrationFragmentListener, DepartmentFragment.DepartmentFragmentListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new MainFragment())
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
    public void gotoDepartment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new DepartmentFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoProfile(Profile profile) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, ProfileFragment.newInstance(profile))
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void setBackDepartment(String dept) {

        RegistrationFragment fragment = (RegistrationFragment) getSupportFragmentManager().findFragmentByTag("tag");
        if(fragment!=null){
            fragment.setSelectDepartment(dept);
        }
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void cancelDepartment() {
        getSupportFragmentManager().popBackStack();

    }
}