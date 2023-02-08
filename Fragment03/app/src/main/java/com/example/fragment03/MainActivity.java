package com.example.fragment03;

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
                .replace(R.id.rootView, new RegistrationFragment(),"Test")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void setDepartment() {
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
    public void sendDepartment(String department) {
        RegistrationFragment fragment = (RegistrationFragment) getSupportFragmentManager().findFragmentByTag("Test");
        if(fragment !=null){
            fragment.SetSectedDepartment(department);

        }
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void cancelDepartment() {
     getSupportFragmentManager().popBackStack();
    }
}