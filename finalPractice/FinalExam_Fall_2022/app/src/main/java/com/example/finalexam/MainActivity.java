package com.example.finalexam;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements LoginFragment.LoginListener, RegisterFragment.AuthListener, SearchFragment.SearchListener, MyFavoritesFragment.OnPhotoSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(FirebaseAuth.getInstance()==null){
            getSupportFragmentManager().beginTransaction()
                    // .add(R.id.fragment_container, new LoginFragment())
                    .add(R.id.containerView, new LoginFragment())
                    .commit();
        }
        getSupportFragmentManager().beginTransaction()
                // .add(R.id.fragment_container, new LoginFragment())
                .replace(R.id.containerView, new SearchFragment())
                .addToBackStack(null)
                .commit();
    }


    @Override
    public void createNewAccountClicked() {
        getSupportFragmentManager().beginTransaction()
                // .replace(R.id.fragment_container, new RegisterFragment())
                .replace(R.id.containerView, new RegisterFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void cancel() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void authSuccessful() {
        getSupportFragmentManager().beginTransaction()
                // .replace(R.id.fragment_container, new SearchFragment())
                .replace(R.id.containerView, new SearchFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void logout() {
        getSupportFragmentManager().beginTransaction()
                // .replace(R.id.fragment_container, new LoginFragment())
                .replace(R.id.containerView, new LoginFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void myFavorites() {
        getSupportFragmentManager().beginTransaction()
                // .replace(R.id.fragment_container, new FavoritesFragment())
                .replace(R.id.containerView, new MyFavoritesFragment())
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void onPhotoSelected(Photo photo) {

        getSupportFragmentManager().beginTransaction()
                // .replace(R.id.fragment_container, photoFragment)
                .replace(R.id.containerView, PhotoDetailsFragment.newInstance(photo))
                .addToBackStack(null)
                .commit();
    }
}