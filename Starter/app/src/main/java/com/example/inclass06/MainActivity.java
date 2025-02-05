package com.example.inclass06;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements ContactsFragment.ContactsListener, CreateContactFragment.CreateListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new ContactsFragment())
                .commit();
    }

    @Override
    public void gotoDetails(Contact contact) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, DetailsFragment.newInstance(contact))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoAddContact() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new CreateContactFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void completeSubmit() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void cancelContact() {
        getSupportFragmentManager().popBackStack();
    }
}