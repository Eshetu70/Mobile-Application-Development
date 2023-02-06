package com.example.practiceactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements MainFragment.MainFragmentListener, RegistrationFragment.RegistrationFragmentListner, DepartmentFragment.DepartmentFragmentListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.rootview, new MainFragment())
                .commit();

    }

    @Override
    public void gotoRegister() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootview, new RegistrationFragment(),"register")
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void sendDepartment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootview, new DepartmentFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoProfile(Profile profile) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootview, ProfileFragment.newInstance(profile))
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void backToRegistration(String department) {
        RegistrationFragment fragment = (RegistrationFragment) getSupportFragmentManager().findFragmentByTag("register");
        if(fragment !=null){
            fragment.setSelecedDepartment(department);
        }
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void cancelDepartment() {
        getSupportFragmentManager().popBackStack();

    }
}